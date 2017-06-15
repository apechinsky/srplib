package org.srplib.conversion.adapters.collections4;

import org.apache.commons.collections4.Transformer;
import org.srplib.contract.Argument;
import org.srplib.conversion.Converter;

/**
 * A adapter for commons collections4 {@link Transformer}.
 *
 * <p>TransformerAdapter is a good way to reuse existing converters with rich set of utility methods of
 * {@link org.apache.commons.collections4.CollectionUtils}.
 *
 * <pre>
 *     Converter converter = ....
 *     ...
 *     Collection result = CollectionUtils.collect(collection, new TransformerAdapter(converter));
 * </pre>
 * <p>
 *
 * @author Anton Pechinsky
 */
public class TransformerAdapter<I, O> implements Transformer<I, O> {

    private Converter<I, O> converter;

    /**
     * Creates instance of TransformerAdapter.
     *
     * @param converter Converter a converter to use when {@link org.apache.commons.collections.Transformer#transform(Object)} method is called.
     * @throws IllegalArgumentException if converter is null.
     */
    public TransformerAdapter(Converter<I, O> converter) {
        Argument.checkNotNull(converter, "Converter should not be null!");
        this.converter = converter;
    }

    @Override
    public O transform(I source) {
        return converter.convert(source);
    }
}
