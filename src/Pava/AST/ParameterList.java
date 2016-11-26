package Pava.AST;

import java.util.List;

/**
 * Parameter list node for abstract syntax tree
 * Created by yangruihan on 2016/11/26.
 */
public class ParameterList extends ASTList {
    public ParameterList(List<ASTree> children) {
        super(children);
    }

    public String name(int i) {
        return ((ASTLeaf) child(i)).token().getText();
    }

    public int size() {
        return numChildren();
    }
}
