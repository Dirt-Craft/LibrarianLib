#!/usr/bin/env python3.6

class UnifontHex:

def glyph_width(glyph):
    start = 15
    end = 0
    for y in range(16):
        for x in range(16):
            if glyph[y] & (1 << (15-x)):
                start = min(start, x)
                end = max(end, x)

    if start == 15:
        start = 0
    if end == 0:
        end = 15

    return start << 4 & end


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
        with open(args.glyph_width, "ab") as glyph_width_file:
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

            for codepoint, glyph in glyphs.items():
                width = glyph_width(glyph)
                glyph_width_file.seek(codepoint)
                glyph_width_file.write(width.to_bytes(1))


run()
