package org.srplib.reflection;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.srplib.contract.Argument;

/**
 * Collection of utilities for java types.
 *
 * @author Anton Pechinsky
 */
public class Types {

    private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER;

    private static final Map<Class<?>, Class<?>> WRAPPER_TO_PRIMITIVE;

    private static final Map<Class<?>, Object> DEFAULT_VALUES = new HashMap<>();

    static {
        Map<Class<?>, Class<?>> primitives = new HashMap<>();
        primitives.put(boolean.class, Boolean.class);
        primitives.put(byte.class, Byte.class);
        primitives.put(char.class, Character.class);
        primitives.put(short.class, Short.class);
        primitives.put(int.class, Integer.class);
        primitives.put(long.class, Long.class);
        primitives.put(float.class, Float.class);
        primitives.put(double.class, Double.class);
        PRIMITIVE_TO_WRAPPER = Collections.unmodifiableMap(primitives);

        Map<Class<?>, Class<?>> wrappers = new HashMap<>();
        wrappers.put(Boolean.class, boolean.class);
        wrappers.put(Byte.class, byte.class);
        wrappers.put(Character.class, char.class);
        wrappers.put(Short.class, short.class);
        wrappers.put(Integer.class, int.class);
        wrappers.put(Long.class, long.class);
        wrappers.put(Float.class, float.class);
        wrappers.put(Double.class, double.class);
        WRAPPER_TO_PRIMITIVE = Collections.unmodifiableMap(wrappers);

        DEFAULT_VALUES.put(Boolean.class, false);
        DEFAULT_VALUES.put(Byte.class, (byte) 0);
        DEFAULT_VALUES.put(Character.class, '\u0000');
        DEFAULT_VALUES.put(Short.class, (short) 0);
        DEFAULT_VALUES.put(Integer.class, 0);
        DEFAULT_VALUES.put(Long.class, 0L);
        DEFAULT_VALUES.put(Float.class, 0F);
        DEFAULT_VALUES.put(Double.class, 0D);

        DEFAULT_VALUES.put(boolean.class, false);
        DEFAULT_VALUES.put(byte.class, (byte) 0);
        DEFAULT_VALUES.put(char.class, '\u0000');
        DEFAULT_VALUES.put(short.class, (short) 0);
        DEFAULT_VALUES.put(int.class, 0);
        DEFAULT_VALUES.put(long.class, 0L);
        DEFAULT_VALUES.put(float.class, 0F);
        DEFAULT_VALUES.put(double.class, 0D);
    }

    /**
     * Tests if specified class is a primitive wrapper.
     *
     * @param clazz Class a class to test
     * @return true if class is primitive wrapper
     */
    public static boolean isPrimitive(Class<?> clazz) {
        return clazz.isPrimitive();
    }

    /**
     * Tests if specified class is an array of primitives.
     *
     * @param type Class a class to test
     * @return true if class is an array of primitives
     * @throws IllegalArgumentException if the specified Class parameter is null.
     */
    public static boolean isPrimitiveArray(Class<?> type) {
        Argument.checkNotNull(type, "'type' must not be null!");
        return isArray(type) && type.getComponentType().isPrimitive();
    }

    /**
     * Tests if specified class is a primitive wrapper.
     *
     * @param clazz Class a class to test
     * @return true if class is primitive wrapper
     */
    public static boolean isWrapper(Class<?> clazz) {
        return WRAPPER_TO_PRIMITIVE.containsKey(clazz);
    }

    /**
     * Tests if specified class is an array of primitive wrapper.
     *
     * @param type Class a class to test
     * @return true if class is an array
     * @throws IllegalArgumentException if the specified Class parameter is null.
     */
    public static boolean isWrapperArray(Class<?> type) {
        Argument.checkNotNull(type, "'type' must not be null!");
        return isArray(type) && isWrapper(type.getComponentType());
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
     * Returns all java primitive types.
     *
     * @return Set set of primitive types
     */
    public static Set<Class<?>> getPrimitives() {
        return PRIMITIVE_TO_WRAPPER.keySet();
    }

    /**
     * Returns all java primitive wrapper types.
     *
     * @return Set set of wrapper types
     */
    public static Set<Class<?>> getWrappers() {
        return WRAPPER_TO_PRIMITIVE.keySet();
    }

    /**
     * Returns wrapper for specified primitive type.
     *
     * @param type Class a type
     * @return Class wrapper type or {@code null} if type is not a primitive type
     * @throws IllegalArgumentException if type is null
     */
    public static <T> Class<T> getWrapper(Class<T> type) {
        Argument.checkNotNull(type, "'type' must not be null!");
        return (Class<T>) PRIMITIVE_TO_WRAPPER.get(type);
    }

    /**
     * Returns primitive type for specified wrapper type.
     *
     * @param type Class a type
     * @return Class primitive type or {@code null} if parameter type is not a wrapper type
     * @throws IllegalArgumentException if type is null
     */
    public static <T> Class<T> getPrimitive(Class<T> type) {
        Argument.checkNotNull(type, "'type' must not be null!");

        return (Class<T>) WRAPPER_TO_PRIMITIVE.get(type);
    }

    /**
     * Returns default value for specified primitive or wrapper type.
     *
     * @param type Class a type
     * @return default value for specified type or {@code null} if type is not a wrapper or primitive
     * @throws IllegalArgumentException if type is null
     */
    public static <T> T getDefaultValue(Class<T> type) {
        Argument.checkNotNull(type, "'type' must not be null!");
        return (T) DEFAULT_VALUES.get(type);
    }

    public static boolean isNumber(Class<?> type) {
        return Number.class.isAssignableFrom(type);
    }

}
