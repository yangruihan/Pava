package Pava.Parser;

import Pava.AST.Arguments;
import Pava.AST.DefStmnt;
import Pava.AST.ParameterList;

import static Pava.Parser.Parser.rule;

/**
 * Add def identifier for Pava which can define a function
 * Created by yangruihan on 2016/11/26.
 */
public class FuncParser extends BasicParser {
    Parser param = rule().identifier(reserved);
    Parser params = rule(ParameterList.class).ast(param).repeat(rule().sep(",").ast(param));
    Parser paramList = rule().sep("(").maybe(params).sep(")");
    Parser def = rule(DefStmnt.class).sep("def").identifier(reserved).ast(paramList).ast(block);
    Parser args = rule(Arguments.class).ast(expr).repeat(rule().sep(",").ast(expr));
    Parser postfix = rule().sep("(").maybe(args).sep(")");

    public FuncParser() {
        reserved.add(")");
        primary.repeat(postfix);
        simple.option(args);
        program.insertChoice(def);
    }
}
