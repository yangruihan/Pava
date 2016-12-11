package Pava.Runner;

import Pava.Evaluator.ClassEvaluator;
import Pava.Evaluator.ClosureEvaluator;
import Pava.Evaluator.NativeEvaluator;
import Pava.Interpreter.ClassInterpreter;
import javassist.gluonj.util.Loader;

/**
 * Created by yangruihan on 2016/12/11.
 */
public class ClassRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(ClassInterpreter.class, args, ClassEvaluator.class, NativeEvaluator.class, ClosureEvaluator.class);
    }
}
