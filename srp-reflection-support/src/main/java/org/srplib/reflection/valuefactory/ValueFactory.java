package org.srplib.reflection.valuefactory;

/**
 * Generic value object factory.
 *
 * <p>Implementation responsible for creating values by value metadata. ValueMeta should provide essential data for
 * object creation. </p>
 *
 * @author Anton Pechinsky
 */
public interface ValueFactory<T> {

    /**
     * Creates value.
     *
     * @param meta ValueMeta value metadata
     * @return value
     * @see TypeMeta
     */
    T get(TypeMeta meta);

}
