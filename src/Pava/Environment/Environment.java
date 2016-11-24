package Pava.Environment;

/**
 * An environment for program
 * Created by yangruihan on 2016/11/24.
 */
public interface Environment {
    void put(String name, Object value);

    Object get(String name);
}
