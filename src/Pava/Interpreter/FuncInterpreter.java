package Pava.Interpreter;

import Pava.Environment.NestedEnv;
import Pava.Exception.ParseException;
import Pava.Parser.FuncParser;

/**
 * An interpreter for supporting function define
 * Created by yangruihan on 2016/11/26.
 */
public class FuncInterpreter extends BasicInterpreter {
    public static void main(String[] args) throws ParseException {
        run(new FuncParser(), new NestedEnv());
    }
}
