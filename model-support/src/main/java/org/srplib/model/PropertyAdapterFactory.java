package org.srplib.model;

/**
 * Factory for constructing instances of {@link org.srplib.model.ValueModel} for named properties.
 *
 * <p>A kind of property is defined by implementation. It may be java class field, property, a key of Map or something else.</p>
 *
 * @author Q-APE
 */
public interface PropertyAdapterFactory<T> {

    /**
     * Returns {@link org.srplib.model.ValueModel} for specified property name..
     *
     * @param property String property name.
     * @return ValueModel value model for specified property.
     */
    ValueModel<T> newAdapter(String property);

}
