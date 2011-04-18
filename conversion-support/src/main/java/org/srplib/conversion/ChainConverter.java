package org.srplib.conversion;

import java.util.ArrayList;
import java.util.List;

import org.srplib.contract.Argument;

/**
 * Converter chain converter.
 *
 * <p>Sequentially invokes aggregated converters.</p>
 *
 * @author Anton Pechinsky
 */
public class ChainConverter<I, O> implements Converter<I, O> {

    private List<Converter> converters = new ArrayList<Converter>();

    public ChainConverter(List<Converter> converters) {
        Argument.checkNotNull(converters, "Converters list should not be null.");

        this.converters = converters;
    }

    @Override
    @SuppressWarnings("unchecked")
    public O convert(I input) {
        Object value = input;
        for (Converter converter : converters) {
            value = converter.convert(value);
        }
        return (O) value;
    }
}

