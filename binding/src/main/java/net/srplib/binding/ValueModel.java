package net.srplib.binding;

/**
 * Generic value model.
 *
 * @author Anton Pechinsky
 */
public interface ValueModel<T> {

    /**
     * Set model value.
     *
     * @param value new model value
     */
    void setValue(T value);

    /**
     * Returns current model value.
     *
     * @return value of model.
     */
    T getValue();

    /**
     * Returns type of value.
     *
     * @return Class value class
     */
    Class<T> getType();

}
