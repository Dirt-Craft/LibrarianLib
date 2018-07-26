#!/usr/bin/env python3.6

from PIL import Image
import sys
import argparse

parser = argparse.ArgumentParser(
    description="Reads 256x256 font atlas png files and creates a .hex file from them.",
    epilog="Reads the images left to right, then top to bottom. U+XXX0 will be on the left edge, U+XXXF will be on the "
           "right edge. Expects the images to have transparent backgrounds and opaque text."
)
parser.add_argument("atlas_pattern", help="The path of the atlas pages. `xx` and `XX` will be replaced with the "
                                          "lowercase and uppercase hexadecimal codepoint prefixes respectively.")
parser.add_argument("output", help="The output path for the .hex file")
args = parser.parse_args()

print()


def glyph_width(i, glyph):
    width = 0

    # Initialize selected code points for double width (16x16).
    # Double-width is forced in cases where a glyph (usually a combining
    # glyph) only occupies the left-hand side of a 16x16 grid, but must
    # be rendered as double-width to appear properly with other glyphs
    # in a given script.  If additions were made to a script after
    # Unicode 5.0, the Unicode version is given in parentheses after
    # the script name.
    if 0x0700 <= i <= 0x074F: width = 16 # Syriac
    if 0x0800 <= i <= 0x083F: width = 16 # Samaritan (5.2)
    if 0x0900 <= i <= 0x0DFF: width = 16 # Indic
    if 0x1000 <= i <= 0x109F: width = 16 # Myanmar
    if 0x1100 <= i <= 0x11FF: width = 16 # Hangul Jamo
    if 0x1400 <= i <= 0x167F: width = 16 # Canadian Aboriginal
    if 0x1700 <= i <= 0x171F: width = 16 # Tagalog
    if 0x1720 <= i <= 0x173F: width = 16 # Hanunoo
    if 0x1740 <= i <= 0x175F: width = 16 # Buhid
    if 0x1760 <= i <= 0x177F: width = 16 # Tagbanwa
    if 0x1780 <= i <= 0x17FF: width = 16 # Khmer
    if 0x18B0 <= i <= 0x18FF: width = 16 # Ext. Can. Aboriginal
    if 0x1800 <= i <= 0x18AF: width = 16 # Mongolian
    if 0x1900 <= i <= 0x194F: width = 16 # Limbu
    # if i >= 0x1980 and i <= 0x19DF: width = 16 # New Tai Lue
    if 0x1A00 <= i <= 0x1A1F: width = 16 # Buginese
    if 0x1A20 <= i <= 0x1AAF: width = 16 # Tai Tham (5.2)
    if 0x1B00 <= i <= 0x1B7F: width = 16 # Balinese
    if 0x1B80 <= i <= 0x1BBF: width = 16 # Sundanese (5.1)
    if 0x1BC0 <= i <= 0x1BFF: width = 16 # Batak (6.0)
    if 0x1C00 <= i <= 0x1C4F: width = 16 # Lepcha (5.1)
    if 0x1CC0 <= i <= 0x1CCF: width = 16 # Sundanese Supplement
    if 0x1CD0 <= i <= 0x1CFF: width = 16 # Vedic Extensions (5.2)
    if i == 0x2329 or  i == 0x232A: width = 16 # Left- & Right-pointing Angle Brackets
    if 0x2E80 <= i <= 0xA4CF: width = 16 # CJK
    # if i >= 0x9FD8 and i <= 0x9FE9: width = 32 # CJK quadruple-width
    if 0xA900 <= i <= 0xA92F: width = 16 # Kayah Li (5.1)
    if 0xA930 <= i <= 0xA95F: width = 16 # Rejang (5.1)
    if 0xA960 <= i <= 0xA97F: width = 16 # Hangul Jamo Extended-A
    if 0xA980 <= i <= 0xA9DF: width = 16 # Javanese (5.2)
    if 0xAA00 <= i <= 0xAA5F: width = 16 # Cham (5.1)
    if 0xA9E0 <= i <= 0xA9FF: width = 16 # Myanmar Extended-B
    if 0xAA00 <= i <= 0xAA5F: width = 16 # Cham
    if 0xAA60 <= i <= 0xAA7F: width = 16 # Myanmar Extended-A
    if 0xAAE0 <= i <= 0xAAFF: width = 16 # Meetei Mayek Ext (6.0)
    if 0xABC0 <= i <= 0xABFF: width = 16 # Meetei Mayek (5.2)
    if 0xAC00 <= i <= 0xD7AF: width = 16 # Hangul Syllables
    if 0xD7B0 <= i <= 0xD7FF: width = 16 # Hangul Jamo Extended-B
    if 0xF900 <= i <= 0xFAFF: width = 16 # CJK Compatibility
    if 0xFE10 <= i <= 0xFE1F: width = 16 # Vertical Forms
    if 0xFE30 <= i <= 0xFE60: width = 16 # CJK Compatibility Forms
    if 0xFFE0 <= i <= 0xFFE6: width = 16 # CJK Compatibility Forms

    if i == 0x303F: width = 0 # CJK half-space fill

    if width != 0:
        return width

    for i in range(16):
        if glyph[i] & 0xFF != 0:
            return 16
    return 8


def encode_glyph(region):
    lines = [0] * 16
    for y in range(16):
        row = 0
        for x in range(16):
            if region.getpixel((x, y)):
                row = row | 1 << (15-x)
        lines[y] = row
    return lines


def encode_page(page, hex_file):
    atlas_file = args.atlas_pattern.replace("xx", "%0.2x" % page).replace("XX", "%0.2X" % page)
    image = Image.open(atlas_file).split()[-1]

    first_codepoint = page << 8
    for i in range(256):
        x = (i & 15) * 16
        y = (i >> 4) * 16
        codepoint = first_codepoint + i
        codepoint_hex = "%0.4X" % codepoint
        glyph = encode_glyph(image.crop((x, y, x+16, y+16)))
        width = glyph_width(i, glyph)
        # print("U+" + codepoint_hex)

        # for row in glyph:
        #     print(("{0:0=16b}".format(row)).replace("0", "-").replace("1", "#"))
        glyph = [row >> (16 - width) for row in glyph]
        # print("clipped to " + str(width))
        # for row in glyph:
        #     print((("{0:0=%db}" % width).format(row)).replace("0", "-").replace("1", "#"))

        permit_empty = codepoint in [0x0020, 0x00a0, 0x3000, 0x202f, 0x205f, 0xffa0] or 0x2000 <= codepoint <= 0x200a
        is_empty = all([it == 0 for it in glyph])
        if not is_empty or permit_empty:
            line_format = "%0." + str(int(width/4)) + "X"
            hex_lines = [line_format % line for line in glyph]
            hex_string = "".join(hex_lines)
            hex_file.write(codepoint_hex + ":" + hex_string + "\n")
            # print(codepoint_hex + ":" + hex_string)


with open(args.output, "w") as hex_file:
    for page in range(256):
        try:
            encode_page(page, hex_file)
            print("\r                             \rCompleted page for U+%0.2xxx" % page, end="")
            sys.stdout.flush()
        except FileNotFoundError:
            print("\r                             \rNo atlas file for U+%0.2xxx" % page)
