package org.srplib.contract;

/**
 * Simple utility class for lightweight contract library.
 *
 * @author Anton Pechinsky
 */
public class Utils {

    /**
     * Tests if two objects are equal.
     *
     * @param object1 Object first object
     * @param object2 Object second object
     * @return true if objects are equals, {@code false} otherwise.
     */
    public static boolean equals(Object object1, Object object2) {
        return object1 == object2 || (object1 != null && object1.equals(object2));
    }

    /**
     * Tests if provided string is null, empty or contain whitespace only.
     *
     * @param string String to test
     * @return true if string is blank.
     */
    public static boolean isBlank(String string) {
        return string == null || string.trim().length() == 0;
    }

    /**
     * Formats message with using specified message format string and provided arguments.
     *
     * @param messageFormat String message format string.
     * @param arguments vararg array of arguments.
     * @return String formatted string.
     */
    public static String format(String messageFormat, Object... arguments) {
        return String.format(messageFormat, arguments);
    }

    /**
     * Returns default value if specified value parameter is null.
     *
     * @param value value to test
     * @param defaultValue default value
     * @param <T> value type parameter
     * @return value if not null or defaultValue is value parameter is null.
     */
    public static <T> T getDefaultIfNull(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }

    /**
     * Returns first non null value from provided array of values.
     *
     * @param defaultValue default value
     * @param values vararg array of values to choose from.
     * @param <T> value type parameter
     * @return first non null value or default value if all values are nulls or vararg array is null or no values are provided.
     */
    public static <T> T getFirstNonNull(T defaultValue, T... values) {
        for (T value : values) {
            if (value != null) {
                return value;
            }
        }
        return defaultValue;
    }

}