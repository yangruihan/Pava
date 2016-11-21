package Pava.AST;

import java.util.List;

/**
 * - node for abstract syntax tree
 * Created by yangruihan on 2016/11/22.
 */
public class NegativeExpr extends ASTList {
    public NegativeExpr(List<ASTree> children) {
        super(children);
    }

    public ASTree operand() {
        return child(0);
    }

    @Override
    public String toString() {
        return "-" + operand();
    }
}
