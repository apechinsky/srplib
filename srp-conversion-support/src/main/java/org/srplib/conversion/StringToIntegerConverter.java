package org.srplib.conversion;

/**
 * Converts {@link String} value to {@link Integer} value.
 *
 * @author Anton Pechinsky
 */
public class StringToIntegerConverter implements Converter<String, Integer> {

    /**
     * Converts string to integer.
     *
     * @param input a value to convert.
     * @return Integer value.
     * @throws org.srplib.conversion.ConverterException if value can't be converted.
     */
    @Override
    public Integer convert(String input) {
        try {
            return Integer.valueOf(input);
        }
        catch (NumberFormatException e) {
            throw new ConverterException("Can't convert '" + input + "' to int.", e);
        }
    }
}