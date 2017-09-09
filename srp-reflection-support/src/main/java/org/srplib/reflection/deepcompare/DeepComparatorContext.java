package org.srplib.reflection.deepcompare;

/**
 * Deep comparator context.
 *
 * <ul>
 *     <li>Provides access to root comparator object for DeepComparator implementations.</li>
 *     <li>Provides methods to register mismatches.</li>
 * </ul>
 * <p>Used to </p>
 *
 * @author Anton Pechinsky
 */
public interface DeepComparatorContext {

    /**
     * Compare objects using common comparison infrastructure.
     *
     * @param object1 first object
     * @param object2 second object
     */
    void compare(Object object1, Object object2);

    /**
     * Compare objects with object graph path information.
     *
     * @param object1 first object
     * @param object2 second object
     * @param node String new object graph path segment. This may be field name, collection index, etc. Used to build
     * mismatch path from root node to current node.
     */
    void compareNested(Object object1, Object object2, String node);

    /**
     * Registers mismatch information.
     *
     * <p>Implementation is responsible</p>
     *
     * @param pattern String mismatch message pattern.
     * @param arguments String mismatch message arguments.
     */
    void registerMismatch(String pattern, Object... arguments);

}
