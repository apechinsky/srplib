package org.srplib.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.srplib.contract.Argument;
import org.srplib.contract.Assert;
import org.srplib.contract.Utils;
import org.srplib.support.ExceptionUtils;

/**
 * Helper class containing static utility methods.
 *
 * @author Anton Pechinsky
 */
public class ReflectionUtils {

    /**
     * Predeclared empty object array (new Object[0]).
     */
    public static final Object[] EMPTY_ARGUMENTS = new Object[0];

    /**
     * Predeclared empty class array (new Class<?>[0]).
     */
    public static final Class<?>[] EMPTY_TYPES = new Class<?>[0];

    /**
     * Set containing primitive wrapper classes.
     */
    public static final Set<Class<?>> WRAPPERS = new HashSet<Class<?>>();

    static {
        WRAPPERS.addAll(Arrays.<Class<?>>asList(
            Boolean.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Character.class
        ));
    }

    /**
     * Map containing init values for primitive classes.
     */
    public static final Map<Class, Object> INIT_VALUES = new HashMap<Class, Object>();

    /**
     * A name of constructor method. Used for diagnostic purposes.
     */
    public static final String CONSTRUCTOR_NAME = "constructor";

    static {
        INIT_VALUES.put(boolean.class, false);
        INIT_VALUES.put(byte.class, (byte) 0);
        INIT_VALUES.put(short.class, (short) 0);
        INIT_VALUES.put(int.class, 0);
        INIT_VALUES.put(long.class, (long) 0);
        INIT_VALUES.put(float.class, (float) 0.0);
        INIT_VALUES.put(double.class, 0.0);
        INIT_VALUES.put(char.class, '\u0000');
    }


    /**
     * Returns init value for specified class (including classes representing primitives).
     *
     * @param type Class class
     * @return null for reference types, for primitives returns their init values.
     */
    public static Object getInitValue(Class<?> type) {

        Object result = null;

        if (type.isPrimitive() && !void.class.equals(type)) {
//            result = Array.get(Array.newInstance(type, 1), 0);
            result = INIT_VALUES.get(type);
        }

        return result;
    }


    /**
     * Returns first generic type parameter of specified class.
     *
     * @param clazz Class source class
     * @return Class class of type parameters, <code>null</code> if class has no type parameters.
     */
    public static <T> Class<T> getTypeParameter(Class<?> clazz) {
        Type[] typeArguments = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments();
        return typeArguments.length > 0 ? (Class<T>) typeArguments[0] : null;
    }

    /**
     * Returns first generic type parameter of specified class.
     *
     * @param clazz Class source class
     * @param message
     * @param parameters
     * @return Class class of type parameters, <code>null</code> if class has no type parameters.
     */
    public static <T> Class<T> getTypeParameter(Class<?> clazz, String message, Object... parameters) {
        Class<T> type = getTypeParameter(clazz);
        Assert.checkNotNull(type, message, parameters);
        return type;
    }

    /**
     * Searches and returns specified method in specified class. Non recursive.
     *
     * @param clazz Class starting class to search method in
     * @param methodName String method name.
     * @param parameters an array of parameter types
     * @return Method if found, {@code null} otherwise
     * @throws IllegalStateException if no method found
     */
    public static Method getDeclaredMethod(Class<?> clazz, String methodName, Class<?>... parameters) {
        try {
            return clazz.getDeclaredMethod(methodName, parameters);
        }
        catch (NoSuchMethodException e) {
            throw new ReflectionException("No such method " + toString(clazz, methodName, parameters), e);
        }
    }

    /**
     * Searches and returns specified method recursively.
     *
     * @param clazz Class starting class to search method in
     * @param methodName String method name.
     * @param parameters an array of parameter types
     * @return Method if found, {@code null} otherwise
     */
    public static Method findDeclaredMethod(Class<?> clazz, String methodName, Class<?>... parameters) {
        try {
            if (clazz == null) {
                return null;
            }
            return clazz.getDeclaredMethod(methodName, parameters);
        }
        catch (NoSuchMethodException e) {
            return findDeclaredMethod(clazz.getSuperclass(), methodName, parameters);
        }
    }

