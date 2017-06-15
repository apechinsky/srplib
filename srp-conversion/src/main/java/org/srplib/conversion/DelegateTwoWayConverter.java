package org.srplib.conversion;

import org.srplib.contract.Argument;

/**
 * An implementation of {@link TwoWayConverter} using two instances of {@link Converter} where the
 * first is used in {@link #convert(Object)} method and the second in {@link #convertBack(Object)} method.
 *
 * @param <I> represents input value type
 * @param <O> represents output value type
 * @author Anton Pechinsky
 */
public class DelegateTwoWayConverter<I, O> implements TwoWayConverter<I, O> {

    private Converter<I, O> converter;

    private Converter<O, I> reverseConverter;

    /**
     * Creates two way converter using specified converters.
     *
     * @param converter Converter first converter.
     * @param reverseConverter Converter second converter.
     */
    public DelegateTwoWayConverter(Converter<I, O> converter, Converter<O, I> reverseConverter) {
        Argument.checkNotNull(converter, "Converter must not be null.");
        Argument.checkNotNull(reverseConverter, "Reverse converter must not be null.");

        this.converter = converter;
        this.reverseConverter = reverseConverter;
    }

    @Override
    public O convert(I input) {
        return converter.convert(input);
    }

    @Override
    public I convertBack(O output) {
        return reverseConverter.convert(output);
    }

    /**
     * Returns underlying delegate converter.
     *
     * @return Converter an instance of converter.
     */
    public Converter getConverter() {
        return converter;
    }

    /**
     * Returns underlying delegate reverse converter.
     *
     * @return Converter an instance of converter.
     */
    public Converter getReverseConverter() {
        return reverseConverter;
    }
}
