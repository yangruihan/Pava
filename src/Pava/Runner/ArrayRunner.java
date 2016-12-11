package Pava.Runner;

import Pava.Evaluator.ArrayEvaluator;
import Pava.Evaluator.ClassEvaluator;
import Pava.Evaluator.ClosureEvaluator;
import Pava.Evaluator.NativeEvaluator;
import Pava.Interpreter.ClassInterpreter;
import javassist.gluonj.util.Loader;

/**
 * Created by yangruihan on 2016/12/11.
 */
public class ArrayRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(ClassInterpreter.class, args, ClassEvaluator.class, ArrayEvaluator.class, NativeEvaluator.class, ClosureEvaluator.class);
    }
}
