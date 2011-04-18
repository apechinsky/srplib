package org.srplib.contract;

/**
 * Helper class containing method for maintaining invariants.
 *
 * <p>Each method throws {@link IllegalStateException} in case of wrong argument.</p>
 *
 * <p>
 * It's common practice to make such utility classes non-instantiable. A number of good reasons for that and we agree
 * with the most of them. Nevertheless this class has been designed non-final, non-abstract and doesn't
 * prevent instantiation. The reason is maximal reusability.
 * </p>
 *
 * @author Anton Pechinsky
 */
public class Assert {

    /**
     * Asserts that specified expression is true.
     *
     * @param expression boolean expression
     * @param messageFormat String representing message format
     * @param arguments vararg array of message format parameters.
     * @throws IllegalStateException if object is false.
     */
    public static void checkTrue(boolean expression, String messageFormat, Object... arguments) {
        if (!expression) {
            throw new IllegalStateException(Utils.format(messageFormat, arguments));
        }
    }

    /**
     * Asserts that specified expression is false.
     *
     * @param expression boolean expression
     * @param messageFormat String representing message format
     * @param arguments vararg array of message format parameters.
     * @throws IllegalStateException if object is false.
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
     * @throws IllegalStateException if object is not null.
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
     * @throws IllegalStateException if object null.
     */
    public static void checkNotNull(Object object, String messageFormat, Object... arguments) {
        checkTrue(object != null, messageFormat, arguments);
    }

    /**
     * Asserts that specified string is blank.
     *
     * @param string String to check
     * @param messageFormat String representing messageFormat to pass to exception.
     * @param arguments vararg array of messageFormat format parameters.
     * @throws IllegalStateException if object is not null.
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
     * @throws IllegalStateException if object is not null.
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
     * @throws IllegalStateException if object1 is not null.
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
     * @throws IllegalStateException if object1 is not null.
     */
    public static void checkNotEqual(Object object1, Object object2, String messageFormat, Object... arguments) {
        checkTrue(!Utils.equals(object1, object2), messageFormat, arguments);
    }

    /**
     * Throws <code>IllegalStateException</code> unconditionally.
     *
     * @param messageFormat String representing messageFormat to pass to exception.
     * @param arguments vararg array of messageFormat format parameters.
     * @throws IllegalStateException
     */
    public static void fail(String messageFormat, Object... arguments) {
        checkTrue(false, messageFormat, arguments);
    }

}
