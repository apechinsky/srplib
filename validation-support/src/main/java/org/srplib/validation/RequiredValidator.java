package org.srplib.validation;

/**
 * Validates if value is null.
 *
 * TODO: do we need this validators if required validation is a special case in validation process?
 *
 * @author Anton Pechinsky
 */
public class RequiredValidator extends org.srplib.validation.AbstractValidator<Object> {

    @Override
    protected boolean isValid(Validatable<Object> validatable) {
        return validatable.getValue() != null;
    }

    @Override
    protected ValidationError newError() {
        return Validators.newError("Value should not be null");
    }
}
