package Pava.Model;

import Pava.Environment.Environment;
import Pava.Evaluator.FuncEvaluator.*;

/**
 * Created by yangruihan on 2016/12/3.
 */
public class PavaObject {
    public static class AccessException extends Exception {
    }

    protected Environment environment;

    public PavaObject(Environment environment) {
        this.environment = environment;
    }

    @Override
    public String toString() {
        return "<Object:" + hashCode() + ">";
    }

    public Object read(String member) throws AccessException {
        return getEnv(member).get(member);
    }

    public void write(String member, Object value) throws AccessException {
        ((EnvEx) getEnv(member)).putNew(member, value);
    }

    protected Environment getEnv(String member) throws AccessException {
        Environment e = ((EnvEx) environment).where(member);
        if (e != null && e == environment) {
            return e;
        } else {
            throw new AccessException();
        }
    }
}
