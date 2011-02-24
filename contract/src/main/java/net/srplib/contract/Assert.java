package net.srplib.contract;

/**
 * Helper methods to maintain invariants.
 *
 * @author Anton Pechinsky
 */
public final class Assert {

    private Assert() {
        // prevent instantiation
    }

    /**
     * Ensures that specified object isnull.
     *
     * @param object Object to test
     * @param message String representing message to pass to exception.
     * @throws IllegalStateException if object is not null.
     */
    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new IllegalStateException(message);
        }
    }

    /**
     * Ensures that specified object is NOT null.
     *
     * @param object Object to test
     * @param message String representing message to pass to exception.
     * @throws IllegalStateException if object null.
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalStateException(message);
        }
    }

    /**
     * Ensures that specified expression is true.
     *
     * @param expression boolean expression
     * @param message String representing message to pass to exception.
     * @throws IllegalStateException if object is false.
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }

    /**
     * Throws <code>IllegalStateException</code> unconditionally.
     *
     * @param message String representing message to pass to exception.
     * @throws IllegalStateException
     */
    public static void fail(String message) {
        isTrue(false, message);
    }

    /**
     * Throws <code>UnsupportedOperationException</code> unconditionally. A quick way to implements method :)
     *
     * @param message String message
     * @throws IllegalStateException
     */
    public static void throwNotImplemented(String message) {
        throw new UnsupportedOperationException(message);
    }

    /**
     * Throws <code>UnsupportedOperationException</code>.exception with default message ("Not implemented yet.").
     *
     * @throws UnsupportedOperationException
     */
    public static void throwNotImplemented() {
        throwNotImplemented("Not implemented yet.");
    }

    /**
     * Throws <code>UnsupportedOperationException</code> with default message ("Unsupported operation").
     *
     * @throws UnsupportedOperationException
     */
    public static void throwUnsupportedOperationException() {
        throw new UnsupportedOperationException("Unsupported operation.");
    }

    /**
     * Ensures that specified objects are equal.
     *
     * @param object Object to test
     * @param expected Expected object
     * @throws IllegalStateException if object is not null.
     */
    public static void isEqual(Object object, Object expected) {
        isEqual(object, expected, "Assertion failed.");
    }

    /**
     * Ensures that specified objects are equal.
     *
     * @param object Object to test
     * @param expected Expected object
     * @param message Additional message.
     * @throws IllegalStateException if object is not null.
     */
    public static void isEqual(Object object, Object expected, String message) {
        boolean equals = object == null ? expected == null : object.equals(expected);
        if (!equals) {
            throw new IllegalStateException(message + String.format(" Expected '%s' but got '%s'", expected, object));
        }
    }
}
