package org.srplib.objectgraph;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.srplib.reflection.ReflectionUtils;

/**
 * Encapsulates object graph navigation algorithm.
 *
 * <ul>
 *     <li>
 *         iterates java container structures: (Map, Collection, array, etc.).
 *     </li>
 *     <li>
 *         iterates declared fields of java classes using reflection API.
 *     </li>
 *     <li>
 *         handles cyclic references.
 *     </li>
 * </ul>
 *
 * <p>Useful where object graph navigation is required: debugging, logging, conversion, etc.</p>
 *
 * @author Anton Pechinsky
 */
public class ObjectGraph implements Element {

    private Object root;

    private Set<Object> visited = new HashSet<Object>();

    /**
     * Creates object graph with specified root.
     *
     * @param root Object root object. {@code null} is supported.
     */
    public ObjectGraph(Object root) {
        this.root = root;
    }

    @Override
    public void accept(Visitor visitor) {
        traverse(root, visitor);
    }

    private void traverse(Object object, Visitor visitor) {
        if (object == null) {
            return;
        }
        if (!isTraversable(object)) {
            return;
        }

        if (isVisited(object)) {
            return;
        }
        rememberVisited(object);

        visitor.visit(object);


        if (ReflectionUtils.isMap(object.getClass())) {
            traverseMap((Map) object, visitor);
        }
        else if (ReflectionUtils.isCollection(object.getClass())) {
            traverseCollection((Collection) object, visitor);
        }
        else if (ReflectionUtils.isArray(object.getClass())) {
            traverseArray((Object[]) object, visitor);
        }
        else {
            traverseDeclaredFields(object.getClass(), object, visitor);
        }
    }

    private void rememberVisited(Object object) {
        visited.add(object);
    }

    private boolean isVisited(Object object) {
        return visited.contains(object);
    }

    private void traverseMap(Map map, Visitor visitor) {
        for (Object entryObject : map.entrySet()) {
            Map.Entry entry = (Map.Entry) entryObject;
            traverse(entry.getKey(), visitor);
            traverse(entry.getValue(), visitor);
        }
    }

    private void traverseCollection(Collection collection, Visitor visitor) {
        for (Object valueItem : collection) {
            traverse(valueItem, visitor);
        }
    }

    private void traverseArray(Object[] array, Visitor visitor) {
        for (Object valueItem : array) {
            traverse(valueItem, visitor);
        }
    }

    private void traverseDeclaredFields(Class objectClass, Object object, Visitor visitor) {
        if (objectClass == Object.class) {
            return;
        }

        for (Field field : objectClass.getDeclaredFields()) {
            if (ReflectionUtils.isSyntheticName(field.getName())) {
                continue;
            }
            Object fieldValue = ReflectionUtils.getFieldValue(field, object);
            traverse(fieldValue, visitor);
        }

        traverseDeclaredFields(objectClass.getSuperclass(), object, visitor);
    }

    private boolean isTraversable(Object object) {
        return object != null && object.getClass() != Object.class && ReflectionUtils.isComplexType(object.getClass());
    }
}
