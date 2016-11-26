package Pava.AST;

import java.util.List;

/**
 * Arguments node for abstract syntax tree
 * Created by yangruihan on 2016/11/26.
 */
public class Arguments extends Postfix {
    public Arguments(List<ASTree> children) {
        super(children);
    }

    public int size() {
        return numChildren();
    }
}
