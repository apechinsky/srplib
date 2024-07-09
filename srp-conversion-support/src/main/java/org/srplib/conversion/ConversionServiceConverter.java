package org.srplib.conversion;

import org.srplib.contract.Argument;

/**
 * Converter that delegates conversion to conversion service.
 * <p>Uses as an adaptor where {@link Converter} interface required</p>
 */
public class ConversionServiceConverter implements Converter {

    private ConversionService conversionService;

    public ConversionServiceConverter(ConversionService conversionService) {
        Argument.checkNotNull(conversionService, "conversionService must not be null!");

        this.conversionService = conversionService;
    }

    @Override
    public Object convert(Object input) {
        return conversionService.convert(input);
    }
}
