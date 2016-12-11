package Pava.Interpreter;

import Pava.Environment.NestedEnv;
import Pava.Exception.ParseException;
import Pava.Parser.ClassParser;
import Pava.Tools.Natives;

/**
 * Created by yangruihan on 2016/12/11.
 */
public class ClassInterpreter extends BasicInterpreter {
    public static void main(String[] args) throws ParseException {
        run(new ClassParser(), new Natives().environment(new NestedEnv()));
    }
}
