package org.srplib.reflection.deepcompare;

/**
 * DeepComparator configuration.
 *
 * @author Q-APE
 */
public interface DeepComparatorConfiguration {

    /**
     * Returns comparator for given class.
     *
     * @param type Class a type
     * @return DeepComparator a comparator for specified type
     */
    DeepComparator getComparator(Class type);

}
