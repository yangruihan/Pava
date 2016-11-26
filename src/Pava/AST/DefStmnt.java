package Pava.AST;

import java.util.List;

/**
 * Define statement node for abstract syntax tree
 * Created by yangruihan on 2016/11/26.
 */
public class DefStmnt extends ASTList {
    public DefStmnt(List<ASTree> children) {
        super(children);
    }

    public String name() {
        return ((ASTLeaf) child(0)).token().getText();
    }

    public ParameterList parameters() {
        return (ParameterList) child(1);
    }

    public BlockStmnt body() {
        return (BlockStmnt) child(2);
    }

    @Override
    public String toString() {
        return "(def " + name() + " " + parameters() + " " + body() + ")";
    }
}
