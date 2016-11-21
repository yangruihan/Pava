package Pava.AST;

import Pava.Token.Token;

/**
 * String literal node for abstract syntax tree
 * Created by yangruihan on 2016/11/22.
 */
public class StringLiteral extends ASTLeaf {
    public StringLiteral(Token token) {
        super(token);
    }

    public String value() {
        return token().getText();
    }
}
