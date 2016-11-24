package Pava.Environment;

import java.util.HashMap;

/**
 * Basic environment for program
 * Created by yangruihan on 2016/11/24.
 */
public class BasicEnv implements Environment {
    protected HashMap<String, Object> values;

    public BasicEnv() {
        values = new HashMap<>();
    }

    @Override
    public void put(String name, Object value) {
        values.put(name, value);
    }

    @Override
    public Object get(String name) {
        return values.get(name);
    }
}
