package org.srplib.conversion;

/**
 * Converts {@link Float} value to {@link String} value.
 *
 * @author Q-GMA
 */
public class FloatToStringConverter implements Converter<Float, String> {
    /**
     * Converts float to string.
     *
     * @param input a value to convert.
     * @return String value.
     * TODO: describe exceptions
     */
    @Override
    public String convert(Float input) {
        return String.valueOf(input);
    }
}
