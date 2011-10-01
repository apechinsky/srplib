package org.srplib.support;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import org.srplib.contract.Assert;

/**
 * Helper class containing static utility methods.
 *
 * @author Anton Pechinsky
 */
public class ReflectionUtils {

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
     * Returns value of specified property of specified object using reflection.
     *
     * @param bean Object object to containing property
     * @param property String property name
     * @return Object value of specified property
     * @throws IllegalStateException if underlying reflection subsystem throws exception.
     */
    public static Object getField(Object bean, String property) {
        try {
            Field field = bean.getClass().getDeclaredField(property);
            field.setAccessible(true);
            return field.get(bean);
        }
        catch (NoSuchFieldException e) {
            throw new IllegalStateException("Property '" + property + "' not found in class " + bean.getClass(), e);
        }
        catch (IllegalAccessException e) {
            throw new IllegalStateException("Can't access property '" + property + "' of class " + bean.getClass(), e);
        }
    }

    /**
     * Set value of specified property of specified object.
     *
     * @param bean Object object to containing property
     * @param property String property name
     * @param value Object value to be set as property value
     * @throws IllegalStateException if underlying reflection subsystem throws exception.
     */
    public static void setField(Object bean, String property, Object value) {
        try {
            Field field = bean.getClass().getDeclaredField(property);
            field.setAccessible(true);
            field.set(bean, value);
        }
        catch (NoSuchFieldException e) {
            throw new IllegalStateException("Property '" + property + "' not found in class " + bean.getClass(), e);
        }
        catch (IllegalAccessException e) {
            throw new IllegalStateException("Can't access property '" + property + "' of class " + bean.getClass(), e);
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

    public static boolean isSyntheticName(String name) {
        return name.indexOf('$') != -1;
    }

    public static <T> Class<T> classForName(String className) {
        try {
            return (Class<T>)Class.forName(className);
        }
        catch (ClassNotFoundException e) {
            throw new IllegalStateException("Class '" + className + "' not found. ", e);
        }
    }
}
