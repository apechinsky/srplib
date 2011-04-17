package org.srplib.conversion;

/**
 * @author Anton Pechinsky
 */
public class StringToBooleanConverter implements Converter<String, Boolean> {

    public Boolean convert(String input) {
        return Boolean.valueOf(input);
    }
}