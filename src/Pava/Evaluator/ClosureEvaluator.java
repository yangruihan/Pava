package Pava.Evaluator;

import Pava.AST.ASTree;
import Pava.AST.Fun;
import Pava.Environment.Environment;
import Pava.Model.Function;
import javassist.gluonj.Require;
import javassist.gluonj.Reviser;

import java.util.List;

/**
 * Created by yangruihan on 2016/11/26.
 */

@Require(FuncEvaluator.class)
@Reviser
public class ClosureEvaluator {
    @Reviser
    public static class FunEx extends Fun {
        public FunEx(List<ASTree> children) {
            super(children);
        }

        public Object eval(Environment environment) {
            return new Function(parameters(), body(), environment);
        }
    }
}
