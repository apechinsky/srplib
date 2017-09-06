package org.srplib.reflection.classgraph;

import java.lang.reflect.Field;

import org.srplib.contract.Argument;
import org.srplib.reflection.ReflectionUtils;

/**
 * Нод
 * @author Anton Pechinsky
 */
public class ClassGraphNode {

    private final Field field;

    private final Class type;

    public static ClassGraphNode create(Class context, String fieldName) {
        return create(ReflectionUtils.getFieldRecursively(context, fieldName));
    }

    public static ClassGraphNode create(Field field) {
        Argument.checkNotNull(field, "field must not be null!");

        return new ClassGraphNode(field.getType(), field);
    }

    public static ClassGraphNode create(Class type) {
        return new ClassGraphNode(type, null);
    }

    /**
     * Class graph node constructor
     *
     * @param type Class a class node type
     * @param field Field class node field. Can be null for root class.
     */
    protected ClassGraphNode(Class type, Field field) {
        Argument.checkNotNull(type, "type must not be null");
        this.type = type;
        this.field = field;
    }

    public Field getField() {
        return field;
    }

    public Class getType() {
        return type;
    }

    public boolean isRoot() {
        return field == null;
    }

    @Override
    public String toString() {
        String fieldName = field != null ? field.getName() : "null";
        return String.format("[field: '%s', class: %s]", fieldName, type);
    }
}
