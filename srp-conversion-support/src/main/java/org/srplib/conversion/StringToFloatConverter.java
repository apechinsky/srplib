package org.srplib.conversion;

/**
 * Converts {@link String} value to {@link Float} value.
 *
 * @author Q-GMA
 */
public class StringToFloatConverter implements Converter<String, Float> {
    /**
     * Converts string to float.
     *
     * @param input a value to convert.
     * @return Float value.
     * @throws org.srplib.conversion.ConverterException if value can't be converted.
     */
    @Override
    public Float convert(String input) {
        try {
            return Float.valueOf(input);
        }
        catch (NumberFormatException e) {
            throw new ConverterException("Can't convert '" + input + "' to float.", e);
        }
    }
}
