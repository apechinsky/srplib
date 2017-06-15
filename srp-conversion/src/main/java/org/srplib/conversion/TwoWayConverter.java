package org.srplib.conversion;
 

/**
 * An extension of {@link Converter} containing a method for reverse conversion ({@link #convertBack(Object)}}).
 *
 * @author Anton Pechinsky
 */
public interface TwoWayConverter<I, O> extends Converter<I, O>{

    /**
     * Converts specified value.
     *
     * @param output a value to convert.
     * @return an input value.
     * @throws ConverterException if value can't be converted.
     */
    I convertBack(O output);

}
