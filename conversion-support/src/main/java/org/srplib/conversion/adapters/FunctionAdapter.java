package org.srplib.conversion.adapters;

import org.srplib.contract.Argument;
import org.srplib.conversion.Converter;

import com.google.common.base.Function;

/**
 * A adapter for google core libraries known as Guava project (http://code.google.com/p/guava-libraries).
 *
 * <p>FunctionAdapter wraps {@link Converter} to implementation of {@link Function}. FunctionAdapter is a good way to reuse
 * existing converters with rich set of utility methods of Guava project}.
 *
 * <pre>
 *     Converter converter = ....
 *     ...
 *     Collection result = Collections2.transform(collection, new FunctionAdapter(converter));
 * </pre>
 * <p>
 *
 * @author Q-APE
 */
public class FunctionAdapter<I, O> implements Function<I, O> {

    private Converter<I, O> converter;

    public FunctionAdapter(Converter<I, O> converter) {
        Argument.checkNotNull(converter, "Converter should not be null!");
        this.converter = converter;
    }

    @Override
    public O apply(I input) {
        return converter.convert(input);
    }
}
