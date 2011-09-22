package org.srplib.conversion;

/**
 * Converts {@link Enum} to {@link String}
 *
 * @author Anton Pechinsky
 */
public class EnumToStringConverter<T extends Enum> implements Converter<T, String> {

    @Override
    public String convert(T input) {
        return input.name();
    }

}
