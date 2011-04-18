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

}