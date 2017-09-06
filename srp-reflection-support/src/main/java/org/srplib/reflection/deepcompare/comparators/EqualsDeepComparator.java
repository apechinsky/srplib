package org.srplib.reflection.deepcompare.comparators;

import org.srplib.reflection.deepcompare.DeepComparator;
import org.srplib.reflection.deepcompare.DeepComparatorContext;

/**
 * Compares objects using standard {@link Object#equals(Object)} method.
 *
 * <p>Note. Comparator doesn't perform null or reference equality check.</p>
 *
 * @author Anton Pechinsky
 */
public class EqualsDeepComparator implements DeepComparator<Object> {

    @Override
    public void compare(Object object1, Object object2, DeepComparatorContext context) {

        if (!object1.equals(object2)) {
            context.registerMismatch(String.format("Expected: '%s' actual: '%s'", object1, object2));
        }
    }
}
