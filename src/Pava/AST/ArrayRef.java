package Pava.AST;

import java.util.List;

/**
 * Array node for abstract syntax tree
 * Created by yangruihan on 2016/12/11.
 */
public class ArrayRef extends Postfix {
    public ArrayRef(List<ASTree> children) {
        super(children);
    }

    public ASTree index() {
        return child(0);
    }

    @Override
    public String toString() {
        return "[" + index() + "]";
    }
}
