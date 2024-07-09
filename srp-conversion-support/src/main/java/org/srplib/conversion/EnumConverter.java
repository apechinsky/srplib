package org.srplib.conversion;

/**
 * {@link Enum} types converter
 * <p>Matching is built by enum constant names</p>
 */
public class EnumConverter<I extends Enum, O extends Enum> extends AbstractTypeAwareConverter<I, O>
    implements TwoWayConverter<I, O> {

    public EnumConverter(Class<I> inputType, Class<O> outputType) {
        super(inputType, outputType);
    }

    @Override
    public O convert(I input) {
        return convert(getOutputType(), input);
    }

    @Override
    public I convertBack(O output) {
        return convert(getInputType(), output);
    }

    public static <X extends Enum, Y extends Enum> Y convert(Class<Y> targetClass, X input) {
        return (Y) Enum.valueOf(targetClass, input.name());
    }

}
