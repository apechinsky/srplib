package org.srplib.reflection.valuefactory;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.srplib.conversion.Converter;
import org.srplib.conversion.mapper.MatcherMapBuilder;
import org.srplib.reflection.valuefactory.factories.ConstantValueFactory;


/**
 * @author Anton Pechinsky
 */
public class ConfigurableValueFactory implements ValueFactory {

    private Converter<Class<?>, ValueFactory> converter;

    public Builder builder() {
        return new Builder();
    }

    private ConfigurableValueFactory(Converter<Class<?>, ValueFactory> converter) {
        this.converter = converter;
    }

    @Override
    public Object get(TypeMeta meta) {
        ValueFactory valueFactory = converter.convert(meta.getType());
        return valueFactory.get(meta);
    }

    public static class Builder {

        private MatcherMapBuilder<Class<?>, ValueFactory> builder;

        public Builder() {
            this.builder = MatcherMapBuilder.<Class<?>, ValueFactory>create()
                .throwExceptionOnNoMatch(true);
        }

        public static Builder create() {
            return new Builder();
        }

        public Builder register(Matcher<Class<?>> classMatcher, ValueFactory<?> valueFactory) {
            builder.map(classMatcher, valueFactory);
            return this;
        }

        public <T> Builder register(Class<T> type, ValueFactory<T> valueFactory) {
            register(Matchers.<Class<?>>sameInstance(type), valueFactory);
            return this;
        }

        public <T> Builder register(Class<T> type, T value) {
            register(type, new ConstantValueFactory<>(value));
            return this;
        }

        public ValueFactory build() {
            return new ConfigurableValueFactory(builder.build());
        }

    }

}
