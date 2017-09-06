package org.srplib.reflection.deepcompare.comparators;

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
 * <p>Note. Comparator doesn't perform null or reference equality check.</p>
 *
 * @author Anton Pechinsky
 */
public class ArrayDeepComparator implements DeepComparator<Object[]> {

    @Override
    public void compare(Object[] array1, Object[] array2, DeepComparatorContext context) {

        if (array1.length != array2.length) {
            context.registerMismatch(String.format("Array size mismatch. Expected: %d actual: %d", array1.length, array2.length));
        }

        for (int i = 0; i < array1.length; i++) {
            context.compareNested(array1[i], array2[i], String.format("[%d]", i));
        }
    }

}
