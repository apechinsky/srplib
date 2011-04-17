package org.srplib.validation;

/**
 * Validates if {@link Number} is positive.
 *
 * @author Anton Pechinsky
 */
public class PositiveNumberValidator<T extends Number> implements Validator<T> {

    @Override
    public void validate(Validatable<T> validatable) {
        Number value = validatable.getValue();
        boolean valid = value.doubleValue() > 0.0d;
        if (!valid) {
            validatable.addError(Validators.newError("Value should be positive."));
        }

    }
}