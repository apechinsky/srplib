package org.srplib.support;

import java.util.List;

import org.srplib.contract.Argument;

/**
 * Simplifies object creation via reflection.
 *
 * <ul>
 *  <li>Encapsulates constructor signature: class, parameter parameters. So builder may be reused to create multiple instances.</li>
 *  <li>Wraps checked exception into unchecked.</li>
 *  <li>Provides very detailed error message.</li>
 *  <li>Clarify and simplify instance creation.</li>
 * </ul>
 *
 * <p>Client should specify parameters for constructor invocation.</p>
 *
 * <pre>
 *      // Using ReflectionUtils
 *      ReflectionUtils.newInstance(Person.class,
 *          new Class[] {String.class, String.class, int.class},
 *          new Object[] {"John", "Smith", 35})
 *
 *      // Using ReflectionBeanBuilder
 *      Person person = ReflectionBeanBuilder.create(Person.class)
 *          .parameters(String.class, String.class, int.class)
 *          .newInstance("John", "Smith", 35);
 * </pre>
 *
 * @author Anton Pechinsky
 */
public class ReflectionBeanBuilder<T> {

    private Class<T> clazz;

    private Class<?>[] parameters = {};

    private String errorMessagePattern;

    private Object[] errorMessageParameters;

    /**
     * An alternative to constructor.
     *
     * @param clazz Class a class to create instance of
     * @param parameters vararg array of constructor parameters
     * @return ReflectionBeanBuilder
     */
    public static <T> ReflectionBeanBuilder<T> create(Class<T> clazz, Class<?>... parameters) {
        Argument.checkNotNull(clazz, "Can't create object with 'null' class!");
        ReflectionBeanBuilder<T> builder = new ReflectionBeanBuilder<T>(clazz);
        builder.parameters(parameters);
        return builder;
    }

    /**
     * Creates builder for specified class.
     *
     * @param clazz Class a class to create instance of
     */
    public ReflectionBeanBuilder(Class<T> clazz) {
        Argument.checkNotNull(clazz, "Can't create object with 'null' class!");
        this.clazz = clazz;
    }


    /**
     * Specify parameter parameters as list.
     *
     * @param types List constructor parameter parameters.
     * @return this
     */
    public ReflectionBeanBuilder<T> parameters(List<Class<?>> types) {
        Argument.checkNotNull(types, "Parameter parameters must not be null!");
        parameters((Class[]) types.toArray());
        return this;
    }

    /**
     * Specify parameter parameters as vararg.
     *
     * @param types vararg constructor parameter parameters.
     * @return this
     */
    public ReflectionBeanBuilder<T> parameters(Class<?>... types) {
        Argument.checkTrue(ReflectionUtils.hasConstructor(clazz, types), "No constructor with ");
        this.parameters = types;
        return this;
    }

    /**
     * An error message to be added to standard exception message.
     *
     * @param pattern String error message pattern.
     * @param parameters vararg message pattern parameters.
     * @return this
     */
    public ReflectionBeanBuilder<T> errorMessage(String pattern, Object... parameters) {
        this.errorMessagePattern = pattern;
        this.errorMessageParameters = parameters;
        return this;
    }

    /**
     * Creates new instance.
     *
     * @param arguments vararg of actual parameters.
     * @return new instance
     * @throws IllegalArgumentException if number actual parameters more or less than number of formal parameters (parameters).
     * @throws ReflectionException if object creation fails
     */
    public T newInstance(Object... arguments) {
        try {
            return ReflectionUtils.newInstance(clazz, parameters, arguments);
        }
        catch (ReflectionException e) {
            throw new ReflectionException(getUserMessage() + " " + e.getMessage(), e);
        }
    }

    /**
     * Returns detail message provided by user.
     *
     * @return String formatted user message string.
     */
    private String getUserMessage() {
        return errorMessagePattern == null
            ? ""
            : String.format(errorMessagePattern, errorMessageParameters);
    }

}
