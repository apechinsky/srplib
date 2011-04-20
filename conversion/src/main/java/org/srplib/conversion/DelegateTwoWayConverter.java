package org.srplib.conversion;

import org.srplib.contract.Argument;

import com.sun.corba.se.impl.logging.OMGSystemException;

/**
 * An implementation of {@link TwoWayConverter} which uses two other converters to implement
 *
 * @param <I> represents input value type
 * @param <O> represents output value type
 * @author Q-APE
 */
public class DelegateTwoWayConverter<I, O> implements TwoWayConverter<I, O> {

    private Converter<I, O> converter;

    private Converter<O, I> reverseConverter;

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
