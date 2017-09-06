package org.srplib.reflection.objectgraph;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import static java.util.Arrays.asList;

import org.srplib.reflection.Types;
import org.srplib.support.Predicate;

/**
 * A predicate for checking should or not GlassGraph traverse (examine internals) particular class.
 *
 * @author Anton Pechinsky
 */
public class StandardTraversableClassesFilter implements Predicate<Class<?>> {

    private static final Set<Class<?>> PROHIBITED_TYPES = new HashSet<>(asList(
        Object.class,
        String.class,
        StringBuilder.class,
        StringBuffer.class,
        Date.class
    ));

    @Override
    public boolean test(Class<?> value) {
        return !isProhibited(value);
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
