package Pava.AST;

import java.util.List;

/**
 * Created by yangruihan on 2016/12/3.
 */
public class Dot extends Postfix {
    public Dot(List<ASTree> children) {
        super(children);
    }

    public String name() {
        return ((ASTLeaf) child(0)).token().getText();
    }

    @Override
    public String toString() {
        return "." + name();
    }
}
