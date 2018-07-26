#!/usr/bin/env python3.6

from PIL import Image
import sys
import argparse

parser = argparse.ArgumentParser(
    description='Reads a .hex file and outputs 256x256 atlas png files.',
    epilog="Writes the images left to right, then top to bottom. U+XXX0 will be on the left edge, U+XXXF will be on "
           "the right edge. Writes glyphs with a transparent background and opaque text in the specified color. The "
           "color components of the background are still in the specified color to prevent interpolation artifacts."
)
parser.add_argument('-m', '--merge', action='store_true', help='Merge glyphs in the hex file with those already in '
                                                               'the atlas files, as opposed to completely overwriting '
                                                               'the atlases')
parser.add_argument('-c', '--color', nargs=1, default='FFFFFF', help='The foreground color of the text in six-digit '
                                                                     'hex.')
parser.add_argument('input', help='The input path for the .hex file')
parser.add_argument('atlas_pattern', help='The path of the atlas png files. `xx` and `XX` will be replaced with the '
                                          'lowercase and uppercase hexadecimal codepoint prefixes respectively.')
args = parser.parse_args()

print()

r = int(args.color[0:2], 16) # / 256
g = int(args.color[2:4], 16) # / 256
b = int(args.color[4:6], 16) # / 256


def draw_glyph(image, glyph_x, glyph_y, glyph):
    for y in range(16):
        for x in range(16):
            alpha = 255 if glyph[y] & (1 << (15-x)) else 0
            image.putpixel((glyph_x + x, glyph_y + y), (r, g, b, alpha))


def write_page(page, glyphs):
    image_path = args.atlas_pattern.replace("xx", "%0.2x" % page).replace("XX", "%0.2X" % page)
    image = None
    if args.merge:
        try:
            image = Image.open(image_path)
        except FileNotFoundError:
            pass
    if image is None:
        image = Image.new('RGBA', (256, 256), (r, g, b, 0))

    codepoints_drawn = 0
    first_codepoint = page << 8
    for i in range(256):
        codepoint = first_codepoint + i
        if codepoint in glyphs:
            x = (i & 15) * 16
            y = (i >> 4) * 16
            draw_glyph(image, x, y, glyphs[i])
            codepoints_drawn += 1
    image.save(image_path)
    return codepoints_drawn


def group(string, n):
    return [string[i:i + n] for i in range(0, len(string), n)]


def decode_glyph(line):
    codepoint_hex, glyph_hex = line.split(':', maxsplit=1)
    codepoint = int(codepoint_hex, 16)
    if len(glyph_hex) == 2 * 16:
        return codepoint, [int(it, 16) << 8 for it in group(glyph_hex, 2)]
    elif len(glyph_hex) == 4 * 16:
        return codepoint, [int(it, 16) for it in group(glyph_hex, 4)]
    else:
        raise ValueError('Invalid character hex width {} (approximately {}x16 pixels)'
                         .format(len(glyph_hex), len(glyph_hex)*4/16.0))


def run():
    with open(args.input, "r") as hex_file:
        glyph_lines = {}
        glyphs = {}

        for line_number, line in enumerate(hex_file):
            try:
                codepoint, glyph = decode_glyph(line.strip())
                if codepoint in glyphs:
                    raise ValueError("Glyph U+%0.2X was already defined on line %d" % (codepoint, glyph_lines[codepoint]))
                glyph_lines[codepoint] = line_number
                glyphs[codepoint] = glyph
            except BaseException:
                sys.stderr.print("Error processing glyph on line {}".format(line_number))
                raise

        for page in range(256):
            drawn_count = write_page(page, glyphs)
            print("\r                                                   ", end="")
            print("\rDrew %d codepoints on page for U+%0.2xxx" % (drawn_count, page), end="")
            sys.stdout.flush()


run()
