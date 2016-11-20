package Pava.Token;

/**
 * Token class
 * Created by yangruihan on 2016/11/20.
 */
public abstract class Token {
    public static final Token EOF = new Token(-1) {
    };       // end of file
    public static final String EOL = "\\n";                 // end of line

    private int lineNumber;     // the line number of this token

    protected Token(int line) {
        lineNumber = line;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    /* type of token */
    public boolean isIdentifier() {
        return false;
    }

    public boolean isNumber() {
        return false;
    }

    public boolean isString() {
        return false;
    }

    /* get value from token */
    public int getNumber() {
        return -1;
    }

    public String getText() {
        return "";
    }
}
