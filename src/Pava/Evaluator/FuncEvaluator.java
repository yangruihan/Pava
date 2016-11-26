package Pava.Evaluator;

import Pava.AST.*;
import Pava.Environment.Environment;
import Pava.Exception.PavaException;
import Pava.Model.Function;
import javassist.gluonj.Require;
import javassist.gluonj.Reviser;

import java.util.List;

/**
 * Evaluator for supporting define function
 * Created by yangruihan on 2016/11/26.
 */

@Require(BasicEvaluator.class)
@Reviser
public class FuncEvaluator {

    @Reviser
    public interface EnvEx extends Environment {
        void putNew(String name, Object value);

        Environment where(String name);

        void setOuter(Environment environment);
    }

    @Reviser
    public static class DefStmntEx extends DefStmnt {
        public DefStmntEx(List<ASTree> children) {
            super(children);
        }

        public Object eval(Environment environment) {
            ((EnvEx) environment).putNew(name(), new Function(parameters(), body(), environment));
            return name();
        }
    }

    @Reviser
    public static class PrimaryEx extends PrimaryExpr {
        public PrimaryEx(List<ASTree> children) {
            super(children);
        }

        public ASTree operand() {
            return child(0);
        }

        public Postfix postfix(int nest) {
            return (Postfix) child(numChildren() - nest - 1);
        }

        public boolean hasPostfix(int nest) {
            return numChildren() - nest > 1;
        }

        public Object eval(Environment environment) {
            return evalSubExpr(environment, 0);
        }

        public Object evalSubExpr(Environment environment, int nest) {
            if (hasPostfix(nest)) {
                Object target = evalSubExpr(environment, nest + 1);
                return ((PostfixEx) postfix(nest)).eval(environment, target);
            } else {
                return ((BasicEvaluator.ASTreeEx) operand()).eval(environment);
            }
        }
    }

    @Reviser
    public static abstract class PostfixEx extends Postfix {
        public PostfixEx(List<ASTree> children) {
            super(children);
        }

        public abstract Object eval(Environment env, Object value);
    }

    @Reviser
    public static class ArgumentsEx extends Arguments {
        public ArgumentsEx(List<ASTree> children) {
            super(children);
        }

        public Object eval(Environment callerEnv, Object value) {
            if (!(value instanceof Function)) {
                throw new PavaException("bad function", this);
            }
            Function func = (Function) value;
            ParameterList parameters = func.parameters();
            if (size() != parameters.size()) {
                throw new PavaException("bad number of arguments", this);
            }
            Environment newEnv = func.makeEnv();
            int num = 0;
            for (ASTree a : this) {
                ((ParamsEx) parameters).eval(newEnv, num++, ((BasicEvaluator.ASTreeEx) a).eval(callerEnv));
            }
            return ((BasicEvaluator.BlockEx) func.body()).eval(newEnv);
        }
    }

    @Reviser
    public static class ParamsEx extends ParameterList {
        public ParamsEx(List<ASTree> children) {
            super(children);
        }

        public void eval(Environment environment, int index, Object value) {
            ((EnvEx) environment).putNew(name(index), value);
        }
    }
}
