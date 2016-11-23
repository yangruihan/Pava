package Pava.Test;

import Pava.AST.ASTree;
import Pava.BasicParser;
import Pava.Lexer;
import Pava.ParseException;
import Pava.Token.Token;
import Pava.UI.CodeDialog;

/**
 * Created by yangruihan on 2016/11/23.
 */
public class ParserRunner {

    public static void main(String[] args) throws ParseException {
        Lexer l = new Lexer(new CodeDialog());
        BasicParser basicParser = new BasicParser();
        while (l.peek(0) != Token.EOF) {
            ASTree ast = basicParser.parse(l);
            System.out.println("=> " + ast.toString());
        }
    }
}
