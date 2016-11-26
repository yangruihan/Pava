package Pava.AST;

import java.util.List;

/**
 * A closure function node for abstract syntax tree
 * Created by yangruihan on 2016/11/26.
 */
public class Fun extends ASTList {
    public Fun(List<ASTree> children) {
        super(children);
    }

    public ParameterList parameters() {
        return (ParameterList) child(0);
    }

    public BlockStmnt body() {
        return (BlockStmnt) child(1);
    }

    @Override
    public String toString() {
        return "(fun " + parameters() + " " + body() + ")";
    }
}
