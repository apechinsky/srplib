package org.srplib.reflection;

import org.srplib.contract.Argument;
import org.srplib.contract.Utils;

/**
 * Helper method for pretty printing reflection data (method signature, field declaration, etc.)
 *
 * @author Anton Pechinsky
 */
public class ToStringHelper {

    /**
     * Returns string representation of method or constructor invocation.
     *
     * <p>Used to create diagnostic messages.</p>
     *
     * @param clazz Class a class (non-null)
     * @param methodName String method name. If {@code null} then 'constructor' will be used.
     * @param parameters Class[] parameter parameters (non-null)
     * @param arguments Object[] parameter values (nullable)
     * @return String text.
     */
    public static String toString(Class<?> clazz, String methodName, Class<?>[] parameters, Object[] arguments) {
        Argument.checkNotNull(clazz, "clazz must not be null!");
        Argument.checkNotNull(parameters, "parameters must not be null!");

        String result = String.format("%s.%s(%s)",
            clazz.getName(), Utils.getDefaultIfNull(methodName, "constructor"), joinClassNames(",", parameters));

        if (arguments != null) {
            result += " arguments: [" + join(",", arguments) + "]";

            if (parameters.length != arguments.length) {
                result += " Number of arguments doesn't match number of parameters.";
            }
        }


        return result;
    }

    /**
     * Returns string representation of method or constructor signature.
     *
     * <p>
     * Call to this method is equivalent to:
     * <pre>
     *     toString(clazz, methodName, parameterTypes, null);
     * </pre>
     *
     * </p>
     *
     * @param clazz Class class
     * @param methodName String method name. If {@code null} then 'constructor' will be used.
     * @param parameters Class[] parameter types
     * @return String text.
     */
    public static String toString(Class<?> clazz, String methodName, Class<?>[] parameters) {
        return toString(clazz, methodName, parameters, null);
    }

    /**
     * Returns string representation of field.
     *
     * @param declaringClass Class class
     * @param fieldName String field name.
     * @return String field full name
     */
    public static String toString(Class<?> declaringClass, String fieldName) {
        return declaringClass.getName() + "." + fieldName;
    }

    /**
     * Joins class names using specified separator.
     *
     * @param separator String separator to use
     * @param classes vararg array of classes to join.
     * @return String joined string
     */
    public static String joinClassNames(String separator, Class<?>... classes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < classes.length; i++) {
            sb.append(classes[i].getName());
            if (i < classes.length - 1) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    /**
     * Joins object array using specified separator.
     *
     * @param separator String separator to use
     * @param values vararg array of values to join. Method leverages {@link Object#toString()}
     * @return String joined string
     */
    public static String join(String separator, Object... values) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            sb.append(values[i]);
            if (i < values.length - 1) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }
}
