package net.srplib.conversion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.srplib.contract.Argument;
import net.srplib.conversion.Converter;
import net.srplib.conversion.ConverterException;

/**
 * Converts {@link String} to {@link Date} using specified pattern.
 *
 * @author Q-APE
 */
public class StringToDateConverter implements Converter<String, Date> {

    private static final String ISO_DATE_PATTERN = "yyyy-MM-dd";

    private String format;

    /**
     * Constructs converter using specified date format.
     *
     * @param format String date format. See {@link SimpleDateFormat} for syntax details.
     */
    public StringToDateConverter(String format) {
        Argument.notNull(format, "format");

        // fail-fast check
        createDateFormat(format);

        this.format = format;
    }

    public StringToDateConverter() {
        this(ISO_DATE_PATTERN);
    }

    public Date convert(String input) {
        try {
            Date result = null;
            if (!isBlank(input)) {
                result = createDateFormat(format).parse(input);
            }
            return result;
        }
        catch (ParseException e) {
            throw new ConverterException("Can't convert '" + input + "' to Date.", e);
        }
    }

    /**
     * //TODO: move to utilities
     *
     * @param string String to test
     * @return true if string is null, empty or contains whitespaces only
     */
    private boolean isBlank(String string) {
        return string == null || "".equals(string.trim());
    }

    private SimpleDateFormat createDateFormat(String format) {
        return new SimpleDateFormat(format);
    }

}