    /**
     * Invokes specified method of specified object using reflection.
     *
     * <p>Method wraps all checked exceptions into unchecked exceptions.</p>
     *
     * @param method Method to invoke
     * @param object Object the object the underlying method is invoked from
     * @param arguments vararg array of method arguments.
     * @return method invocation result
     */
    @SuppressWarnings("unchecked")
    public static <T> T invokeMethod(Method method, Object object, Object... arguments) {
        boolean accessible = method.isAccessible();
        try {
            method.setAccessible(true);
            return (T) method.invoke(object, arguments);
        }
        catch (IllegalAccessException e) {
            throw new ReflectionException("Can't invoke method " + method, e);
        }
        catch (InvocationTargetException e) {
            throw ExceptionUtils.asUnchecked(e.getTargetException());
        }
        finally {
            method.setAccessible(accessible);
        }
    }


    /**
     * Set value of specified field of specified object.
     *
     * <p>Method wraps all checked exceptions into unchecked exceptions.</p>
     *
     * @param field Field field to set value to.
     * @param object Object the object the underlying method is invoked from
     * @param value Object value to set to field
     */
    public static void setFieldValue(Field field, Object object, Object value) {
        boolean accessible = field.isAccessible();
        try {
            field.setAccessible(true);
            field.set(object, value);
        }
        catch (IllegalAccessException e) {
            throw new ReflectionException("Can't set value to field '" + field + "'", e);

        }
        finally {
            field.setAccessible(accessible);
        }
    }

