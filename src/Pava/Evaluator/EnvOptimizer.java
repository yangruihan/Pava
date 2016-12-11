package Pava.Evaluator;

import Pava.AST.*;
import Pava.Environment.Environment;
import Pava.Exception.PavaException;
import Pava.Model.OptFunction;
import Pava.Model.Symbols;
import Pava.Token.Token;
import javassist.gluonj.Require;
import javassist.gluonj.Reviser;

import java.util.List;

import static javassist.gluonj.GluonJ.revise;

/**
 * Created by yangruihan on 2016/12/11.
 */
@Require(ClosureEvaluator.class)
@Reviser
public class EnvOptimizer {
    @Reviser
    public interface EnvEx2 extends Environment {
        Symbols symbols();

        void put(int nest, int index, Object value);

        Object get(int nest, int index);

        void putNew(String name, Object value);

        Environment where(String name);
    }

    @Reviser
    public static abstract class ASTreeOptEx extends ASTree {
        public void lookup(Symbols symbols) {
        }
    }

    @Reviser
    public static class ASTListOptEx extends ASTList {
        public ASTListOptEx(List<ASTree> children) {
            super(children);
        }

        public void lookup(Symbols symbols) {
            for (ASTree tree : this) {
                ((ASTreeOptEx) tree).lookup(symbols);
            }
        }
    }

    @Reviser
    public static class DefStmntEx extends DefStmnt {
        protected int index, size;

        public DefStmntEx(List<ASTree> children) {
            super(children);
        }

        public void lookup(Symbols symbols) {
            index = symbols.putNew(name());
            size = FunEx.lookup(symbols, parameters(), body());
        }

        public Object eval(Environment environment) {
            ((EnvEx2) environment).put(0, index, new OptFunction(parameters(), body(), environment, size));
            return name();
        }
    }

    @Reviser
    public static class FunEx extends Fun {
        protected int size = -1;

        public FunEx(List<ASTree> children) {
            super(children);
        }

        public void lookup(Symbols symbols) {
            size = lookup(symbols, parameters(), body());
        }

        public Object eval(Environment environment) {
            return new OptFunction(parameters(), body(), environment, size);
        }

        public static int lookup(Symbols symbols, ParameterList parameterList, BlockStmnt body) {
            Symbols newSymbols = new Symbols(symbols);
            ((ParamsEx) parameterList).lookup(newSymbols);
            ((ASTreeOptEx) revise(body)).lookup(newSymbols);
            return newSymbols.size();
        }
    }

    @Reviser
    public static class ParamsEx extends ParameterList {
        protected int[] offsets = null;

        public ParamsEx(List<ASTree> children) {
            super(children);
        }

        public void lookup(Symbols symbols) {
            int s = size();
            offsets = new int[s];
            for (int i = 0; i < s; i++) {
                offsets[i] = symbols.putNew(name(i));
            }
        }

        public void eval(Environment environment, int index, Object value) {
            ((EnvEx2) environment).put(0, offsets[index], value);
        }
    }

    @Reviser
    public static class NameEx extends Name {
        protected static final int UNKNOWN = -1;

        protected int index, nest;

        public NameEx(Token token) {
            super(token);
            index = UNKNOWN;
        }

        public void lookup(Symbols symbols) {
            Symbols.Location location = symbols.get(name());
            if (location == null) {
                throw new PavaException("undefined name: " + name(), this);
            } else {
                index = location.index;
                nest = location.nest;
            }
        }

        public void lookupForAssign(Symbols symbols) {
            Symbols.Location location = symbols.put(name());
            index = location.index;
            nest = location.nest;
        }

        public Object eval(Environment environment) {
            if (index == UNKNOWN) {
                return environment.get(name());
            } else {
                return ((EnvEx2) environment).get(nest, index);
            }
        }

        public void evalForAssign(Environment environment, Object value) {
            if (index == UNKNOWN) {
                environment.put(name(), value);
            } else {
                ((EnvEx2) environment).put(nest, index, value);
            }
        }
    }

    @Reviser
    public static class BinaryEx2 extends BasicEvaluator.BinaryEx {
        public BinaryEx2(List<ASTree> children) {
            super(children);
        }

        public void lookup(Symbols symbols) {
            ASTree left = left();
            if ("=".equals(operator())) {
                if (left instanceof Name) {
                    ((NameEx) left).lookupForAssign(symbols);
                    ((ASTreeOptEx) right()).lookup(symbols);
                    return;
                }
            }
            ((ASTreeOptEx) left).lookup(symbols);
            ((ASTreeOptEx) right()).lookup(symbols);
        }

        @Override
        protected Object computeAssign(Environment environment, Object rvalue) {
            ASTree left = left();
            if (left instanceof Name) {
                ((NameEx) left).evalForAssign(environment, rvalue);
                return rvalue;
            } else {
                return super.computeAssign(environment, rvalue);
            }
        }
    }
}
