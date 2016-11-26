package Pava.Runner;

import Pava.ClosureInterpreter;
import Pava.Evaluator.ClosureEvaluator;
import javassist.gluonj.util.Loader;

/**
 * Created by yangruihan on 2016/11/26.
 */
public class ClosureRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(ClosureInterpreter.class, args, ClosureEvaluator.class);
    }
}
