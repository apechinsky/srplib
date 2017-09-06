package org.srplib.reflection;

import java.lang.reflect.Field;
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

import org.srplib.contract.Argument;
import org.srplib.contract.Assert;
import org.srplib.support.Path;

/**
 * Helper class containing static utility methods for simplifying reflection API.
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
     * Returns init value for specified class (including classes representing primitives).
     *
     * @param type Class class
     * @return null for reference types, for primitives returns their init values.
     */
    public static Object getInitValue(Class<?> type) {
        return Types.getDefaultValue(type);
    }


    /**
     * Returns first generic type parameter of specified class.
     *
     * @param clazz Class source class
     * @return Class class of type parameters, <code>null</code> if class has no type parameters.
     */
    public static List<Class<?>> getTypeParameters(Class<?> clazz) {
        Argument.checkNotNull(clazz, "'clazz' must not be null!");

        if (!isParameterizedType(clazz.getGenericSuperclass())) {
            return null;
        }
        Type[] typeArguments = (asParameterizedType(clazz)).getActualTypeArguments();
        List types = Arrays.asList(typeArguments);
        return (List<Class<?>>) types;
    }

    /**
     * Returns first generic type parameter of specified class.
     *
     * @param clazz Class source class
     * @return Class class of type parameters, <code>null</code> if class has no type parameters.
     */
    public static <T> Class<T> getTypeParameter(Class<?> clazz) {
        Argument.checkNotNull(clazz, "'clazz' must not be null!");

        if (!isParameterizedType(clazz.getGenericSuperclass())) {
            return null;
        }

        Type[] typeArguments = asParameterizedType(clazz).getActualTypeArguments();

        if (typeArguments.length == 0) {
            return null;
        }

        Type firstTypeArgument = typeArguments[0];

        return isParameterizedType(firstTypeArgument)
            ? (Class<T>) ((ParameterizedType) firstTypeArgument).getRawType()
            : (Class<T>) firstTypeArgument;
    }

    private static boolean isParameterizedType(Type type) {
        return type instanceof ParameterizedType;
    }

    private static ParameterizedType asParameterizedType(Class<?> clazz) {
        Argument.checkNotNull(clazz, "'clazz' must not be null!");

        return (ParameterizedType) clazz.getGenericSuperclass();
    }

    /**
     * Returns first generic type parameter of specified class with specific error message.
     *
     * @param clazz Class source class
     * @param message String error message pattern, Message will be used to construct exception text.
     * @param parameters Object vararg. parameter for message pattern.
     * @return Class class of type parameters, <code>null</code> if class has no type parameters.
     */
    public static <T> Class<T> getTypeParameter(Class<?> clazz, String message, Object... parameters) {
        Argument.checkNotNull(clazz, "'clazz' must not be null!");

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
     */
    public static Method findMethod(Class<?> clazz, String methodName, Class<?>... parameters) {
        try {
            Argument.checkNotNull(clazz, "'clazz' must not be null!");
            Argument.checkNotNull(clazz, "'methodName' must not be null!");

            return clazz.getDeclaredMethod(methodName, parameters);
        }
        catch (NoSuchMethodException e) {
            return null;
        }
    }

    /**
     * Searches and returns specified method in specified class. Non recursive.
     *
     * @param clazz Class starting class to search method in
     * @param methodName String method name.
     * @param parameters an array of parameter types
     * @return Method if found, {@code null} otherwise
     * @throws ReflectionException if no method found
     */
    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameters) {
        try {
            Argument.checkNotNull(clazz, "'clazz' must not be null!");

            return clazz.getDeclaredMethod(methodName, parameters);
        }
        catch (NoSuchMethodException e) {
            throw new ReflectionException("No such method " + ToStringHelper.toString(clazz, methodName, parameters), e);
        }
    }


    /**
     * Searches and returns specified method recursively.
     *
     * @param clazz Class starting class to search method in.
     * @param methodName String method name (non-null).
     * @param parameters an array of parameter types
     * @return Method if found, {@code null} otherwise. Returns {@code null} if clazz is {@code null}.
     */
    public static Method findMethodRecursively(Class<?> clazz, String methodName, Class<?>... parameters) {
        Argument.checkNotNull(methodName, "'methodName' must not be null!");

        if (clazz == null) {
            return null;
        }
        Method method = findMethod(clazz, methodName, parameters);

        if (method == null) {
            method = findMethodRecursively(clazz.getSuperclass(), methodName, parameters);
        }
        return method;
    }

    /**
     * Searches and returns specified method recursively.
     *
     * @param clazz Class starting class to search method in
     * @param methodName String method name.
     * @param parameters an array of parameter types
     * @return Method by specified parameters
     * @throws ReflectionException if no method found.
     */
    public static Method getMethodRecursively(Class<?> clazz, String methodName, Class<?>... parameters) {
        Method method = findMethodRecursively(clazz, methodName, parameters);
        if (method == null) {
            throw new ReflectionException("No such method " + ToStringHelper.toString(clazz, methodName, parameters));
        }
        return method;
    }


    /**
     * Returns declared field of specified class.
     *
     * @param clazz Class class to introspect.
     * @return Field or {@code null} if no such field in specified class.
     */
    public static Field findField(Class<?> clazz, String fieldName) {
        Argument.checkNotNull(clazz, "Can't get field from null class.");
        Argument.checkNotNull(fieldName, "Can't get field with null name.");

        try {
            return clazz.getDeclaredField(fieldName);
        }
        catch (NoSuchFieldException e) {
            return null;
        }
    }

    /**
     * Returns declared field of specified class.
     *
     * @param clazz Class class to introspect.
     * @return Field
     */
    public static Field getField(Class<?> clazz, String fieldName) {
        Field field = findField(clazz, fieldName);
        if (field == null) {
            throw new ReflectionException(String.format("No declared field '%s' in class '%s'.", fieldName, clazz.getName()));
        }
        return field;
    }

    /**
     * Searches recursively and returns declared field with specified name.
     *
     * @param clazz Class class to start search from
     * @param fieldName String field name
     * @return Field field name or {@code null} if field was not found in class or superclasses
     */
    public static Field findFieldRecursively(Class<?> clazz, String fieldName) {

        Field field;

        if (Path.isComplex(fieldName)) {
            Path path = Path.parse(fieldName);
            field = findFieldRecursively(clazz, path);
        }
        else {
            field = findField(clazz, fieldName);

            if (field == null && clazz.getSuperclass() != null) {
                field = findFieldRecursively(clazz.getSuperclass(), fieldName);
            }
        }

        return field;
    }

    /**
     * Searches field specified as {@link Path}.
     *
     * TODO: finder should not throw exception
     *
     * @param clazz Class class
     * @return path Path field path
     * @throws IllegalArgumentException if class or path is {@code null}
     * @throws ReflectionException if intermediate field is not found
     */
    public static Field findFieldRecursively(Class<?> clazz, Path path) {
        Assert.checkNotNull(clazz, "class must not be null!");
        Assert.checkNotNull(path, "Property name must not be null!");

        String fieldName = path.getFirst();

        Field field = findFieldRecursively(clazz, fieldName);

        if (field != null && path.isComplex()) {
            field = findFieldRecursively(field.getType(), path.subpath());
        }

        return field;
    }

    /**
     * Searches recursively and returns declared field with specified name.
     *
     * @param clazz Class class to start search from
     * @param path Path field path
     * @return Field field name or {@code null} if field was not found in class or superclasses
     * @throws ReflectionException if no such field found
     */
    public static Field getFieldRecursively(Class<?> clazz, Path path) {
        Assert.checkNotNull(clazz, "class must not be null!");
        Assert.checkNotNull(path, "Property name must not be null!");

        String fieldName = path.getFirst();

        Field field = findFieldRecursively(clazz, fieldName);

        if (field != null && path.isComplex()) {
            field = findFieldRecursively(field.getType(), path.subpath());
        }

        if (field == null) {
            throw new ReflectionException(
                String.format("Can't find field path '%s'. No declared field '%s' in class '%s' or its superclasses.",
                    path.toString(), fieldName, clazz));
        }

        return field;
    }
    /**
     * Searches recursively and returns declared field with specified name.
     *
     * @param clazz Class class to start search from
     * @param fieldName String field name
     * @return Field field name or {@code null} if field was not found in class or superclasses
     * @throws ReflectionException if no such field found
     */
    public static Field getFieldRecursively(Class<?> clazz, String fieldName) {
        Assert.checkNotNull(fieldName, "Field name must not be null!");

        return getFieldRecursively(clazz, Path.parse(fieldName));
    }

    /**
     * Returns list of declared fields of specified class.
     *
     * @param clazz Class class to introspect.
     * @return List of Field
     */
    public static List<Field> getFields(Class<?> clazz) {
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
    public static List<Field> getFieldsRecursively(Class<?> clazz) {
        LinkedList<Field> fields = new LinkedList<Field>();
        collectFieldsRecursively(clazz, fields);
        return fields;
    }

    private static void collectFieldsRecursively(Class<?> type, List<Field> fields) {
        if (type != null) {
            fields.addAll(getFields(type));
            collectFieldsRecursively(type.getSuperclass(), fields);
        }
    }

    /**
     * Set value of specified field of specified object.
     *
     * <p>Method wraps all checked exceptions into unchecked exceptions.</p>
     *
     * @param target Object the object the underlying method is invoked from
     * @param field Field field to set value to.
     * @param value Object value to set to field
     * @throws ReflectionException if can't set field value.
     */
    public static void setFieldValue(Object target, Field field, Object value) {
        boolean accessible = field.isAccessible();
        try {
            field.setAccessible(true);
            field.set(target, value);
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
     * @param target Object target object
     * @param fieldName String field name to set value to.
     * @param value Object value to set to a field
     * @throws ReflectionException if can't set field value.
     */
    public static void setFieldValue(Object target, String fieldName, Object value) {
        Argument.checkNotNull(target, "'target' must not be null!");
        Argument.checkNotNull(fieldName, "'fieldName' must not be null!");

        Path path = Path.parse(fieldName);
        setFieldValue(target, path, value);
    }

    /**
     * Set value of specified field of specified object.
     *
     * @param target Object target object
     * @param path Path field path
     * @param value Object value to set to field
     * @throws IllegalArgumentException if target of path are null
     * @throws ReflectionException in case of reflection error
     */
    public static void setFieldValue(Object target, Path path, Object value) {
        Argument.checkNotNull(target, "Can't set value to field '%s' of null object.", path);
        Argument.checkNotNull(path, "path must not be null.");

        Object parentObject = target;

        for (int i = 0; i < path.size() - 1; i++) {
            String fieldName = path.get(i);

            if (parentObject == null) {
                throw new ReflectionException(
                    String.format("Can't set value to field '%s'. Object for path '%s' is null.",
                        path, path.subpath(0, i)));
            }

            Field field = findFieldRecursively(parentObject.getClass(), fieldName);

            if (field == null) {
                throw new ReflectionException(
                    String.format("Can't set value to field '%s'. No field '%s' (full path '%s') in class '%s'. ",
                        path, fieldName, path.subpath(0, i + 1), target.getClass()));
            }

            parentObject = getFieldValue(parentObject, field);
        }

        if (parentObject == null) {
            throw new ReflectionException(
                String.format("Can't set value to field '%s'. Parent object (%s) is null", path, path.parent()));
        }

        Field field = getFieldRecursively(parentObject.getClass(), path.getLast());
        setFieldValue(parentObject, field, value);
    }


    private static void setFieldValueInternal(Object target, String fieldName, Object value) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            setFieldValue(target, field, value);
        }
        catch (NoSuchFieldException e) {
            throw new ReflectionException(String.format("Can't set value to field '%s'. No such field.",
                ToStringHelper.toString(target.getClass(), fieldName)), e);
        }
    }

    /**
     * Set value of specified field of specified object.
     *
     * <p>Method wraps all checked exceptions into unchecked exceptions.</p>
     *
     * @param target Object target object
     * @param field Field field to set value to.
     * @return value of field
     * @throws ReflectionException if can't get field value.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getFieldValue(Object target, Field field) {
        boolean accessible = field.isAccessible();
        try {
            field.setAccessible(true);
            return (T) field.get(target);
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
     * @param target Object target object
     * @param fieldName String field name
     * @return value of specified property
     * @throws ReflectionException if can't get field value.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getFieldValue(Object target, String fieldName) {
        Path path = Path.parse(fieldName);
        return getFieldValue(target, path);
    }

    /**
     * Returns value of nested field specified as path.
     *
     * @param target Object target object.
     * @param path Path field path
     * @return Object field value
     * @throws IllegalArgumentException if target or path are null
     * @throws ReflectionException if case of reflection error
     */
    public static <T> T getFieldValue(Object target, Path path) {
        Argument.checkNotNull(target, "Can't get value of field '%s' of null object.", path);
        Argument.checkNotNull(path, "path must not be null.");
        Argument.checkFalse(path.isEmpty(), "path must not be empty.");

        Object fieldValue = target;

        for (int i = 0; i < path.size(); i++) {
            String fieldName = path.get(i);

            if (fieldValue == null) {
                throw new ReflectionException(
                    String.format("Can't get value of field '%s'. Object for path '%s' is null.",
                        path, path.subpath(0, i)));
            }

            Field field = findFieldRecursively(fieldValue.getClass(), fieldName);

            if (field == null) {
                throw new ReflectionException(
                    String.format("Can't get value of field '%s'. No field '%s' (full path '%s') in class '%s'. ",
                        path, fieldName, path.subpath(0, i + 1), target.getClass()));
            }

            fieldValue = getFieldValue(fieldValue, field);
        }

        return (T) fieldValue;
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
            throw new ReflectionException(String.format("No such field '%s'.", ToStringHelper.toString(clazz, fieldName)), e);
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
     * @deprecated use invokeMethod(Object, Method, Object...)
     */
    @Deprecated
    @SuppressWarnings("unchecked")
    public static <T> T invokeMethod(Method method, Object object, Object... arguments) {
        return invokeMethod(object, method, arguments);
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
        return ReflectionInvoker.invokeMethod(target, method, arguments);
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
        return ReflectionInvoker.newInstance(clazz, parameters, arguments);
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
     * @deprecated use Types class
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
     * <li>classes representing primitives (int.class, long.class, etc.)</li>
     * <li>primitive wrapper classes</li>
     * <li>String</li>
     * <li>Date</li>
     * <li>Enum</li>
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
     * @deprecated use Types class
     */
    public static boolean isPrimitiveWrapper(Class<?> clazz) {
        return WRAPPERS.contains(clazz);
    }

    /**
     * Tests if specified class is an array of primitives.
     *
     * @param clazz Class a class to test
     * @return true if class is an array of primitives
     * @throws IllegalArgumentException if the specified Class parameter is null.
     * @deprecated use Types class
     */
    public static boolean isPrimitiveArray(Class<?> clazz) {
        Argument.checkNotNull(clazz, "Can't examine 'null' class.");
        return isArray(clazz) && clazz.getComponentType().isPrimitive();
    }

    /**
     * Tests if specified class is an array of primitive wrapper.
     *
     * @param clazz Class a class to test
     * @return true if class is an array
     * @throws IllegalArgumentException if the specified Class parameter is null.
     * @deprecated use Types class
     */
    public static boolean isPrimitiveWrapperArray(Class<?> clazz) {
        Argument.checkNotNull(clazz, "Can't examine 'null' class.");
        return isArray(clazz) && isPrimitiveWrapper(clazz.getComponentType());
    }

}



