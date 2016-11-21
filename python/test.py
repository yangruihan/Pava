#!/usr/bin/env python3
# -*- coding:utf-8 -*-

from lexer import *


def main():
    with open('test.pa', 'r') as f:
        lines = f.readlines()
        lexer = Lexer(lines)

        while lexer.peak(0).type != TokenType.EOF:
            token = lexer.read()
            print('=> %-20s%-20s' % (token.text, token.type))


if __name__ == '__main__':
    main()
