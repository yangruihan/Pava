package Pava;

import Pava.AST.ASTree;

/**
 * Pava exception from runtime exception
 * Created by yangruihan on 2016/11/20.
 */
public class PavaException extends RuntimeException {
    public PavaException(String message) {
        super(message);
    }

    public PavaException(String message, ASTree tree) {
        super(message + " " + tree.location());
    }
}
