package Pava.AST;

import java.util.Iterator;

/**
 * Abstract syntax tree for Pava
 * Created by yangruihan on 2016/11/21.
 */
public abstract class ASTree implements Iterable<ASTree> {
    public abstract ASTree child(int i);

    public abstract int numChildren();

    public abstract Iterator<ASTree> children();

    public abstract String location();

    public Iterator<ASTree> iterator() {
        return children();
    }
}
