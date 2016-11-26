package Pava.Interpreter;

import Pava.Environment.NestedEnv;
import Pava.Exception.ParseException;
import Pava.Parser.ClosureParser;
import Pava.Tools.Natives;

/**
 * Created by yangruihan on 2016/11/26.
 */
public class NativeInterpreter extends BasicInterpreter {
    public static void main(String[] args) throws ParseException {
        run(new ClosureParser(), new Natives().environment(new NestedEnv()));
    }
}
