package nl.hanze.kantine;

public class TeWeinigGeldException extends RuntimeException  {

    /**
     * Constructor
     */
    public TeWeinigGeldException() {
        super();
    }

    /**
     * Constructor
     * @param exception the Throwable Exception that will be thrown.
     */
    public TeWeinigGeldException(Exception exception) {
        super(exception);
    }

    /**
     * Constructor
     * @param message the message included with the exception.
     */
    public TeWeinigGeldException(String message) {
        super(message);
    }
}
