package nl.elmar.model;

public class EParserException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EParserException() {
    }

    public EParserException(String s) {
        super(s);
    }

    public EParserException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public EParserException(Throwable throwable) {
        super(throwable);
    }
}