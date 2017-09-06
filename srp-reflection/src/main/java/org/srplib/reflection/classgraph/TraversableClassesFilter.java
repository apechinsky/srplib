package org.srplib.reflection.classgraph;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import static java.util.Arrays.asList;

import org.srplib.reflection.ReflectionUtils;
import org.srplib.reflection.Types;
import org.srplib.support.Predicate;

/**
 * A predicate for checking should or not GlassGraph traverse (examine internals) particular class.
 *
 * @author Anton Pechinsky
 */
public class TraversableClassesFilter implements Predicate<ClassGraphNode> {

    private static final Set<Class<?>> PROHIBITED_TYPES = new HashSet<>(asList(
        Object.class,
        String.class,
        StringBuilder.class,
        StringBuffer.class,
        Date.class
    ));

    @Override
    public boolean test(ClassGraphNode value) {
        return !isProhibited(value.getType()) && !isProhibited(value.getField());
    }

    private boolean isProhibited(Field field) {
        return field != null && (ReflectionUtils.isSyntheticName(field.getName()) || Modifier.isStatic(field.getModifiers()));

    }

    private boolean isProhibited(Class type) {
        return
            PROHIBITED_TYPES.contains(type) ||
                Types.isPrimitive(type) ||
                Types.isPrimitiveArray(type) ||
                Types.isWrapper(type) ||
                Types.isWrapperArray(type) ||
                Types.isNumber(type) ||
                type.isEnum();
    }

}
