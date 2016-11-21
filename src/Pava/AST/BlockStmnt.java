package Pava.AST;

import java.util.List;

/**
 * Block expression node for abstract syntax tree
 * Created by yangruihan on 2016/11/22.
 */
public class BlockStmnt extends ASTList {
    public BlockStmnt(List<ASTree> children) {
        super(children);
    }
}
