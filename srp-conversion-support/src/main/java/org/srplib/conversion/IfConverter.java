package org.srplib.conversion;

/**
 * A converter encapsulating IF logic.
 *
 * <p>Input value is converted to boolean using following rules:
 * <ul>
 * <li>if null then false</li>
 * <li>if Boolean then return as is</li>
 * <li>else Boolean.valueOf(value)</li>
 * </ul>
 * </p>
 *
 * @author Anton Pechinsky
 */
public class IfConverter<I, O> implements Converter<I, O> {

    private O trueValue;

    private O falseValue;

    /**
     * An alternative to constructor.
     *
     * @param trueValue a value to be returned if expression is true. {@code null} is legal value.
     * @param falseValue a value to be returned if expression is false. {@code null} is legal value.
     * @param <I> input value type
     * @param <O> output value type
     * @return IfConverter
     */
    public static <I, O> IfConverter<I, O> ifConverter(O trueValue, O falseValue) {
        return new IfConverter<I, O>(trueValue, falseValue);
    }

    /**
     * Creates converter using specified true an false values.
     *
     * @param trueValue a value to be returned if expression is true. {@code null} is legal value.
     * @param falseValue a value to be returned if expression is false. {@code null} is legal value.
     */
    public IfConverter(O trueValue, O falseValue) {
        this.trueValue = trueValue;
        this.falseValue = falseValue;
    }

    @Override
    public O convert(I input) {
        return asBoolean(input) ? trueValue : falseValue;
    }

    private boolean asBoolean(Object input) {
        if (input == null) {
            return false;
        }
        if (input instanceof Boolean) {
            return (Boolean) input;
        }
        return Boolean.valueOf(String.valueOf(input));
    }
}
