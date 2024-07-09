package org.srplib.conversion.service;

import java.util.Objects;

import org.srplib.conversion.TwoWayConverter;

/**
 * Named empty converter.
 * <p>Used for conversion infrastructure test purposes.</p>
 */
class NamedConverter<I, O> implements TwoWayConverter<I, O> {

    private final String name;

    public NamedConverter(String name) {
        this.name = name;
    }

    /**
     * Creates converter with provided name
     */
    public static <I, O> TwoWayConverter<I, O> converter(String name) {
        return new NamedConverter<>(name);
    }

    /**
     * Creates converter with provided name.
     */
    public static <I, O> TwoWayConverter<I, O> converter() {
        return converter("anonymous");
    }

    @Override
    public I convertBack(O output) {
        return (I) output;
    }

    @Override
    public O convert(I input) {
        return (O) input;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NamedConverter that = (NamedConverter) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
