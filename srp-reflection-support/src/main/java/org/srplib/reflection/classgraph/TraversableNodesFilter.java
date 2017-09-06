package org.srplib.reflection.classgraph;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.srplib.reflection.ReflectionUtils;
import org.srplib.reflection.objectgraph.StandardTraversableClassesFilter;
import org.srplib.support.Predicate;

/**
 * A predicate for checking should or not GlassGraph traverse (examine internals) particular class.
 *
 * @author Anton Pechinsky
 */
public class TraversableNodesFilter implements Predicate<ClassGraphNode> {

    private static final Predicate<Class<?>> CLASSES_FILTER = new StandardTraversableClassesFilter();

    @Override
    public boolean test(ClassGraphNode value) {
        return CLASSES_FILTER.test(value.getType()) && !isProhibited(value.getField());
    }

    private boolean isProhibited(Field field) {
        return field != null && (ReflectionUtils.isSyntheticName(field.getName()) || Modifier.isStatic(field.getModifiers()));

    }

}
