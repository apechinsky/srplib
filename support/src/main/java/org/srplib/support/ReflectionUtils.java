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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.srplib.contract.Assert;

/**
 * Helper class containing static utility methods.
 *
 * @author Anton Pechinsky
 */
public class ReflectionUtils {

    public static final Set<Class<?>> WRAPPERS = new HashSet<Class<?>>();

    static {
        WRAPPERS.addAll(Arrays.<Class<?>>asList(
            Boolean.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class
        ));
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
     * @param parameterTypes an array of parameter types
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
     * @param parameterTypes an array of parameter types
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
        try {
            method.setAccessible(true);
            return (T) method.invoke(object, args);
        }
        catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
        catch (InvocationTargetException e) {
            ExceptionUtils.rethrow(e.getTargetException());
        }
        return null;
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

    public static <T> Class<T> getFieldType(Class<?> clazz, String property) {
        try {
            return (Class<T>) clazz.getDeclaredField(property).getType();
        }
        catch (NoSuchFieldException e) {
            throw new IllegalStateException("Property '" + property + "' not found in class " + clazz, e);
        }
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
        try {
            return clazz.newInstance();
        }
        catch (InstantiationException e) {
            throw new IllegalStateException(String.format("Can't create instance of class '%s'", clazz.getName()), e);
        }
        catch (IllegalAccessException e) {
            throw new IllegalStateException(String.format("Can't create instance of class '%s'", clazz.getName()), e);
        }
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
    public static <T> T newInstance(Class<T> clazz, Class[] parameterTypes, Object[] parameters) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor(parameterTypes);
            constructor.setAccessible(true);
            return constructor.newInstance(parameters);
        }
        catch (InstantiationException e) {
            throw new IllegalStateException(String.format("Can't create instance of class '%s'", clazz.getName()), e);
        }
        catch (IllegalAccessException e) {
            throw new IllegalStateException(String.format("Can't create instance of class '%s'", clazz.getName()), e);
        }
        catch (NoSuchMethodException e) {
            throw new IllegalStateException(String.format("Can't create instance of class '%s'", clazz.getName()), e);
        }
        catch (InvocationTargetException e) {
            throw new IllegalStateException(String.format("Can't create instance of class '%s'", clazz.getName()), e);
        }
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


    public static <T> Class<T> classForName(String className) {
        try {
            return (Class<T>)Class.forName(className);
        }
        catch (ClassNotFoundException e) {
            throw new IllegalStateException("Class '" + className + "' not found. ", e);
        }
    }

    public static boolean isCollection(Class<?> clazz) {
        return clazz.isArray() || Collection.class.isAssignableFrom(clazz);
    }

    public static boolean isMap(Class<?> clazz) {
        return Map.class.isAssignableFrom(clazz);
    }

    public static boolean isComplexType(Class<?> clazz) {
        return !isSimpleType(clazz);
    }

    public static boolean isSimpleType(Class<?> clazz) {
        return clazz.isPrimitive() || isPrimitiveWrapper(clazz) || clazz == String.class || clazz == Date.class
            || Enum.class.isAssignableFrom(clazz);
    }

    public static boolean isSyntheticName(String name) {
        return name.indexOf('$') != -1;
    }

    public static boolean isPrimitiveWrapper(Class<?> clazz) {
        return WRAPPERS.contains(clazz);
    }
}
