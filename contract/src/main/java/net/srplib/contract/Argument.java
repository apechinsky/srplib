package net.srplib.contract;

/**
 * Utility methods to validate methods' arguments.
 *
 * @author Anton Pechinsky
 */
public final class Argument {

    private Argument() {
        // prevent instantiation
    }

    /**
     * Ensures that specified parameter is null.
     *
     * @param object Object to test
     * @param name String representing parameter name.
     * @throws IllegalArgumentException if object is not null.
     */
    public static void isNull(Object object, String name) {
        if (object != null) {
            throw new IllegalArgumentException("Argument '" + name + "' must be null!");
        }
    }

    /**
     * Ensures that specified parameter is NOT null.
     *
     * @param object Object to test
     * @param name String representing parameter name.
     * @throws IllegalArgumentException if object null.
     */
    public static void notNull(Object object, String name) {
        if (object == null) {
            throw new IllegalArgumentException("Argument '" + name + "' must not be null!");
        }
    }

    /**
     * Ensures that specified parameter expression is true.
     *
     * @param expression Object to test
     * @param message String representing message to pass to exception.
     * @throws IllegalArgumentException if object is false.
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Ensures that specified parameter expression is false.
     *
     * @param expression Object to test
     * @param message String representing message to pass to exception.
     * @throws IllegalArgumentException if object is false.
     */
    public static void isFalse(boolean expression, String message) {
        isTrue(!expression, message);
    }
}
