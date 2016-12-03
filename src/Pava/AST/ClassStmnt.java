package Pava.AST;

import java.util.List;

/**
 * Class statement node for abstract syntax tree
 * Created by yangruihan on 2016/12/3.
 */
public class ClassStmnt extends ASTList {
    public ClassStmnt(List<ASTree> children) {
        super(children);
    }

    public String name() {
        return ((ASTLeaf) child(0)).token().getText();
    }

    /**
     * get the super class name for this class
     *
     * @return the name of super class
     */
    public String superClass() {
        if (numChildren() < 3)
            return null;
        else
            return ((ASTLeaf) child(0)).token().getText();
    }

    public ClassBody body() {
        return (ClassBody) child(numChildren() - 1);
    }

    @Override
    public String toString() {
        String parent = superClass();
        if (parent == null) {
            parent = "*";
        }
        return "(class " + name() + " " + parent + " " + body() + ")";
    }
}
