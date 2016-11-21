package Pava.AST;

import Pava.Token.Token;

/**
 * Number literal node for abstract syntax tree
 * Created by yangruihan on 2016/11/21.
 */
public class NumberLiteral extends ASTLeaf {

    public NumberLiteral(Token token) {
        super(token);
    }

    public int value() {
        return token.getNumber();
    }
}
