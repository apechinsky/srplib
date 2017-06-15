package org.srplib.contract;

/**
 * Helper class containing methods for checking method arguments.
 *
 * <p>Each method throws {@link IllegalArgumentException} in case of wrong argument.</p>
 *
 * @author Anton Pechinsky
 * @see Assert
 */
public class Argument {

    /**
     * Asserts that specified expression is true.
     *
     * @param expression boolean expression
     * @param messageFormat String representing message format
     * @param arguments vararg array of message format parameters.
     * @throws IllegalArgumentException if object is false.
     */
    public static void checkTrue(boolean expression, String messageFormat, Object... arguments) {
        if (!expression) {
            throw new IllegalArgumentException(Utils.format(messageFormat, arguments));
        }
    }

    /**
     * Asserts that specified expression is false.
     *
     * @param expression boolean expression
     * @param messageFormat String representing message format
     * @param arguments vararg array of message format parameters.
     * @throws IllegalArgumentException if object is false.
     */
    public static void checkFalse(boolean expression, String messageFormat, Object... arguments) {
        checkTrue(!expression, messageFormat, arguments);
    }

    /**
     * Asserts that specified object is null.
     *
     * @param object Object to test
     * @param messageFormat String representing messageFormat to pass to exception.
     * @param arguments vararg array of messageFormat format parameters.
     * @throws IllegalArgumentException if object is not null.
     */
    public static void checkNull(Object object, String messageFormat, Object... arguments) {
        checkTrue(object == null, messageFormat, arguments);
    }

    /**
     * Asserts that specified object is NOT null.
     *
     * @param object Object to test
     * @param messageFormat String representing messageFormat to pass to exception.
     * @param arguments vararg array of messageFormat format parameters.
     * @throws IllegalArgumentException if object null.
     */
    public static void checkNotNull(Object object, String messageFormat, Object... arguments) {
        checkTrue(object != null, messageFormat, arguments);
    }

    /**
     * Asserts that specified object is NOT null.
     *
     * <p>Unlike {@link #checkNotNull(Object, String, Object...)} this method produces generic error message:
     * {@code Argument '${argument}' must not be null!}
     * </p>
     *
     * @param object Object to test
     * @param argument String argument name.
     * @throws IllegalArgumentException if object null.
     */
    public static void checkNotNullWithGenericMessage(Object object, String argument) {
        checkNotNull(object, "Argument '%s' must not be null!", argument);
    }

    /**
     * Asserts that specified string is blank.
     *
     * @param string String to check
     * @param messageFormat String representing messageFormat to pass to exception.
     * @param arguments vararg array of messageFormat format parameters.
     * @throws IllegalArgumentException if object is not null.
     */
    public static void checkBlank(String string, String messageFormat, Object... arguments) {
        checkTrue(Utils.isBlank(string), messageFormat, arguments);
    }

    /**
     * Asserts that specified string is NOT blank.
     *
     * @param string String to check
     * @param messageFormat String representing messageFormat to pass to exception.
     * @param arguments vararg array of messageFormat format parameters.
     * @throws IllegalArgumentException if object is not null.
     */
    public static void checkNotBlank(String string, String messageFormat, Object... arguments) {
        checkTrue(!Utils.isBlank(string), messageFormat, arguments);
    }

    /**
     * Asserts that specified objects are equal.
     *
     * @param object1 Object first object
     * @param object2 Object second object
     * @param messageFormat String representing messageFormat to pass to exception.
     * @param arguments vararg array of messageFormat format parameters.
     * @throws IllegalArgumentException if object1 is not null.
     */
    public static void checkEqual(Object object1, Object object2, String messageFormat, Object... arguments) {
        checkTrue(Utils.equals(object1, object2), messageFormat, arguments);
    }

    /**
     * Asserts that specified objects are NOT equal.
     *
     * @param object1 Object first object
     * @param object2 Object second object
     * @param messageFormat String representing messageFormat to pass to exception.
     * @param arguments vararg array of messageFormat format parameters.
     * @throws IllegalArgumentException if object1 is not null.
     */
    public static void checkNotEqual(Object object1, Object object2, String messageFormat, Object... arguments) {
        checkTrue(!Utils.equals(object1, object2), messageFormat, arguments);
    }

    /**
     * Returns <code>IllegalArgumentException</code> unconditionally.
     *
     * <p>Used in scenarios where compiler forces to return some value (e.g. if/else chain or switch).
     * To avoid returning a null use {@code throw Argument.failure()} construct.</p>
     *
     * <pre>
     *  switch (value) {
     *      case A:
     *          return valueA;
     *      ...
     *      default:
     *          throw Argument.failure(...)
     *  }
     * </pre>
     *
     * @param messageFormat String representing messageFormat to pass to exception.
     * @param arguments vararg array of messageFormat format parameters.
     * @return IllegalArgumentException
     */
    public static RuntimeException failure(String messageFormat, Object... arguments) {
        return new IllegalArgumentException(Utils.format(messageFormat, arguments));
    }

    /**
     * Throws <code>IllegalArgumentException</code> unconditionally.
     *
     * @param messageFormat String representing messageFormat to pass to exception.
     * @param arguments vararg array of messageFormat format parameters.
     * @throws IllegalArgumentException
     */
    public static void fail(String messageFormat, Object... arguments) {
        throw failure(messageFormat, arguments);
    }

}
