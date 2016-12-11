package Pava.Model;

import Pava.AST.BlockStmnt;
import Pava.AST.ParameterList;
import Pava.Environment.ArrayEnv;
import Pava.Environment.Environment;

/**
 * Created by yangruihan on 2016/12/11.
 */
public class OptFunction extends Function {
    protected int size;

    public OptFunction(ParameterList parameters, BlockStmnt body, Environment environment, int memorySize) {
        super(parameters, body, environment);
        size = memorySize;
    }

    @Override
    public Environment makeEnv() {
        return new ArrayEnv(size, environment);
    }
}
