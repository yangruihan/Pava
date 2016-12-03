package Pava.Model;

import Pava.AST.ClassBody;
import Pava.AST.ClassStmnt;
import Pava.Environment.Environment;
import Pava.Exception.PavaException;

/**
 * Created by yangruihan on 2016/12/3.
 */
public class ClassInfo {
    protected ClassStmnt definition;
    protected Environment environment;
    protected ClassInfo superClass;

    public ClassInfo(ClassStmnt classStmnt, Environment environment) {
        this.definition = classStmnt;
        this.environment = environment;

        Object obj = environment.get(classStmnt.superClass());
        if (obj == null) {
            this.superClass = null;
        } else if (obj instanceof ClassInfo) {
            this.superClass = (ClassInfo) obj;
        } else {
            throw new PavaException("unknow super class: " + classStmnt.superClass(), classStmnt);
        }
    }

    public String name() {
        return this.definition.name();
    }

    public ClassInfo superClass() {
        return this.superClass;
    }

    public ClassBody body() {
        return this.definition.body();
    }

    public Environment environment() {
        return environment;
    }

    @Override
    public String toString() {
        return "<class " + name() + ">";
    }
}
