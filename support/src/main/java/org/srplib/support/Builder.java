package org.srplib.support;

/**
 * An interface for object builder.
 *
 * <p>Based on Joshua Bloch article.</p>
 *
 * @author Anton Pechinsky
 */
public interface Builder<T> {

    /**
     * Creates and returns target object.
     *
     * @return created object
     */
    T build();

}
