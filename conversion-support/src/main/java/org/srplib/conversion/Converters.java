package org.srplib.conversion;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
    public static <I, O> Converter<I, O> empty() {
        return (Converter<I, O>)EmptyConverter.instance();
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

    /**
     * Creates converter from boolean value to specified alternatives.
     *
     * <p>Converter may be considered as {@code IF (input) THEN trueValue ELSE falseValue}</p>
     *
     * @param trueValue a value to return if input value is {@code true}
     * @param falseValue a value to return if input value is {@code false}
     * @return Converter
     */
    public static <T> Converter<Boolean, T> choice(T trueValue, T falseValue) {
        return new IfConverter<Boolean, T>(trueValue, falseValue);
    }

    /**
     * Creates converter which uses map for value conversion.
     *
     * <p>Converter may be considered as {@code SWITCH (input) OF ... DEFAULT}</p>
     *
     * @param map Map a map where input values are keys and values are output values.
     * @param defaultValue Object default value if no matches found in map
     * @return Converter
     */
    public static <I, O> Converter<I, O> choice(Map<I, O> map, O defaultValue) {
        return new SwitchConverter<I, O>(map, defaultValue);
    }

    /**
     * Converts vararg array of converters into single converter.
     *
     * <p>Method is useful when you have serveral converters but API expect single instance. So you can make a chain of
     * converters.</p>
     *
     * <p>If converters is null or empty then {@link #empty} converter is returned (null-value)</p>
     *
     * <p>If converters.length == 1 then converters[0] is returned</p>
     *
     * <p>If converters.length > 1 then {@link ChainConverter} is returned.</p>
     *
     * @param converters vararg of Converter
     * @return Converter a converter encapsulating all converters.
     */
    public static <I, O> Converter<I, O> chain(Converter... converters) {
        if (converters == null) {
            return empty();
        }
        return chain(Arrays.asList(converters));
    }

    /**
     * Converts list of converters into single converter.
     *
     * <p>Method is similar to {@link #chain(Converter[])}.</p>
     *
     * @param converters vararg of Converter
     * @return Converter a converter encapsulating all converters.
     */
    public static <I, O> Converter<I, O> chain(List<Converter> converters) {
        Converter<I, O> result;
        if (converters == null || converters.isEmpty()) {
            result = empty();
        }
        else if (converters.size() == 1) {
            result = converters.get(0);
        }
        else {
            result = new ChainConverter<I, O>(converters);
        }
        return result;
    }

    /**
     * Creates converter from object to boolean.
     *
     * <p>Converter will return {@code true} if object is equal to specified object.</p>
     *
     * @param object an object to compare to. Nulls are accepted.
     * @return Converter from object to boolean.
     */
    public static <T> Converter<T, Boolean> equal(T object) {
        return new EqualsConverter<T>(object);
    }
}
