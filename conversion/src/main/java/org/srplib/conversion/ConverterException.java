package org.srplib.conversion;

/**
 * This exception is used to report conversion errors.
 *
 * @author Anton Pechinsky
 */
public class ConverterException extends RuntimeException {

    public ConverterException() {
    }

    public ConverterException(String message) {
        super(message);
    }

    public ConverterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConverterException(Throwable cause) {
        super(cause);
    }
}
