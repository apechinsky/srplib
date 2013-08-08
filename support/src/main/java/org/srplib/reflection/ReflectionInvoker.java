package org.srplib.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.srplib.contract.Argument;

/**
 * Simplifies method invocation via reflection.
 *
 * <ul>
 *  <li>Encapsulates method signature: class, parameter parameters. So invoker may be reused to create multiple instances.</li>
 *  <li>Wraps checked exception into unchecked.</li>
 *  <li>Provides very detailed error message.</li>
 * </ul>
 *
 * <p>Client should specify parameters for a method invocation.</p>
 *
 * <pre>
 *      // Using ReflectionUtils
 *      ReflectionUtils.newInstance(Person.class,
 *          new Class[] {String.class, String.class, int.class},
 *          new Object[] {"John", "Smith", 35})
 *
 *      // Using ReflectionInvoker
 *      Person person = ReflectionInvoker.constructor(Person.class)
 *          .parameters(String.class, String.class, int.class)
 *          .invoke("John", "Smith", 35);
 *
 *      // Invoke constructor without parameters
 *      ReflectionInvoker.constructor(Person.class).invoke();
 *
 *      // Invoke method
 *      Person person = ...
 *      ReflectionInvoker.method(person, "setName").invoke("James");
 *
 *      // Create invoker for multiple invocations
 *      ReflectionInvoker invoker = ReflectionInvoker.method(person, "setName");
 *      invoker.invoke("John");
 *      ...
 *      invoker.invoke("James");
 *
 *      // Create invoker for multiple constructor invocation
 *      ReflectionInvoker invoker = ReflectionInvoker.constructor(Person.class).parameters(String.class, Integer.class);
 *
 *      Person john = invoker.invoke("John", 24);
 *      Person james = invoker.invoke("James", 33);
 * </pre>
 *
 * @author Anton Pechinsky
 */
public class ReflectionInvoker<T, V> {

    private Class<T> clazz;

    private T target;

    private String methodName;

    private Class<?>[] parameters = {};

    private String errorMessagePattern;

    private Object[] errorMessageParameters;


    /**
     * Create constructor invoker.
     *
     * @param clazz Class a class to create instance of
     * @return ReflectionInvoker
     */
    public static <T> ReflectionInvoker<T, T> constructor(Class<T> clazz) {
        Argument.checkNotNull(clazz, "Can't create object with 'null' class!");

        return new ReflectionInvoker<T, T>(clazz, null);
    }

    /**
     * Create method invoker for specified class and method name.
     *
     * <p>This method is used if target object isn't known and should be provided later with {@link #target} method.</p>
     *
     * @param clazz Class a class of target object
     * @param methodName String name of method to invoke
     * @return ReflectionInvoker
     */
    public static <T, V> ReflectionInvoker<T, V> method(Class<T> clazz, String methodName) {
        Argument.checkNotNull(clazz, "Can't create object of 'null' class!");

        return new ReflectionInvoker<T, V>(clazz, methodName);
    }

    /**
     * Create method invoker for specified method of target object.
     *
     * @param target Class a class to create instance of
     * @param methodName String name of method to invoke
     * @return ReflectionBeanBuilder
     */
    public static <T, V> ReflectionInvoker<T, V> method(T target, String methodName) {
        ReflectionInvoker<T, V> invoker = method((Class<T>) target.getClass(), methodName);
        invoker.target = target;
        return invoker;
    }

    /**
     * Creates builder for specified class.
     *
     * @param clazz Class a class to create instance of
     */
    public ReflectionInvoker(Class<T> clazz, String methodName, Class<?>... parameters) {
        Argument.checkNotNull(clazz, "Can't create object with 'null' class!");
        this.clazz = clazz;
        this.methodName = methodName;
        this.parameters = parameters;
    }


    /**
     * Specify parameter parameters as list.
     *
     * @param target Object set target object to invoke methods on
     * @return this
     */
    public ReflectionInvoker<T, V> target(T target) {
        this.target = target;
        return this;
    }

    /**
     * Specify parameter parameters as list.
     *
     * @param types List constructor parameter parameters.
     * @return this
     */
    public ReflectionInvoker<T, V> parameters(List<Class<?>> types) {
        Argument.checkNotNull(types, "Parameter parameters must not be null!");
        parameters((Class[]) types.toArray());
        return this;
    }

