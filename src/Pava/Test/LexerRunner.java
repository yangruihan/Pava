package Pava.Test;

import Pava.Lexer;
import Pava.ParseException;
import Pava.Token.Token;
import Pava.UI.CodeDialog;

/**
 * Created by yangruihan on 2016/11/20.
 */
public class LexerRunner {

    public static void main(String[] args) throws ParseException {
        Lexer l = new Lexer(new CodeDialog());
        for (Token t; (t = l.read()) != Token.EOF; ) {
            System.out.println("=> " + t.getText());
        }
    }
}
