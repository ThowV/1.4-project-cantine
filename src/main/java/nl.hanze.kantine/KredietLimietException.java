package nl.hanze.kantine;

public class KredietLimietException extends RuntimeException {

    /**
     * Constructor
     */
    public KredietLimietException() {
        super();
    }

    /**
     * Constructor
     * @param exception the Throwable Exception that will be thrown.
     */
    public KredietLimietException(Exception exception) {
        super(exception);
    }

    /**
     * Constructor
     * @param message the message included with the exception.
     */
    public KredietLimietException(String message) {
        super(message);
    }
}
