package Pava.Parser;

import Pava.AST.Fun;

import static Pava.Parser.Parser.rule;

/**
 * Created by yangruihan on 2016/11/26.
 */
public class ClosureParser extends FuncParser {
    public ClosureParser() {
        primary.insertChoice(rule(Fun.class).sep("fun").ast(paramList).ast(block));
    }
}
