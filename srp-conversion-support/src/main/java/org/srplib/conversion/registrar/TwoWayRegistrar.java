package org.srplib.conversion.registrar;

import org.srplib.conversion.ConvertBackConverter;
import org.srplib.conversion.ConverterRegistry;
import org.srplib.conversion.Registrar;
import org.srplib.conversion.TwoWayConverter;

/**
 * Registrar for twoway converter (first -> second, second -> first)
 */
public class TwoWayRegistrar<I, O> extends AbstractRegistrar<I, O> {

    /**
     * Constructor.
     *
     * @param first first type
     * @param second second type
     * @param converter twoway converter
     */
    public TwoWayRegistrar(Class<I> first, Class<O> second, TwoWayConverter<I, O> converter) {
        super(first, second, converter);
    }

    @Override
    public void register(ConverterRegistry registry) {
        registry.add(getInputType(), getOutputType(), getConverter());
        registry.add(getOutputType(), getInputType(), new ConvertBackConverter<>((TwoWayConverter<I, O>) getConverter()));
    }

    /**
     * Factory method, constructor alternative
     *
     * @param first first type
     * @param second second type
     * @param <I> first type parameter
     * @param <O> second type parameter
     * @return registrar
     */
    public static <I, O> Registrar twoway(Class<I> first, Class<O> second, TwoWayConverter<I, O> converter) {
        return new TwoWayRegistrar<>(first, second, converter);
    }

}
