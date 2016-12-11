package Pava.Parser;

import Pava.AST.ArrayLiteral;
import Pava.AST.ArrayRef;
import javassist.gluonj.Reviser;

import static Pava.Parser.Parser.rule;

/**
 * Created by yangruihan on 2016/12/11.
 */
@Reviser
public class ArrayParser extends FuncParser {
    Parser elements = rule(ArrayLiteral.class).ast(expr).repeat(rule().sep(",").ast(expr));

    public ArrayParser() {
        reserved.add("]");
        primary.insertChoice(rule().sep("[").maybe(elements).sep("]"));
        postfix.insertChoice(rule(ArrayRef.class).sep("[").ast(expr).sep("]"));
    }
}
