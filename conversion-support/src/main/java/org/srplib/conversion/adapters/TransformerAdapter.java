package org.srplib.conversion.adapters;

import org.apache.commons.collections.Transformer;
import org.srplib.contract.Argument;
import org.srplib.conversion.Converter;

/**
 * A adapter for commons collections {@link org.apache.commons.collections.Transformer}.
 *
 * <p>Transformer15Adapter is a good way to reuse existing converters with rich set of utility methods of
 * {@link org.apache.commons.collections.CollectionUtils}.
 *
 * <pre>
 *     Converter converter = ....
 *     ...
 *     Collection result = CollectionUtils.collect(collection, new Transformer15Adapter(converter));
 * </pre>
 * <p>
 *
 * @author Q-APE
 */
public class TransformerAdapter<I, O> implements Transformer {

    private Converter<I, O> converter;

    public TransformerAdapter(Converter<I, O> converter) {
        Argument.checkNotNull(converter, "Converter should not be null!");
        this.converter = converter;
    }

    @Override
    public Object transform(Object source) {
        return converter.convert((I)source);
    }
}
