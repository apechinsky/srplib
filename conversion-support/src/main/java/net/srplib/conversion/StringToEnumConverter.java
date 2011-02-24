package net.srplib.conversion;

/**
 * @author Anton Pechinsky
 */
public class StringToEnumConverter<T extends Enum> implements Converter<String, T> {

    private Class<T> enumClass;

    public StringToEnumConverter(Class<T> enumClass) {
        this.enumClass = enumClass;
    }

    public T convert(String input) {
        for (T enumValue : enumClass.getEnumConstants()) {
            if (enumValue.name().equals(input)) {
                return enumValue;
            }
        }
        return null;
    }

}
