package Pava.Evaluator;

import Pava.AST.*;
import Pava.Environment.Environment;
import Pava.Environment.NestedEnv;
import Pava.Evaluator.FuncEvaluator.*;
import Pava.Evaluator.BasicEvaluator.*;
import Pava.Exception.PavaException;
import Pava.Model.ClassInfo;
import Pava.Model.PavaObject;
import Pava.Model.PavaObject.AccessException;
import javassist.gluonj.Require;
import javassist.gluonj.Reviser;

import java.util.List;

/**
 * Created by yangruihan on 2016/12/3.
 */
@Require(FuncEvaluator.class)
@Reviser
public class ClassEvaluator {

    @Reviser
    public static class ClassStmntEx extends ClassStmnt {
        public ClassStmntEx(List<ASTree> children) {
            super(children);
        }

        public Object eval(Environment environment) {
            ClassInfo classInfo = new ClassInfo(this, environment);
            environment.put(name(), classInfo);
            return name();
        }
    }

    @Reviser
    public static class ClassBodyEx extends ClassBody {
        public ClassBodyEx(List<ASTree> children) {
            super(children);
        }

        public Object eval(Environment environment) {
            for (ASTree tree : this) {
                ((ASTreeEx) tree).eval(environment);
            }
            return null;
        }
    }

    @Reviser
    public static class DotEx extends Dot {
        public DotEx(List<ASTree> children) {
            super(children);
        }

        public Object eval(Environment environment, Object value) {
            String member = name();
            if (value instanceof ClassInfo) {
                if (member.equals("new")) {
                    ClassInfo classInfo = (ClassInfo) value;
                    NestedEnv nestedEnv = new NestedEnv(classInfo.environment());
                    PavaObject po = new PavaObject(nestedEnv);
                    nestedEnv.put("this", po);
                    initObject(classInfo, nestedEnv);
                    return po;
                }
            } else if (value instanceof PavaObject) {
                try {
                    return ((PavaObject) value).read(member);
                } catch (AccessException e) {
                }
            }
            throw new PavaException("bad member access: " + member, this);
        }

        protected void initObject(ClassInfo classInfo, Environment environment) {
            if (classInfo.superClass() != null) {
                initObject(classInfo.superClass(), environment);
            }
            ((ClassBodyEx) classInfo.body()).eval(environment);
        }
    }

    @Reviser
    public static class AssignEx extends BinaryEx {
        public AssignEx(List<ASTree> children) {
            super(children);
        }

        @Override
        protected Object computeAssign(Environment environment, Object rvalue) {
            ASTree leftTree = left();
            if (leftTree instanceof PrimaryExpr) {
                PrimaryEx primaryEx = (PrimaryEx) leftTree;
                if (primaryEx.hasPostfix(0) && primaryEx.postfix(0) instanceof Dot) {
                    Object t = ((PrimaryEx) leftTree).evalSubExpr(environment, 1);
                    if (t instanceof PavaObject) {
                        return setFeild((PavaObject) t, (Dot) primaryEx.postfix(0), rvalue);
                    }
                }
            }
            return super.computeAssign(environment, rvalue);
        }

        protected Object setFeild(PavaObject obj, Dot expr, Object rvalue) {
            String name = expr.name();
            try {
                obj.write(name, rvalue);
                return rvalue;
            } catch (AccessException e) {
                throw new PavaException("bad member access " + location() + ": " + name);
            }
        }
    }
}
