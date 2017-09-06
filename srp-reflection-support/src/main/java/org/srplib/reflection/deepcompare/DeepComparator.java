package org.srplib.reflection.deepcompare;

/**
 * Interface for deep object comparator.
 *
 * <p>Used to implements comparison logic for particular class or class hierarchy.</p>
 *
 * @author Anton Pechinsky
 */
public interface DeepComparator<T> {

    /**
     * Compares two objects.
     *
     * <p>
     * Implementation performs object comparison logic and delegates mismatch registration and nested object comparison to
     * to {@code context}.
     * </p>
     *
     * @param object1 first object
     * @param object2 second object
     * @param context comparison context .
     */
    void compare(T object1, T object2, DeepComparatorContext context);

}
