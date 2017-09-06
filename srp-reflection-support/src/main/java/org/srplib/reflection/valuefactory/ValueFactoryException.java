package org.srplib.reflection.valuefactory;

/**
 * @author Anton Pechinsky
 */
public class ValueFactoryException extends RuntimeException {

    public ValueFactoryException(String message) {
        super(message);
    }

    public ValueFactoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
