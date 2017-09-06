package org.srplib.reflection.deepcompare.comparators;

import java.util.Map;

import org.srplib.reflection.deepcompare.DeepComparator;
import org.srplib.reflection.deepcompare.DeepComparatorContext;

/**
 * Implements Map comparison logic.
 *
 * Rules:
 * <ul>
 *     <li>Register diff if maps have different sizes.</li>
 *
 *     <li>Register diff if second map doesn't have a key from first one.</li>
 *
 *     <li>Compares map values using context.compare() method.</li>
 * </ul>
 *
 * <p>Note. Comparator doesn't perform null or reference equality check.</p>
 *
 * @author Anton Pechinsky
 */
public class MapDeepComparator implements DeepComparator<Map> {

    @Override
    public void compare(Map map1, Map map2, DeepComparatorContext context) {

        if (map1.size() != map2.size()) {
            context.registerMismatch(String.format("Maps have different size. Expected: %d actual: %d", map1.size(), map2.size()));
        }

        for (Object key : map1.keySet()) {

            if (!map2.containsKey(key)) {
                context.registerMismatch(String.format("Key '%s' not found in second map.", key));
                continue;
            }

            Object value1 = map1.get(key);
            Object value2 = map2.get(key);

            context.compareNested(value1, value2, String.format("[%s]", key));
        }
    }
}
