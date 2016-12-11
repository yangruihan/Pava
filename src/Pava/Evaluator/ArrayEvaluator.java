package Pava.Evaluator;

import Pava.AST.ASTree;
import Pava.AST.ArrayLiteral;
import Pava.AST.ArrayRef;
import Pava.AST.PrimaryExpr;
import Pava.Environment.Environment;
import Pava.Exception.PavaException;
import Pava.Parser.ArrayParser;
import javassist.gluonj.Require;
import javassist.gluonj.Reviser;

import java.util.List;

/**
 * Created by yangruihan on 2016/12/11.
 */
@Require({FuncEvaluator.class, ArrayParser.class})
@Reviser
public class ArrayEvaluator {
    @Reviser
    public static class ArrayLitEx extends ArrayLiteral {
        public ArrayLitEx(List<ASTree> children) {
            super(children);
        }

        public Object eval(Environment environment) {
            int s = numChildren();
            Object[] result = new Object[s];
            int i = 0;
            for (ASTree asTree : this) {
                result[i++] = ((BasicEvaluator.ASTreeEx) asTree).eval(environment);
            }
            return result;
        }
    }

    @Reviser
    public static class ArrayRefEx extends ArrayRef {
        public ArrayRefEx(List<ASTree> children) {
            super(children);
        }

        public Object eval(Environment environment, Object value) {
            if (value instanceof Object[]) {
                Object index = ((BasicEvaluator.ASTreeEx) index()).eval(environment);
                if (index instanceof Integer) {
                    return ((Object[]) value)[(Integer) index];
                }
            }
            throw new PavaException("bad array access", this);
        }
    }

    @Reviser
    public static class AssignEx extends BasicEvaluator.BinaryEx {
        public AssignEx(List<ASTree> children) {
            super(children);
        }

        @Override
        protected Object computeAssign(Environment environment, Object rvalue) {
            ASTree le = left();
            if (le instanceof PrimaryExpr) {
                FuncEvaluator.PrimaryEx p = (FuncEvaluator.PrimaryEx) le;
                if (p.hasPostfix(0) && p.postfix(0) instanceof ArrayRef) {
                    Object a = ((FuncEvaluator.PrimaryEx) le).evalSubExpr(environment, 1);
                    if (a instanceof Object[]) {
                        ArrayRef arrayRef = (ArrayRef) p.postfix(0);
                        Object index = ((BasicEvaluator.ASTreeEx) arrayRef.index()).eval(environment);
                        if (index instanceof Integer) {
                            ((Object[]) a)[(Integer) index] = rvalue;
                            return rvalue;
                        }
                    }

                    throw new PavaException("bad array access", this);
                }
            }

            return super.computeAssign(environment, rvalue);
        }
    }
}
