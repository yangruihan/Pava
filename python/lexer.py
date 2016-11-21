#!/usr/bin/env python3
# -*- coding:utf-8 -*-


from pava_exception import *
import re


class Lexer(object):
    """
    Lexical analyzer for Pava
    """
    _REGEX = r'\s*((//.*)|([0-9]+)|("(\\"|\\\\|\\n|[^"])*")|[A-Z_a-z][A-Z_a-z0-9]*' \
             '|==|<=|>=|&&|\|\||[!"#$%&\'()*+,-./:;<=>?@[\\]^_`{|}~])?'

    def __init__(self, lines):
        self._lines = lines
        self._pattern = re.compile(Lexer._REGEX)
        self._token_queue = []
        self._current_line = 0

    @staticmethod
    def _convert_string(string):
        s = string[1:len(string) - 1]
        return s.replace(r'\\', '\\').replace(r'\n', '\n').replace(r'\"', '\"')

    def read(self):
        if self._fill_queue(0):
            return self._token_queue.pop(0)
        else:
            return EOFToken()

    def peak(self, position):
        if self._fill_queue(position):
            return self._token_queue[position]
        else:
            return EOFToken()

    def _fill_queue(self, position):
        while position >= len(self._token_queue):
            if self._current_line < len(self._lines):
                self._read_line()
            else:
                return False

        return True

    def _read_line(self):
        line = self._lines[self._current_line]
        self._current_line += 1

        start_pos = 0
        end_pos = len(line)
        while start_pos < end_pos:
            match = self._pattern.match(line, start_pos, end_pos)
            if match is not None:
                self._add_token(self._current_line, match)
                start_pos = match.end()
            else:
                raise ParseException("bad token at line " + str(self._current_line))

        self._token_queue.append(IdentifierToken(self._current_line, Token.EOL))

    def _add_token(self, line_number, match):
        if match.group(1) is not None and match.group(2) is None:
            if match.group(3) is not None:
                token = NumberToken(line_number, int(match.group(3)))
            elif match.group(4) is not None:
                token = StringToken(line_number, self._convert_string(match.group(4)))
            else:
                token = IdentifierToken(line_number, match.group(1))
            self._token_queue.append(token)
