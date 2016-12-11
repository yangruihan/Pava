package Pava.Runner;

import Pava.Evaluator.EnvOptimizer;
import Pava.Evaluator.NativeEvaluator;
import Pava.Interpreter.EnvOptInterpreter;
import javassist.gluonj.util.Loader;

/**
 * Created by yangruihan on 2016/12/11.
 */
public class EnvOptRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(EnvOptInterpreter.class, args, EnvOptimizer.class, NativeEvaluator.class);
    }
}
