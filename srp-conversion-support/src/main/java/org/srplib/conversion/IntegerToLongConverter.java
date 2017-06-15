package org.srplib.conversion;

/**
 * @author Anton Pechinsky
 */
public class IntegerToLongConverter implements Converter<Integer, Long> {

    public Long convert(Integer input) {
        return input.longValue();
    }
}