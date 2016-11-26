package Pava.Runner;

import Pava.Evaluator.ClosureEvaluator;
import Pava.Evaluator.NativeEvaluator;
import Pava.Interpreter.NativeInterpreter;
import javassist.gluonj.util.Loader;

/**
 * Created by yangruihan on 2016/11/26.
 */
public class NativeRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(NativeInterpreter.class, args, NativeEvaluator.class, ClosureEvaluator.class);
    }
}
