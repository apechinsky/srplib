package org.srplib.conversion.registrar;

import org.srplib.conversion.Converter;
import org.srplib.conversion.ConverterRegistry;
import org.srplib.conversion.Registrar;

/**
 * Oneway converter registrar (source -> target)
 */
public class OneWayRegistrar<I, O> extends AbstractRegistrar<I, O> {

    /**
     * Constructor.
     *
     * @param source source type
     * @param target target type
     * @param converter converter
     */
    public OneWayRegistrar(Class<I> source, Class<O> target, Converter<I, O> converter) {
        super(source, target, converter);
    }

    @Override
    public void register(ConverterRegistry registry) {
        registry.add(getInputType(), getOutputType(), getConverter());
    }

    /**
     * Factory method, constructor alternative.
     *
     * @param source source type
     * @param target target type
     * @param <I> source type parameter
     * @param <O> target type parameter
     * @return registrar
     */
    public static <I, O> Registrar oneway(Class<I> source, Class<O> target, Converter<I, O> converter) {
        return new OneWayRegistrar<>(source, target, converter);
    }
}
