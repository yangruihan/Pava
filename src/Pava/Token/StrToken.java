package Pava.Token;

/**
 * string token
 * Created by yangruihan on 2016/11/20.
 */
public class StrToken extends Token {

    private String text;

    public StrToken(int lineNo, String text) {
        super(lineNo);
        this.text = text;
    }

    @Override
    public boolean isString() {
        return true;
    }

    @Override
    public String getText() {
        return this.text;
    }
}
