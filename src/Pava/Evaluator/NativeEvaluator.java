package Pava.Evaluator;

import Pava.AST.ASTree;
import Pava.Environment.Environment;
import Pava.Exception.PavaException;
import Pava.Evaluator.BasicEvaluator.*;
import Pava.Model.NativeFunction;
import javassist.gluonj.Require;
import javassist.gluonj.Reviser;

import java.util.List;

/**
 * Created by yangruihan on 2016/11/26.
 */

@Require(FuncEvaluator.class)
@Reviser
public class NativeEvaluator {
    @Reviser
    public static class NativeArgEx extends FuncEvaluator.ArgumentsEx {
        public NativeArgEx(List<ASTree> children) {
            super(children);
        }

        @Override
        public Object eval(Environment callerEnv, Object value) {
            if (!(value instanceof NativeFunction)) {
                return super.eval(callerEnv, value);
            }

            NativeFunction func = (NativeFunction) value;
            int nparams = func.numOfParameters();
            if (size() != nparams) {
                throw new PavaException("bad number of arguments", this);
            }

            Object[] args = new Object[nparams];
            int num = 0;
            for (ASTree a : this) {
                ASTreeEx ae = (ASTreeEx) a;
                args[num++] = ae.eval(callerEnv);
            }
            return func.invoke(args, this);
        }
    }
}
