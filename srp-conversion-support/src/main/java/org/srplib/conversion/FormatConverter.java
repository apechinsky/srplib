package org.srplib.conversion;

import java.util.Collection;

import org.srplib.contract.Argument;

/**
 * A converter encapsulating {@link String#format(String, Object...)} logic.
 *
 * <p>Converter tries to interpret input value as an array using following rules:
 * <ul>
 *     <li>if null then return null</li>
 *     <li>if array then return array</li>
 *     <li>if collection then convert to array</li>
 *     <li>else return String.valueOf(value)</li>
 * </ul>
 *
 * @author Anton Pechinsky
 */
public class FormatConverter<I> implements Converter<I, String> {

    private String pattern;

    private FormatConverter(String pattern) {
        Argument.checkNotNull(pattern, "String pattern must not be null!");
        this.pattern = pattern;
    }

    @Override
    public String convert(I input) {
        return String.format(pattern, toArray(input));
    }

    private Object toArray(I input) {
        Object result;
        if (input == null) {
            result = null;
        }
        else if (input.getClass().isArray()) {
            result = input;
        }
        else if (input instanceof Collection) {
            Collection<?> collection = (Collection<?>)input;
            result = collection.toArray(new Object[collection.size()]);
        }
        else {
            result = String.valueOf(input);
        }
        return result;
    }
}
