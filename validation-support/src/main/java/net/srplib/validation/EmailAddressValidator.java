package net.srplib.validation;

/**
 * Validates if string represents email address.
 *
 * @author Anton Pechinsky
 */
public class EmailAddressValidator extends RegexpValidator {

    private static final String DEFAULT_MESSAGE_PATTERN = "Invalid email address.";

    private static final String EMAIL_REGEXP_PATTERN =
        "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z]{2,}){1}$)";

    public EmailAddressValidator() {
        super(EMAIL_REGEXP_PATTERN);
    }

    protected ValidationError newError() {
        return Validators.newError(DEFAULT_MESSAGE_PATTERN);
    }
}