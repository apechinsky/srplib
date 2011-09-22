package org.srplib.conversion;

/**
 * Converts boolean to string.
 *
 * @author Anton Pechinsky
 */
public class BooleanToStringConverter implements Converter<Boolean, String> {

    public static final String YES = "yes";
    public static final String NO = "no";

    public static final String TRUE = "true";
    public static final String FALSE = "false";

    public static final String ONE = "1";
    public static final String ZERO = "0";

    private String trueString;

    private String falseString;

    /**
     * Creates converter with "true"/"false" strings.
     */
    public BooleanToStringConverter() {
        this(TRUE, FALSE);
    }

    /**
     * Creates converter with specified strings.
     *
     * @param trueString String string for {@code true} value
     * @param falseString String string for {@code false} value
     */
    public BooleanToStringConverter(String trueString, String falseString) {
        this.trueString = trueString;
        this.falseString = falseString;
    }

    @Override
    public String convert(Boolean input) {
        return (Boolean)input ? trueString : falseString;
    }

}
