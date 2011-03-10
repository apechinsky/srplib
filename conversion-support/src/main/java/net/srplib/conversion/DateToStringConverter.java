package net.srplib.conversion;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.srplib.contract.Argument;

/**
 * Converts Date to String using specified pattern.
 *
 * @author Anton Pechinsky
 */
public class DateToStringConverter implements Converter<Date, String> {

    private static final String ISO_DATE_PATTERN = "yyyy-MM-dd";

    private static final String NULL_DATE_STRING = "";

    private String format;

    /**
     * Constructs converter using specified date format
     *
     * @param format String date format. See {@link SimpleDateFormat} for syntax details.
     */
    public DateToStringConverter(String format) {
        Argument.notNull(format, "format");

        // fail-fast check
        createDateFormat(format);

        this.format = format;
    }

    public DateToStringConverter() {
        this(ISO_DATE_PATTERN);
    }


    public String convert(Date input) {
        return input == null ? NULL_DATE_STRING : createDateFormat(format).format(input);
    }

    private SimpleDateFormat createDateFormat(String format) {
        return new SimpleDateFormat(format);
    }
}