    /**
     * Set value of specified field of specified object.
     *
     * <p>Method wraps all checked exceptions into unchecked exceptions.</p>
     *
     * @param fieldName String field name to set value to.
     * @param object Object the object the underlying method is invoked from
     * @param value Object value to set to field
     */
    public static void setFieldValue(String fieldName, Object object, Object value) {
        Argument.checkNotNull(fieldName, "fieldName must not be null!");
        Argument.checkNotNull(object, "Can't set value to field of null object!");
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            setFieldValue(field, object, value);
        }
        catch (NoSuchFieldException e) {
            throw new ReflectionException(String.format("Can't set value to field '%s'. No such field.",
                toString(object.getClass(), fieldName)), e);
        }
    }

    /**
     * Set value of specified property of specified object.
     *
     * @param bean Object object to containing property
     * @param property String property name
     * @param value Object value to be set as property value
     * @throws IllegalStateException if underlying reflection subsystem throws exception.
     * @deprecated use setFieldValue
     */
    @Deprecated
    public static void setField(Object bean, String property, Object value) {
        setFieldValue(property, bean, value);
    }


    /**
     * Set value of specified field of specified object.
     *
     * <p>Method wraps all checked exceptions into unchecked exceptions.</p>
     *
     * @param field Field field to set value to.
     * @param object Object the object the underlying method is invoked from
     * @return value of field
     */
    @SuppressWarnings("unchecked")
    public static <T> T getFieldValue(Field field, Object object) {
        boolean accessible = field.isAccessible();
        try {
            field.setAccessible(true);
            return (T) field.get(object);
        }
        catch (IllegalAccessException e) {
            throw new ReflectionException(String.format("Can't get value of field '%s'.", field), e);
        }
        finally {
            field.setAccessible(accessible);
        }
    }

    /**
     * Returns value of specified property of specified object.
     *
     * <p>Method wraps all checked exceptions into unchecked exceptions.</p>
     *
     * @param object Object the object the underlying method is invoked from
     * @param fieldName String field name
     * @return value of specified property
     */
    @SuppressWarnings("unchecked")
    public static <T> T getFieldValue(Object object, String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            return (T) getFieldValue(field, object);
        }
        catch (NoSuchFieldException e) {
            throw new ReflectionException(String.format("Can't get value of field '%s'. No such field.",
                toString(object.getClass(), fieldName)), e);

        }
    }

    /**
     * Returns value of specified property of specified object using reflection.
     *
     * @param bean Object object to containing pro
     * @param property String property name
     * @return Object value of specified property
     * @throws IllegalStateException if underlying reflection subsystem throws exception.
     * @deprecated use getFieldValue
     */
    @Deprecated
    public static Object getField(Object bean, String property) {
        return getFieldValue(bean, property);
    }


    /**
     * Searches recursively and returns declared field with specified name.
     *
     * @param clazz Class class to start search from
     * @param fieldName String field name
     * @return Field field name or {@code null} if field was not found in class or superclasses
     */
    public static Field findFieldRecursively(Class<?> clazz, String fieldName) {
        try {
            if (clazz == null) {
                return null;
            }
            return clazz.getDeclaredField(fieldName);
        }
        catch (NoSuchFieldException e) {
            return findFieldRecursively(clazz.getSuperclass(), fieldName);
        }
    }

    /**
     * Returns field type.
     *
     * @param clazz Class a class where field is declared
     * @param fieldName String field name
     * @return Class return field type
     * @throws IllegalStateException if no such field in specified class
     */
    public static <T> Class<T> getFieldType(Class<?> clazz, String fieldName) {
        try {
            return (Class<T>) clazz.getDeclaredField(fieldName).getType();
        }
        catch (NoSuchFieldException e) {
            throw new ReflectionException(String.format("No such field '%s'.", toString(clazz, fieldName)), e);
        }
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

    private static String getInstanceCreationErrorMessage(Class<?> clazz, Class<?>[] parameterTypes, Object[] parameters) {
        return "Instance creation error " + toString(clazz, CONSTRUCTOR_NAME, parameterTypes, parameters);
    }

    /**
     * Creates instance of specified class and wraps checked exceptions into unchecked ones.
     *
     * <p>Converts checked exceptions to unchecked</p>
     *
     * @param clazz Class a class to create instance
     * @return an instance of specified class
     */
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<T> clazz) {
        return newInstance(clazz, EMPTY_TYPES, EMPTY_ARGUMENTS);
    }


    /**
     * Creates instance of specified class and wraps checked exceptions into unchecked ones.
     *
     * <p>Converts checked exceptions to unchecked</p>
     *
     * @param className String class name of a class to create instance
     * @return an instance of specified class
     */
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(String className) {
        return (T) newInstance(classForName(className));
    }

    /**
     * Returns list of declared fields of specified class.
     *
     * @param clazz Class class to introspect.
     * @return List of Field
     */
    public static List<Field> getDeclaredFields(Class<?> clazz) {
        Argument.checkNotNull(clazz, "Can't get fields from null class.");

        List<Field> fields = new LinkedList<Field>();
        for (Field field : clazz.getDeclaredFields()) {
            if (!isSyntheticName(field.getName())) {
                fields.add(field);
            }
        }
        return fields;
    }

    /**
     * Returns list of declared fields of specified class including fields of superclasses.
     *
     * @param clazz Class class to introspect.
     * @return List of Field
     */
    public static List<Field> getDeclaredFieldsRecursively(Class<?> clazz) {
        return getDeclaredFieldsRecursively(clazz, new LinkedList<Field>());
    }

    private static List<Field> getDeclaredFieldsRecursively(Class<?> type, List<Field> fields) {
        if (type != null) {
            fields.addAll(getDeclaredFields(type));
        }
        return fields;
    }


    /**
     * Checks for constructor existence.
     *
     * @return true if specified class has constructor with specified parameter parameters.
     */
    public static boolean hasConstructor(Class<?> clazz, Class<?>[] parameterTypes) {
        try {
            clazz.getDeclaredConstructor(parameterTypes);
            return true;
        }
        catch (NoSuchMethodException e) {
            return false;
        }
    }

    /**
     * An alternative to {@link Class#forName(String)} which wraps checked {@link ClassNotFoundException} exception into
     * unchecked {@link ReflectionException}
     *
     * @param className String full class name
     * @return Class class for specified name
     * @throws IllegalArgumentException if class name is null.
     * @throws ReflectionException if reflection API throws {@link ClassNotFoundException}
     */
    public static <T> Class<T> classForName(String className) {
        try {
            Argument.checkNotNull(className, "Can't create class from 'null' class name.");

            return (Class<T>) Class.forName(className);
        }
        catch (ClassNotFoundException e) {
            throw new ReflectionException(String.format("Class '%s' not found.", className), e);
        }
    }

    /**
     * Tests if specified class is an array.
     *
     * @param clazz Class a class to test
     * @return true if class is an array
     * @throws IllegalArgumentException if the specified Class parameter is null.
     */
    public static boolean isArray(Class<?> clazz) {
        Argument.checkNotNull(clazz, "Can't examine 'null' class.");
        return clazz.isArray();
    }

    /**
     * Tests if specified class is a collection.
     *
     * @param clazz Class a class to test
     * @return true if class is a collection
     * @throws IllegalArgumentException if the specified Class parameter is null.
     */
    public static boolean isCollection(Class<?> clazz) {
        Argument.checkNotNull(clazz, "Can't examine 'null' class.");
        return Collection.class.isAssignableFrom(clazz);
    }

    /**
     * Tests if specified class is a map.
     *
     * @param clazz Class a class to test
     * @return true if class is a map
     * @throws IllegalArgumentException if the specified Class parameter is null.
     */
    public static boolean isMap(Class<?> clazz) {
        Argument.checkNotNull(clazz, "Can't examine 'null' class.");
        return Map.class.isAssignableFrom(clazz);
    }

    /**
     * Tests if specified class represents "simple" type.
     *
     * <ul>Simple types are:
     *  <li>classes representing primitives (int.class, long.class, etc.)</li>
     *  <li>primitive wrapper classes</li>
     *  <li>String</li>
     *  <li>Date</li>
     *  <li>Enum</li>
     * </ul>
     *
     * @param clazz Class a class to test
     * @return true if class represents simple type
     * @throws IllegalArgumentException if the specified Class parameter is null.
     */
    public static boolean isSimpleType(Class<?> clazz) {
        Argument.checkNotNull(clazz, "Can't examine 'null' class.");
        return clazz.isPrimitive() || isPrimitiveWrapper(clazz) || clazz == String.class || clazz == Date.class
            || Enum.class.isAssignableFrom(clazz);
    }

    /**
     * Tests if specified class represents "complex" type.
     *
     * <p>Complex type is a type which is not simple (see {@link #isSimpleType(Class)}).</p>
     *
     * @param clazz Class a class to test
     * @return true if class is complex class
     * @throws IllegalArgumentException if the specified Class parameter is null.
     */
    public static boolean isComplexType(Class<?> clazz) {
        Argument.checkNotNull(clazz, "Can't examine 'null' class.");
        return !isSimpleType(clazz);
    }

    /**
     * Tests if specified name is a synthetic name.
     *
     *
     * @param name String identifier name
     * @return true if name contains dollar sign, false if null or doesn't contain dollar sign.
     */
    public static boolean isSyntheticName(String name) {
        return name != null && name.indexOf('$') != -1;
    }

    /**
     * Tests if specified class is a primitive wrapper.
     *
     * <p>Primitive wrappers are listed in {@link #WRAPPERS} set.</p>
     *
     * @param clazz Class a class to test
     * @return true if class is primitive wrapper
     */
    public static boolean isPrimitiveWrapper(Class<?> clazz) {
        return WRAPPERS.contains(clazz);
    }

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
     *     Call to this method is equivalent to:
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
    private static String toString(Class<?> declaringClass, String fieldName) {
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
