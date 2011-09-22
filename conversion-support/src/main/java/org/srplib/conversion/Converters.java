package org.srplib.conversion;

/**
 * Factory class producing instances of {@link Converter} and {@link ConverterRegistry}
 *
 * @author Anton Pechinsky
 */
public class Converters {

    /**
     * Returns empty converter registry.
     *
     * @return ConverterRegistry converter registry without any converters.
     */
    public static ConverterRegistry newRegistry() {
        return new ConverterRegistry();
    }

    /**
     * Returns default converter registry containing basic converter set.
     *
     * @return ConverterRegistry converter registry.
     */
    public static ConverterRegistry newDefaultRegistry() {
        ConverterRegistry converters = new ConverterRegistry();

        converters.registerConverter(String.class, Double.class, new StringToDoubleConverter());
        converters.registerConverter(String.class, double.class, new StringToDoubleConverter());

        converters.registerConverter(String.class, Integer.class, new StringToIntegerConverter());
        converters.registerConverter(String.class, int.class, new StringToIntegerConverter());

        converters.registerConverter(String.class, Boolean.class, new StringToBooleanConverter());
        converters.registerConverter(String.class, boolean.class, new StringToBooleanConverter());

        converters.registerConverter(Boolean.class, boolean.class, EmptyConverter.<Boolean>instance());

        converters.registerConverter(Integer.class, String.class, new IntegerToStringConverter());
        converters.registerConverter(int.class, String.class, new IntegerToStringConverter());

        converters.registerConverter(Integer.class, Long.class, new IntegerToLongConverter());
        converters.registerConverter(Integer.class, long.class, new IntegerToLongConverter());

        return converters;
    }

    /**
     * Returns {@link EmptyConverter} which simply returns passed value back.
     *
     * @return EmptyConverter an empty converter
     * @see EmptyConverter
     */
    public static <I> Converter<I, I> empty() {
        return EmptyConverter.instance();
    }

    /**
     * Returns string to enum converter.
     *
     * <p>
     *   Call to this method is equivalent to <code>new StringToEnumConverter<Sex>(Sex.class)</code>
     * </p>
     *
     * @param enumClass Class class of enum
     * @return DataConverter
     */
    public static <T extends Enum> Converter<String, T> newStringToEnumConverter(Class<T> enumClass) {
        return new StringToEnumConverter<T>(enumClass);
    }

}
