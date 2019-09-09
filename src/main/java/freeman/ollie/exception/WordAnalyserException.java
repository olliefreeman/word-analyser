package freeman.ollie.exception;

/**
 * @since 09/09/2019
 */
public class WordAnalyserException extends Exception {

    public WordAnalyserException(String message) {
        super(message);
    }

    public WordAnalyserException(String message, Throwable cause) {
        super(message, cause);
    }
}
