package Pava;

import Pava.Environment.NestedEnv;
import Pava.Exception.ParseException;

/**
 * Created by yangruihan on 2016/11/26.
 */
public class ClosureInterpreter extends BasicInterpreter {
    public static void main(String[] args) throws ParseException {
        run(new ClosureParser(), new NestedEnv());
    }
}
