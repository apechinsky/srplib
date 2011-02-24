package net.srplib.conversion;

import java.util.HashMap;
import java.util.Map;

/**
 * A class for organizing converters.
 *
 * <p>Contains method for registering and obtaining type converters</p>
 *
 * @author Anton Pechinsky
 */
public class ConverterRegistry {

    private Map<Class, Map<Class, Converter>> converters = new HashMap<Class, Map<Class, Converter>>();

    /**
     * Register type converter.
     *
     * @param source Class source type
     * @param target Class target type
     * @param converter converter instance
     * @return this object for easy chaining
     */
    public <I, O> ConverterRegistry registerConverter(Class<I> source, Class<O> target, Converter<I, O> converter) {
        registerConverterUntyped(source, target, converter);
        return this;
    }

    /**
     * Non generic version of {@link #registerConverter}.
     *
     * <p>Some types can't be used in generic expression and haven't wrappers. For instance, see byte[].class.
     * This method should be used only if {@link #registerConverter} doesn't help.</p>
     *
     * @param source Class source type
     * @param target Class target type
     * @param converter converter instance
     * @return this object for easy chaining
     */
    public ConverterRegistry registerConverterUntyped(Class<?> source, Class<?> target, Converter<?, ?> converter) {
        Map<Class, Converter> targetToConverter = converters.get(source);
        if (targetToConverter == null) {
            targetToConverter = new HashMap<Class, Converter>();
            converters.put(source, targetToConverter);
        }
        targetToConverter.put(target, converter);
        return this;
    }

    /**
     * Returns type converter for specified java types.
     *
     * @param source Class source java type
     * @param target Class target java type
     * @return an instance of {@link Converter}.
     * <code>null</code> if no converter is found.
     */
    @SuppressWarnings("unchecked")
    public <I, O> Converter<I, O> getConverter(Class<I> source, Class<O> target) {
        Map<Class, Converter> targetToConverter = converters.get(source);
        return targetToConverter == null ? null : targetToConverter.get(target);
    }


}
