package Pava.Interpreter;

import Pava.AST.ASTree;
import Pava.AST.NullStmnt;
import Pava.Environment.Environment;
import Pava.Environment.ResizableArrayEnv;
import Pava.Evaluator.BasicEvaluator;
import Pava.Evaluator.EnvOptimizer;
import Pava.Exception.ParseException;
import Pava.Lexer;
import Pava.Parser.BasicParser;
import Pava.Parser.ClosureParser;
import Pava.Token.Token;
import Pava.Tools.Natives;
import Pava.UI.CodeDialog;

/**
 * Created by yangruihan on 2016/12/11.
 */
public class EnvOptInterpreter {
    public static void main(String[] args) throws ParseException {
        run(new ClosureParser(), new Natives().environment(new ResizableArrayEnv()));
    }

    public static void run(BasicParser parser, Environment environment) throws ParseException {
        Lexer lexer = new Lexer(new CodeDialog());
        while (lexer.peek(0) != Token.EOF) {
            ASTree tree = parser.parse(lexer);
            if (!(tree instanceof NullStmnt)) {
                ((EnvOptimizer.ASTreeOptEx) tree).lookup(((EnvOptimizer.EnvEx2) environment).symbols());
                Object r = ((BasicEvaluator.ASTreeEx) tree).eval(environment);
                System.out.println("=> " + r);
            }
        }
    }
}
