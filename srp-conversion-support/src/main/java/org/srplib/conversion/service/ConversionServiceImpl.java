package org.srplib.conversion.service;

import org.srplib.contract.Argument;
import org.srplib.conversion.ConversionService;
import org.srplib.conversion.Converter;
import org.srplib.conversion.ConverterConfigurer;
import org.srplib.conversion.ConverterException;
import org.srplib.conversion.ConverterRegistry;
import org.srplib.conversion.registry.SupertypeConverterRegistry;

/**
 * {@link ConversionService} implementation.
 */
public class ConversionServiceImpl implements ConversionService {

    private ConverterRegistry registry;

    public ConversionServiceImpl(ConverterConfigurer configurer, ConverterRegistry registry) {
        Argument.checkNotNull(configurer, "configurer must not be null!");
        Argument.checkNotNull(registry, "registry must not be null!");

        this.registry = registry;

        configurer.configure(registry, this);
    }

    public ConversionServiceImpl(ConverterConfigurer configurer) {
        this(configurer, new SupertypeConverterRegistry());
    }

    @Override
    public <I, O> O convert(Class<I> inputType, Class<O> outputType, I input) {
        if (input == null) {
            return null;
        }

        if (inputType == outputType) {
            return (O) input;
        }

        Converter<I, O> converter = registry.find(inputType, outputType);

        if (converter == null) {
            throw new ConverterException(
                String.format("Can't convert value from %s to %s. No converter found.", inputType, outputType));
        }

        return converter.convert(input);
    }

}
