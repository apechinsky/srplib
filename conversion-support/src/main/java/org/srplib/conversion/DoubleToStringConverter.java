package org.srplib.conversion;

/**
 * Converts {@link Long} value to {@link String} value.
 *
 * @author Anton Pechinsky
 */
public class DoubleToStringConverter implements Converter<Double, String> {

    /**
     * Converts integer to string.
     *
     * @param input a value to convert.
     * @return String value.
     * TODO: describe exceptions
     */
    @Override
    public String convert(Double input) {
        return String.valueOf(input);
    }
}