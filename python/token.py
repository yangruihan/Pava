#!/usr/bin/env python3
# -*- coding:utf-8 -*-


from enum import Enum


class TokenType(Enum):
    """
    Token type
    """
    EOF = 1,  # end of file
    identifier = 2,  # identifier token
    number = 3,  # number token
    string = 4,  # string token


class Token(object):
    """
    Token class
    """
    EOL = "\\n"  # end of line

    def __init__(self, line_number, text):
        self._line_number = line_number
        self._type = TokenType.EOF
        self._text = text

    @property
    def line_number(self):
        return self._line_number

    @property
    def type(self):
        return self._type

    @property
    def text(self):
        return self._text


class EOFToken(Token):
    """
    End of file token class
    """

    def __init__(self, line_number=-1):
        super().__init__(line_number, "EOF")
        self._type = TokenType.EOF


class IdentifierToken(Token):
    """
    Identifier token class
    """

    def __init__(self, line_number, identifier):
        super().__init__(line_number, identifier)
        self._type = TokenType.identifier


class NumberToken(Token):
    """
    Number token class
    """

    def __init__(self, line_number, number):
        super().__init__(line_number, str(number))
        self._type = TokenType.number
        self._number = number

    @property
    def number(self):
        return self._number


class StringToken(Token):
    """
    String token class
    """

    def __init__(self, line_number, string):
        super().__init__(line_number, string)
        self._type = TokenType.string
