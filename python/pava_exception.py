#!/usr/bin/env python3
# -*- coding:utf-8 -*-


from token import *


class PavaException(Exception):
    """
    Base exception for Pava
    """

    def __init__(self, message):
        super().__init__(message)
        self.message = message


class ParseException(PavaException):
    """
    Parse exception during lexical analysis
    """

    def __init__(self, message, token=None):
        if token is not None:
            super().__init__("syntax error around " + self._location(token) + ". " + message)
        else:
            super().__init__(message)

    def _location(self, token):
        if token.type == TokenType.EOF:
            return "the last line"
        else:
            return '"' + token.text + '" at line ' + str(token.line_number)


if __name__ == '__main__':
    token = EOFToken()
    exception = ParseException("eof token error", token)
    print(exception)

    token = NumberToken(10, 100)
    exception = ParseException("number token error", token)
    print(exception)
