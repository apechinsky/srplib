package org.srplib.conversion;

/**
 * A empty implementation of {@link TwoWayConverter}.
 *
 * <p>This implementation doesn't make any conversions. It simply returns passed value back to the caller.</p>
 *
 * <p>Typical usage is "null value" pattern.</p>
 *
 * @author Anton Pechinsky
 */
public class EmptyConverter<T> implements TwoWayConverter<T, T> {

    public static EmptyConverter INSTANCE = new EmptyConverter();

    /**
     * Returns empty converter.
     *
     * <p> Prefer this method to {@link #INSTANCE} because method returns type safe instance:
     * <pre>
     * Converter&lt;Integer, String&gt; converter = EmptyConverter.instance();
     * </pre>
     * </p>
     *
     * @return EmptyConverter an instance of this class.
     */
    @SuppressWarnings("unchecked")
    public static <T> EmptyConverter<T> instance() {
        return (EmptyConverter<T>) INSTANCE;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T convert(T input) {
        return (T) input;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T convertBack(T output) {
        return (T) output;
    }
}
