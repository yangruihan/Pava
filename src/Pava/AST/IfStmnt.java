package Pava.AST;

import java.util.List;

/**
 * If expression node for abstract syntax tree
 * Created by yangruihan on 2016/11/22.
 */
public class IfStmnt extends ASTList {
    public IfStmnt(List<ASTree> children) {
        super(children);
    }

    public ASTree condition() {
        return child(0);
    }

    public ASTree thenBlock() {
        return child(1);
    }

    public ASTree elseBlock() {
        return numChildren() > 2 ? child(2) : null;
    }

    @Override
    public String toString() {
        return "(if " + condition() + " " + thenBlock() + " else " + elseBlock() + ")";
    }
}
