package Pava.AST;

import java.util.List;

/**
 * Created by yangruihan on 2016/12/11.
 */
public class ArrayLiteral extends ASTList {
    public ArrayLiteral(List<ASTree> children) {
        super(children);
    }

    public int size() {
        return numChildren();
    }
}
