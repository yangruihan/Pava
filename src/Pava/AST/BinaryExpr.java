package Pava.AST;

import Pava.Token.Token;

import java.util.List;

/**
 * Binary expression node for abstract tree
 * for example: 2 + 3
 * Created by yangruihan on 2016/11/21.
 */
public class BinaryExpr extends ASTList {
    public BinaryExpr(List<ASTree> children) {
        super(children);
    }

    public ASTree left() {
        return child(0);
    }

    public String operator() {
        return ((ASTLeaf) child(1)).token().getText();
    }

    public ASTree right() {
        return child(2);
    }
}
