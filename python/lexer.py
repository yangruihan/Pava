#!/usr/bin/env python3
# -*- coding:utf-8 -*-


from token import *
from pava_exception import *
import re


class Lexer(object):
    """
    Lexical analyzer for Pava
    """
    regex = '\\s*((//.*)|([0-9]+)|("(\\\\"|\\\\\\\\|\\\\n|[^"])*")|[A-Z_a-z][A-Z_a-z0-9]*|==|<=|>=|&&|\\|\\||[!"#$%&\'()*+,-./:;<=>?@[\\]^_`{|}~])?'

    def __init__(self):
        self._pattern = re.compile(regex)
        self._token_queue = []
