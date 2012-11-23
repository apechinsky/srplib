package org.srplib.support;

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
            Boolean.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class
        ));
    }

    /**
     * Map containing init values for primitive classes.
     */
    public static final Map<Class, Object> INIT_VALUES = new HashMap<Class, Object>();

    static {
        INIT_VALUES.put(boolean.class, false);
        INIT_VALUES.put(byte.class, (byte) 0);
        INIT_VALUES.put(short.class, (short) 0);
        INIT_VALUES.put(int.class, 0);
        INIT_VALUES.put(long.class, (long) 0);
        INIT_VALUES.put(float.class, (float) 0.0);
        INIT_VALUES.put(double.class, 0.0);
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
     * @param parameterTypes an array of parameter parameters
     * @return Method if found, {@code null} otherwise
     * @throws IllegalStateException if no method found
     */
    public static Method getDeclaredMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        try {
            return clazz.getDeclaredMethod(methodName, parameterTypes);
        }
        catch (NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Searches and returns specified method recursively.
     *
     * @param clazz Class starting class to search method in
     * @param methodName String method name.
     * @param parameterTypes an array of parameter parameters
     * @return Method if found, {@code null} otherwise
     */
    public static Method findDeclaredMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        try {
            if (clazz == null) {
                return null;
            }
            return clazz.getDeclaredMethod(methodName, parameterTypes);
        }
        catch (NoSuchMethodException e) {
            return findDeclaredMethod(clazz.getSuperclass(), methodName, parameterTypes);
        }
    }

    /**
     * Invokes specified method of specified object using reflection.
     *
     * <p>Method wraps all checked exceptions into unchecked exceptions.</p>
     *
     * @param method Method to invoke
     * @param object Object the object the underlying method is invoked from
     * @param args vararg array of method arguments.
     * @return method invocation result
     */
    @SuppressWarnings("unchecked")
    public static <T> T invokeMethod(Method method, Object object, Object... args) {
        boolean accessible = method.isAccessible();
        try {
            method.setAccessible(true);
            return (T) method.invoke(object, args);
        }
        catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
        catch (InvocationTargetException e) {
            ExceptionUtils.rethrow(e.getTargetException());
            return null;
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
        try {
            boolean oldAccessibleStatus = field.isAccessible();
            field.setAccessible(true);

            field.set(object, value);

            field.setAccessible(oldAccessibleStatus);
        }
        catch (IllegalAccessException e) {
            throw new IllegalStateException("Can't access field '" + field + "' of class " + object.getClass(), e);
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
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            setFieldValue(field, object, value);
        }
        catch (NoSuchFieldException e) {
            throw new IllegalStateException("Field '" + fieldName + "' not found in class " + object.getClass(), e);

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
        try {
            boolean oldAccessibleStatus = field.isAccessible();
            field.setAccessible(true);

            T result = (T) field.get(object);

            field.setAccessible(oldAccessibleStatus);

            return result;
        }
        catch (IllegalAccessException e) {
            throw new IllegalStateException("Can't access field '" + field + "' of class " + object.getClass(), e);
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
            throw new IllegalStateException("Field '" + fieldName + "' not found in class " + object.getClass(), e);
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
     * @param field String field name
     * @return Class return field type
     * @throws IllegalStateException if no such field in specified class
     */
    public static <T> Class<T> getFieldType(Class<?> clazz, String field) {
        try {
            return (Class<T>) clazz.getDeclaredField(field).getType();
        }
        catch (NoSuchFieldException e) {
            throw new IllegalStateException("Property '" + field + "' not found in class " + clazz, e);
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
        return "Instance creation error " + toString(clazz, parameterTypes, parameters);
    }

    /**
     * Returns method invocation string including class, parameter parameters and parameter values.
     *
     * <p>Used to create diagnostic messages.</p>
     *
     * @param clazz Class class
     * @param parameterTypes Class[] parameter parameters
     * @param parameters Object[] parameter values
     * @return String text.
     */
    public static String toString(Class<?> clazz, Class<?>[] parameterTypes, Object[] parameters) {
        return String.format("[class: %s, parameters: %s values: %s].",
            clazz.getName(), Arrays.toString(parameterTypes), Arrays.toString(parameters));
    }

    /**
     * Returns string representation of constructor of specified type and it's parameter parameters.
     *
     * <p>Used to create diagnostic messages.</p>
     *
     * @param clazz Class class
     * @param parameterTypes Class[] parameter parameters
     * @return String text.
     */
    public static String toString(Class<?> clazz, Class<?>[] parameterTypes) {
        return String.format("%s(%s)", clazz.getName(), Arrays.toString(parameterTypes));
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
     * @return List of property names
     */
    public static List<String> getDeclaredFields(Class<?> clazz) {
        List<String> properties = new LinkedList<String>();
        for (Field field : clazz.getDeclaredFields()) {
            if (!isSyntheticName(field.getName())) {
                properties.add(field.getName());
            }
        }
        return properties;
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
}
