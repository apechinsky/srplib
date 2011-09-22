package org.srplib.model;

/**
 * Simple mutable implementation of {@link ValueModel}.
 *
 * <p>Allows to change underlying value with {@link #setValue} method.</p>
 *
 * @author Q-APE
 */
public class MutableValueModel<T> implements ValueModel<T> {

    private T value;

    @Override
    public void setValue(T value) {
        this.value = value;
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
