package Pava.Model;

import Pava.AST.BlockStmnt;
import Pava.AST.ParameterList;
import Pava.Environment.Environment;
import Pava.Environment.NestedEnv;

/**
 * Function class
 * Created by yangruihan on 2016/11/26.
 */
public class Function {
    protected ParameterList parameters;
    protected BlockStmnt body;
    protected Environment environment;

    public Function(ParameterList parameters, BlockStmnt body, Environment environment) {
        this.parameters = parameters;
        this.body = body;
        this.environment = environment;
    }

    public ParameterList parameters() {
        return parameters;
    }

    public BlockStmnt body() {
        return body;
    }

    public Environment makeEnv() {
        return new NestedEnv(environment);
    }

    @Override
    public String toString() {
        return "<fun:" + hashCode() + ">";
    }
}
