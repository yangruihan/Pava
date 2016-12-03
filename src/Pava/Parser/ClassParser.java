package Pava.Parser;

import Pava.AST.ClassBody;
import Pava.AST.ClassStmnt;
import Pava.AST.Dot;
import Pava.Token.Token;

import static Pava.Parser.Parser.rule;

/**
 * Created by yangruihan on 2016/12/3.
 */
public class ClassParser extends ClosureParser {
    Parser member = rule().or(def, simple);
    Parser classbody = rule(ClassBody.class).sep("{").option(member).repeat(rule().sep(";", Token.EOL).option(member)).sep("}");
    Parser defclass = rule(ClassStmnt.class).sep("class").identifier(reserved).option(rule().sep("extends").identifier(reserved)).ast(classbody);

    public ClassParser() {
        postfix.insertChoice(rule(Dot.class).sep(".").identifier(reserved));
        program.insertChoice(defclass);
    }
}
