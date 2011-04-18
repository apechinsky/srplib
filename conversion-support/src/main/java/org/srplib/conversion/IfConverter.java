package org.srplib.conversion;

/**
 * A converter encapsulating IF logic.
 *
 * <p>Input value is converted to boolean using following rules:
 * <ul>
 *     <li>if null then false</li>
 *     <li>if Boolean then return as is</li>
 *     <li>else Boolean.valueOf(value)</li>
 * </ul>
 * </p>
 *
 * @author Anton Pechinsky
 */
public class IfConverter<T> implements Converter<Object, T> {

    private T trueValue;

    private T falseValue;

    private IfConverter(T trueValue, T falseValue) {
        this.trueValue = trueValue;
        this.falseValue = falseValue;
    }

    @Override
    public T convert(Object input) {
        return asBoolean(input) ? trueValue : falseValue;
    }

    private boolean asBoolean(Object input) {
        if (input == null) {
            return false;
        }
        if (input instanceof Boolean) {
            return (Boolean)input;
        }
        return Boolean.valueOf(String.valueOf(input));
    }
}
