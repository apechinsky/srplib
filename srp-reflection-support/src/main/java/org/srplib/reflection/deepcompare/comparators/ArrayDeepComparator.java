package org.srplib.reflection.deepcompare.comparators;

import java.lang.reflect.Array;

import org.srplib.reflection.deepcompare.DeepComparator;
import org.srplib.reflection.deepcompare.DeepComparatorContext;

/**
 * Implements array comparison logic.
 *
 * Rules:
 * <ul>
 *     <li>Register diff if arrays have different sizes.</li>
 *
 *     <li>Compares array items using context.compare() method.</li>
 * </ul>
 *
 * <p>Comparator supports object and primitive type arrays.</p>
 *
 * <p>Note. Comparator doesn't perform null or reference equality check.</p>
 *
 * @author Anton Pechinsky
 */
public class ArrayDeepComparator implements DeepComparator<Object> {

    /**
     * @throws IllegalArgumentException if objects are not arrays
     */
    @Override
    public void compare(Object array1, Object array2, DeepComparatorContext context) {

        if (Array.getLength(array1) != Array.getLength(array2)) {
            context.registerMismatch("Array size mismatch. Expected: %d actual: %d",
                Array.getLength(array1), Array.getLength(array2));

            return;
        }

        for (int i = 0; i < Array.getLength(array1); i++) {
            Object value1 = Array.get(array1, i);
            Object value2 = Array.get(array2, i);

            context.compareNested(value1, value2, String.format("[%d]", i));
        }
    }

}
