#!/usr/bin/env python

import math
import re
from subprocess import call
import os

abspath = os.path.abspath(__file__)
dname = os.path.dirname(abspath)
os.chdir(dname)

unifont_version = "11.0.01"

def bytes_from_file(filename, chunksize=8192):
    with open(filename, "rb") as f:
        while True:
            chunk = f.read(chunksize)
            if chunk:
                for b in chunk:
                    yield b
            else:
                break


starts = [ord(byte) >> 4 for byte in bytes_from_file('glyph_sizes.bin')]
widths = [(ord(byte) & 15) + 1 - (ord(byte) >> 4) for byte in bytes_from_file('glyph_sizes.bin')]
widths = [width-1 if width % 2 == 1 else width for width in widths]  # 1 unifont pixel = 0.5 mc pixels. MC rounds down. Thus, subtract 1 if odd
widths = [width + 1 for width in widths]
widths[ord(' ')] = 7
widths[160] = 7  # nbsp

width_index = 0


def transform_coord(match):
    x = float(match.group(1))
    y = float(match.group(2))
    x -= starts[width_index]*64
    return str(int(x)) + " " + str(int(y))


def transform_coords(match):
    return re.sub(r'([-0-9.]+) ([-0-9.]+)', lambda m: transform_coord(m), match.group(0))


with open("metrics-" + unifont_version + ".txt", "w") as metrics:
    for idx, width in enumerate(widths):
        metrics.write("U+{0:04x}: start: {1}, end: {2}\n".format(idx, starts[idx], width))

print("===== Setting character widths")
with open("unifont-" + unifont_version + "-respanned.sfd", "w") as output_file:
    with open("unifont-" + unifont_version + ".sfd", "r") as input_file:
        for line in input_file:
            if re.match(r'StartChar:', line):
                width_index = -1
            start_char_uni_match = re.match(r'StartChar: uni([0-9a-fA-F]+)', line)
            if start_char_uni_match:
                width_index = int(start_char_uni_match.group(1), 16)
            if 0 <= width_index < len(widths):
                if line != 'Width: 0':
                    line = re.sub(r'Width: [0-9a-fA-F]+', lambda m: 'Width: ' + str((widths[width_index]+1) * 64), line)
                line = re.sub(r'<[-0-9. ]+>|[-0-9.]+ [-0-9.]+ [ml]|[-0-9.]+ [-0-9.]+ [-0-9.]+ [-0-9.]+ [-0-9.]+ [-0-9.]+ c|[-0-9.]+ [-0-9.]+ [{voc\[\]]', lambda m: transform_coords(m), line)
            output_file.write(line)
