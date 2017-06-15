package org.srplib.validation;

/**
 * Validates strings with regular expression.
 *
 * @author Anton Pechinsky
 */
public class ConstantValidator extends org.srplib.validation.AbstractValidator<String> {

    private final boolean valid;

    private final String message;

    public ConstantValidator(boolean valid, String message) {
        this.valid = valid;
        this.message = message;
    }

    protected boolean isValid(Validatable<String> validatable) {
        return valid;
    }

    protected ValidationError newError() {
        return Validators.newError(message);
    }
}