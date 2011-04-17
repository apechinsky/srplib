package org.srplib.validation;

/**
 * Validates phone numbers
 *
 * @author Q-GMA
 */
public class PhoneNumberValidator extends RegexpValidator {

    private static final String DEFAULT_MESSAGE_PATTERN =
        "The value of field should start with a \"0\" and could be up to a maximum of 12 additional numbers: \"0XXXXXXXXXXXX\"";

    private static final String REGEXP_PATTERN = "^0\\d{0,12}$";

    /**
     * Creates validators.
     */
    public PhoneNumberValidator() {
        super(REGEXP_PATTERN);
    }

    protected ValidationError newError() {
        return Validators.newError(DEFAULT_MESSAGE_PATTERN);
    }
}