    /**
     * Specify parameter parameters as vararg.
     *
     * @param parameters vararg constructor parameter parameters.
     * @return this
     */
    public ReflectionInvoker<T, V> parameters(Class<?>... parameters) {
        // fail fast checks
        if (isConstructorInvoker()) {
            Argument.checkTrue(ReflectionUtils.hasConstructor(clazz, parameters),
                "No constructor " + ToStringHelper.toString(clazz, null, parameters));
        }
        else {
            Argument.checkTrue(ReflectionUtils.findMethodRecursively(clazz, methodName, parameters) != null,
                "No method " + ToStringHelper.toString(clazz, methodName, parameters));
        }
        this.parameters = parameters;
        return this;
    }

    private boolean isConstructorInvoker() {
        return methodName == null;
    }

    /**
     * An error message to be added to standard exception message.
     *
     * @param pattern String error message pattern.
     * @param parameters vararg message pattern parameters.
     * @return this
     */
    public ReflectionInvoker<T, V> errorMessage(String pattern, Object... parameters) {
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
    public V invoke(Object... arguments) {
        try {
            if (isConstructorInvoker()) {
                return (V) ReflectionUtils.newInstance(clazz, parameters, arguments);
            }
            else {
                // TODO: cache this
                Method method = ReflectionUtils.getMethodRecursively(clazz, methodName, parameters);
                return ReflectionUtils.invokeMethod(target, method, arguments);
            }

        }
        catch (ReflectionException e) {
            throw new ReflectionException(getUserMessage() + " " + e.getMessage(), e);
        }
    }

    /**
     * Invokes specified method of specified target using reflection.
     *
     * <p>Method wraps all checked exceptions into unchecked exceptions.</p>
     *
     * @param method Method to invoke
     * @param target Object the target the underlying method is invoked from
     * @param arguments vararg array of method arguments.
     * @return method invocation result
     */
    @SuppressWarnings("unchecked")
    public static <T> T invokeMethod(Object target, Method method, Object... arguments) {
        boolean accessible = method.isAccessible();
        try {
            method.setAccessible(true);
            return (T) method.invoke(target, arguments);
        }
        catch (IllegalAccessException e) {
            throw new ReflectionException(getMethodInvocationErrorMessage(
                target.getClass(), method.getName(), method.getParameterTypes(), arguments), e);
        }
        catch (InvocationTargetException e) {
            throw new ReflectionException(getMethodInvocationErrorMessage(
                target.getClass(), method.getName(), method.getParameterTypes(), arguments), e.getCause());
        }
        finally {
            method.setAccessible(accessible);
        }
    }

    private static String getMethodInvocationErrorMessage(Class<?> clazz, String methodName, Class<?>[] parameterTypes,
        Object[] parameters) {

        return "Method invocation error " + ToStringHelper.toString(clazz, methodName, parameterTypes, parameters);
    }

    /**
     * Creates instance of specified class and wraps checked exceptions into unchecked ones.
     *
     * <p>Converts checked exceptions to unchecked</p>
     *
     * @param clazz Class a class to create instance
     * @param parameters Class[] array of constructor parameters (parameter types).
     * @param arguments Object[] an array of constructor arguments.
     * @return an instance of specified class
     */
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<T> clazz, Class[] parameters, Object[] arguments) {
        try {
            Argument.checkEqual(parameters.length, arguments.length,
                getInstanceCreationErrorMessage(clazz, parameters, arguments) +
                    " Expecting %d parameters but actually got %d.", parameters.length, arguments.length);

            Constructor<T> constructor = clazz.getDeclaredConstructor(parameters);
            constructor.setAccessible(true);
            return constructor.newInstance(arguments);
        }
        catch (InstantiationException e) {
            throw new ReflectionException(getInstanceCreationErrorMessage(clazz, parameters, arguments), e);
        }
        catch (IllegalAccessException e) {
            throw new ReflectionException(getInstanceCreationErrorMessage(clazz, parameters, arguments), e);
        }
        catch (NoSuchMethodException e) {
            throw new ReflectionException(getInstanceCreationErrorMessage(clazz, parameters, arguments), e);
        }
        catch (InvocationTargetException e) {
            throw new ReflectionException(getInstanceCreationErrorMessage(clazz, parameters, arguments), e.getCause());
        }
    }

    /**
     * A name of constructor method. Used for diagnostic purposes.
     */
    private static final String CONSTRUCTOR_NAME = "constructor";

    private static String getInstanceCreationErrorMessage(Class<?> clazz, Class<?>[] parameterTypes, Object[] parameters) {
        return "Instance creation error " + ToStringHelper.toString(clazz, CONSTRUCTOR_NAME, parameterTypes, parameters);
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
