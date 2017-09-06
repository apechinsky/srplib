package org.srplib.reflection;

/**
 * Exception is used to wrap reflection API exceptions.
 *
 * @author Anton Pechinsky
 */
public class ReflectionException extends RuntimeException {

    public ReflectionException() {
    }

    public ReflectionException(String message) {
        super(message);
    }

    public ReflectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReflectionException(Throwable cause) {
        super(cause);
    }
}
