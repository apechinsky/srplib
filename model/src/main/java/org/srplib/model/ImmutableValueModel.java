package org.srplib.model;

import java.io.Serializable;

import org.srplib.contract.Assert;

/**
 * Simple immutable implementation of {@link ValueModel}.
 *
 * <p>This implementation doesn't permit to change underlying value.</p>
 *
 * @author Anton Pechinsky
 */
public class ImmutableValueModel<T> implements ValueModel<T>, Serializable {

    private final T value;

    /**
     * Constructor.
     *
     * @param value model value. {@code null} value is ok.
     */
    public ImmutableValueModel(T value) {
        this.value = value;
    }

    /**
     * This method ignores value and throws an exception unconditionally.
     *
     * @param value new model value
     * @throws IllegalStateException if called
     */
    @Override
    public void setValue(T value) {
        Assert.fail("Can't change value of ImmutableValueModel.");
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public Class<T> getType() {
        return value == null ? null : (Class<T>) value.getClass();
    }
}
