package org.srplib.reflection.objectgraph;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.srplib.contract.Argument;
import org.srplib.reflection.ReflectionUtils;
import org.srplib.support.Predicate;

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

    private Set<Integer> visitedIdentities = new HashSet<Integer>();

    private Predicate<Class<?>> filter;

    /**
     * Creates object graph with specified root and class filter.
     *
     * @param root Object root object. {@code null} is supported
     * @param filter a predicate defining should or not implementation examine class internals.
     */
    public ObjectGraph(Object root, Predicate<Class<?>> filter) {
        Argument.checkNotNull(root, "root must not be null!");
        Argument.checkNotNull(filter, "filter must not be null!");
        this.root = root;
        this.filter = filter;
    }

    /**
     * Creates object graph with specified root.
     *
     * @param root Object root object. {@code null} is supported.
     */
    public ObjectGraph(Object root) {
        this(root, new StandardTraversableClassesFilter());

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
        int identity = identity(object);
        visitedIdentities.add(identity);
    }

    private boolean isVisited(Object object) {
        int identity = identity(object);
        return visitedIdentities.contains(identity);
    }

    private int identity(Object object) {
        return System.identityHashCode(object);
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
            Object fieldValue = ReflectionUtils.getFieldValue(object, field);
            traverse(fieldValue, visitor);
        }

        traverseDeclaredFields(objectClass.getSuperclass(), object, visitor);
    }

    private boolean isTraversable(Object object) {
        return object != null && filter.test(object.getClass());
    }
}
