package Pava.Environment;

import Pava.Evaluator.EnvOptimizer;
import Pava.Model.Symbols;

import java.util.Arrays;

/**
 * Created by yangruihan on 2016/12/11.
 */
public class ResizableArrayEnv extends ArrayEnv {
    protected Symbols names;

    public ResizableArrayEnv() {
        super(10, null);
        names = new Symbols();
    }

    @Override
    public Symbols symbols() {
        return names;
    }

    @Override
    public Object get(String name) {
        Integer index = names.find(name);
        if (index == null) {
            if (outer == null) {
                return null;
            } else {
                return outer.get(name);
            }
        } else {
            return values[index];
        }
    }

    @Override
    public void put(String name, Object value) {
        Environment environment = where(name);
        if (environment == null) {
            environment = this;
        }
        ((EnvOptimizer.EnvEx2) environment).putNew(name, value);
    }

    @Override
    public void putNew(String name, Object value) {
        assign(names.putNew(name), value);
    }

    @Override
    public Environment where(String name) {
        if (names.find(name) != null) {
            return this;
        } else if (outer == null) {
            return null;
        } else {
            return ((EnvOptimizer.EnvEx2) outer).where(name);
        }
    }

    @Override
    public void put(int nest, int index, Object value) {
        if (nest == 0) {
            assign(index, value);
        } else {
            super.put(nest, index, value);
        }
    }

    protected void assign(int index, Object value) {
        if (index >= values.length) {
            int newLen = values.length * 2;
            if (index >= newLen) {
                newLen = index + 1;
            }
            values = Arrays.copyOf(values, newLen);
        }
        values[index] = value;
    }
}
