package org.srplib.conversion;

import org.srplib.contract.Argument;

/**
 * An adapter for {@link TwoWayConverter} implementing {@link Converter#convert(Object)} method using
 * {@link TwoWayConverter#convertBack(Object)}} method of provided converter.
 *
 * <p>Class allows use of {@link TwoWayConverter#convertBack(Object)}} method where Converter is expected.</p>
 *
 * @author Anton Pechinsky
 */
public class ConvertBackConverter<O, I> implements Converter<O, I> {

    private TwoWayConverter<I, O> delegate;

    /**
     * Constructs class using specified TwoWayConverter.
     *
     * @param delegate ConvertBackConverter
     */
    public ConvertBackConverter(TwoWayConverter<I, O> delegate) {
        Argument.checkNotNull(delegate, "delegate");
        this.delegate = delegate;
    }

    @Override
    public I convert(O input) {
        return delegate.convertBack(input);
    }

    /**
     * Returns underlying TwoWayConverter delegate converter.
     *
     * @return TwoWayConverter delegate converter
     */
    public TwoWayConverter<I, O> getDelegate() {
        return delegate;
    }
}
