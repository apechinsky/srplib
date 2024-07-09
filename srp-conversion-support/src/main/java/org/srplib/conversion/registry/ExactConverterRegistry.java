package org.srplib.conversion.registry;

import java.util.HashMap;
import java.util.Map;

import org.srplib.contract.Assert;
import org.srplib.conversion.Converter;
import org.srplib.conversion.ConverterRegistry;
import org.srplib.support.CompositeKey;

/**
 * Converters registry that returns converter for two exact types
 */
public class ExactConverterRegistry implements ConverterRegistry {

    private Map<CompositeKey, Converter> converters = new HashMap<>();

    @Override
    public <I, O> Converter<I, O> find(Class<I> source, Class<O> target) {
        CompositeKey mappingKey = createKey(source, target);

        return converters.get(mappingKey);
    }

    /**
     * Register specified converter in this converter registry.
     *
     * @param source Class source type
     * @param target Class target type
     * @param converter converter instance
     * @return this object for easy chaining
     */
    public <I, O> void add(Class<I> source, Class<O> target, Converter<I, O> converter) {
        CompositeKey mappingKey = createKey(source, target);

        Assert.checkFalse(converters.containsKey(mappingKey),
            "Converter from '%s' to '%s' already registered.", source.getName(), target.getName());

        converters.put(mappingKey, converter);
    }

    private CompositeKey createKey(Class<?> source, Class<?> target) {
        return new CompositeKey(source, target);
    }

}

