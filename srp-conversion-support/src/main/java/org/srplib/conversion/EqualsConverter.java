package org.srplib.conversion;

/**
 * Converts object to boolean.
 *
 * <p>Converter will return {@code true} if object is equal to specified object. Nulls are supported.</p>
 *
 * @author Anton Pechinsky
 */
public class EqualsConverter<T> implements Converter<T, Boolean> {

    private T object;

    public EqualsConverter(T object) {
        this.object = object;
    }

    @Override
    public Boolean convert(Object input) {
        return input == object || (input != null && input.equals(object));
    }
}
