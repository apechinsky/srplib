package net.srplib.conversion;

import java.util.HashMap;
import java.util.Map;

import net.srplib.contract.Assert;

/**
 * A class for organizing converters.
 *
 * <p>Contains method for registering and obtaining type converters</p>
 *
 * @author Anton Pechinsky
 */
public class ConverterRegistry {

    private Map<MappingKey, Converter> converters = new HashMap<MappingKey, Converter>();

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
        MappingKey mappingKey = createKey(source, target);

        Assert.isTrue(!converters.containsKey(mappingKey),
            String.format("Converter from '%s' to '%s' already registered.", source.getName(), target.getName()));

        converters.put(mappingKey, converter);
        return this;
    }

    /**
     * Returns type converter for specified java types.
     *
     * @param source Class source java type
     * @param target Class target java type
     * @return an instance of {@link Converter}.
     *         <code>null</code> if no converter is found.
     */
    @SuppressWarnings("unchecked")
    public <I, O> Converter<I, O> getConverter(Class<I> source, Class<O> target) {
        return converters.get(createKey(source, target));
    }

    private MappingKey createKey(Class<?> source, Class<?> target) {
        return new MappingKey(source, target);
    }


    private static final class MappingKey {

        private Class source;

        private Class target;

        private MappingKey(Class source, Class target) {
            this.source = source;
            this.target = target;
        }

        @Override
        public boolean equals(Object that) {
            if (this == that) {
                return true;
            }
            if (that == null || getClass() != that.getClass()) {
                return false;
            }

            MappingKey key = (MappingKey) that;

            return source == key.source && target == key.target;
        }

        @Override
        public int hashCode() {
            int result = source.hashCode();
            result = 31 * result + target.hashCode();
            return result;
        }
    }

}
