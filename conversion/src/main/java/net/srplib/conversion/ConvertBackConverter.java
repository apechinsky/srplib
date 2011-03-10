package net.srplib.conversion;

import net.srplib.contract.Argument;

/**
 * An adapter for {@link TwoWayConverter} which implements {@link Converter} using
 * {@link TwoWayConverter#convertBack(Object)}} method.
 *
 * <p>Class allows use of {@link TwoWayConverter#convertBack(Object)}} method where Converter is expected</p>
 *
 * @author Q-APE
 */
public class ConvertBackConverter<O, I> implements Converter<O, I> {

    private TwoWayConverter<I, O> twoWayConverter;

    /**
     * Constructs class using specfied TwoWayConverter.
     *
     * @param twoWayConverter ConvertBackConverter
     */
    public ConvertBackConverter(TwoWayConverter<I, O> twoWayConverter) {
        Argument.notNull(twoWayConverter, "twoWayConverter");
        this.twoWayConverter = twoWayConverter;
    }

    @Override
    public I convert(O input) {
        return twoWayConverter.convertBack(input);
    }
}
