package Pava.Runner;

import Pava.Evaluator.FuncEvaluator;
import Pava.Interpreter.FuncInterpreter;
import javassist.gluonj.util.Loader;

/**
 * Created by yangruihan on 2016/11/26.
 */
public class FuncRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(FuncInterpreter.class, args, FuncEvaluator.class);
    }
}
