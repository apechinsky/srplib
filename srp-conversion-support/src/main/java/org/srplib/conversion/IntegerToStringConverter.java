package org.srplib.conversion;

/**
 * Converts {@link Integer} value to {@link String} value.
 *
 * @author Anton Pechinsky
 */
public class IntegerToStringConverter implements Converter<Integer, String> {

    /**
     * Converts integer to string.
     *
     * @param input a value to convert.
     * @return String value.
     * TODO: describe exceptions
     */
    @Override
    public String convert(Integer input) {
        return String.valueOf(input);
    }
}