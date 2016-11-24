package Pava.Test;

import Pava.BasicEvaluator;
import Pava.BasicInterpreter;
import javassist.gluonj.util.Loader;

/**
 * Created by yangruihan on 2016/11/24.
 */
public class Runner {
    public static void main(String[] args) throws Throwable {
        Loader.run(BasicInterpreter.class, args, BasicEvaluator.class);
    }
}
