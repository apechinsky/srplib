package org.srplib.reflection.deepcompare.comparators;

import java.util.List;

import org.srplib.reflection.deepcompare.DeepComparator;
import org.srplib.reflection.deepcompare.DeepComparatorContext;

/**
 * Implements List comparison logic.
 *
 * Rules:
 * <ul>
 *     <li>Register diff if lists have different sizes.</li>
 *
 *     <li>Compares list items using context.compare() method.</li>
 * </ul>
 *
 * <p>Note. Comparator doesn't perform null or reference equality check.</p>
 *
 * @author Anton Pechinsky
 */
public class ListDeepComparator implements DeepComparator<List> {

    @Override
    public void compare(List list1, List list2, DeepComparatorContext context) {

        if (list1.size() != list2.size()) {
            context.registerMismatch(
                String.format("Lists have different size. Expected: %d actual: %d", list1.size(), list2.size()));

            return;
        }

        for (int i = 0; i < list1.size(); i++) {
            Object value1 = list1.get(i);
            Object value2 = list2.get(i);

            context.compareNested(value1, value2, String.format("[%d]", i));
        }
    }

}
