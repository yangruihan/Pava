package Pava.AST;

import java.util.List;

/**
 * Primary
 * Created by yangruihan on 2016/11/21.
 */
public class PrimaryExpr extends ASTList {
    public static ASTree create(List<ASTree> children) {
        return children.size() == 1 ? children.get(0) : new PrimaryExpr(children);
    }

    public PrimaryExpr(List<ASTree> children) {
        super(children);
    }
}
