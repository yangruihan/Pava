package Pava.AST;

import Pava.Token.Token;

/**
 * Word literal for abstract syntax tree
 * Created by yangruihan on 2016/11/21.
 */
public class Name extends ASTLeaf {
    public Name(Token token) {
        super(token);
    }

    public String name() {
        return token.getText();
    }
}
