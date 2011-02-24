package net.srplib.conversion;

/**
 * @author Anton Pechinsky
 */
public class StringToDoubleConverter implements Converter<String, Double> {

    public Double convert(String input) {
        try {
            return Double.valueOf(input);
        }
        catch (NumberFormatException e) {
            throw new ConverterException(String.format("Can't convert '%s' to Double.", input), e);
        }
    }
}
