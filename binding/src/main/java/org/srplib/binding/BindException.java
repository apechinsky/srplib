package org.srplib.binding;

/**
 * Base exception for binding library.
 *
 * <p>All other binding exceptions should subclass this exception.</p>
 *
 * @author Q-APE
 */
public class BindException extends RuntimeException {

    public BindException() {
    }

    public BindException(String message) {
        super(message);
    }

    public BindException(String message, Throwable cause) {
        super(message, cause);
    }

    public BindException(Throwable cause) {
        super(cause);
    }
}
