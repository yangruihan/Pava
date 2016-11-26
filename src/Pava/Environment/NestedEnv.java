package Pava.Environment;

import java.util.HashMap;

import Pava.Evaluator.FuncEvaluator.EnvEx;

/**
 * Nested environment to support local variable and global variable
 * Created by yangruihan on 2016/11/26.
 */
public class NestedEnv implements Environment {
    protected HashMap<String, Object> values;
    protected Environment outer;

    public NestedEnv() {
        this(null);
    }

    public NestedEnv(Environment environment) {
        values = new HashMap<>();
        outer = environment;
    }

    public void setOuter(Environment outer) {
        this.outer = outer;
    }

    public void putNew(String name, Object value) {
        values.put(name, value);
    }

    public Environment where(String name) {
        if (values.get(name) != null) {
            return this;
        } else if (outer == null) {
            return null;
        } else {
            return ((EnvEx) outer).where(name);
        }
    }

    @Override
    public void put(String name, Object value) {
        Environment e = where(name);
        if (e == null) {
            e = this;
        }
        ((EnvEx) e).putNew(name, value);
    }

    @Override
    public Object get(String name) {
        Object v = values.get(name);
        if (v == null && outer != null) {
            return outer.get(name);
        } else {
            return v;
        }
    }
}
