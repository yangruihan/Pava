package Pava.Environment;

import Pava.Evaluator.EnvOptimizer;
import Pava.Exception.PavaException;
import Pava.Model.Symbols;

/**
 * Created by yangruihan on 2016/12/11.
 */
public class ArrayEnv implements Environment {
    protected Object[] values;
    protected Environment outer;

    public ArrayEnv(int size, Environment environment) {
        values = new Object[size];
        outer = environment;
    }

    public Symbols symbols() {
        throw new PavaException("no symbols");
    }

    @Override
    public void put(String name, Object value) {
        error(name);
    }

    public void putNew(String name, Object value) {
        error(name);
    }

    public void put(int nest, int index, Object value) {
        if (nest == 0) {
            values[index] = value;
        } else if (outer == null) {
            throw new PavaException("no outer enviroment");
        } else {
            ((EnvOptimizer.EnvEx2) outer).put(nest - 1, index, value);
        }
    }

    @Override
    public Object get(String name) {
        error(name);
        return null;
    }

    public Object get(int nest, int index) {
        if (nest == 0) {
            return values[index];
        } else if (outer == null) {
            return null;
        } else {
            return ((EnvOptimizer.EnvEx2) outer).get(nest - 1, index);
        }
    }

    public Environment where(String name) {
        error(name);
        return null;
    }

    public void setOuter(Environment environment) {
        outer = environment;
    }

    private void error(String name) {
        throw new PavaException("cannot access by name: " + name);
    }
}
