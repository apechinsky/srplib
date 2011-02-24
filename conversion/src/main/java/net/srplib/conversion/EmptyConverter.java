package net.srplib.conversion;

import net.srplib.conversion.Converter;

/**
 * This implementation doesn't make any conversions. In simply returns passed value back to the caller.
 *
 * @author Anton Pechinsky
 */
public class EmptyConverter<I, O> implements Converter<I, O> {

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
    public static <I, O> EmptyConverter<I, O> instance() {
        return (EmptyConverter<I, O>)INSTANCE;
    }

    @SuppressWarnings("unchecked")
    @Override
    public O convert(I input) {
        return (O)input;
    }
}
