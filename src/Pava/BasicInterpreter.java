package Pava;

import Pava.AST.ASTree;
import Pava.AST.NullStmnt;
import Pava.Environment.BasicEnv;
import Pava.Environment.Environment;
import Pava.Token.Token;
import Pava.UI.CodeDialog;

/**
 * Basic interpreter
 * Created by yangruihan on 2016/11/24.
 */
public class BasicInterpreter {
    public static void main(String[] args) throws ParseException {
        run(new BasicParser(), new BasicEnv());
    }

    public static void run(BasicParser basicParser, Environment environment) throws ParseException {
        Lexer lexer = new Lexer(new CodeDialog());
        while (lexer.peek(0) != Token.EOF) {
            ASTree asTree = basicParser.parse(lexer);
            if (!(asTree instanceof NullStmnt)) {
                Object r = ((BasicEvaluator.ASTreeEx) asTree).eval(environment);
                System.out.println("=> " + r);
            }
        }
    }
}
