#!/usr/bin/env python3
# -*- coding:utf-8 -*-


import re

if __name__ == '__main__':

    regex = '\\s*((//.*)|([0-9]+)|("(\\\\"|\\\\\\\\|\\\\n|[^"])*")|[A-Z_a-z][A-Z_a-z0-9]*|==|<=|>=|&&|\\|\\||[!"#$%&\'()*+,-./:;<=>?@[\\]^_`{|}~])?'
    print(regex)

    m = re.compile(regex)

    with open('test', 'r') as f:
        lines = f.readlines()
        for line in lines:
            print(m.search(line).groups())
