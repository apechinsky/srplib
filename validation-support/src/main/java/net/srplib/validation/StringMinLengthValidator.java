package net.srplib.validation;

/**
 * Validates if string length is not less than specified value.
 *
 * @author Anton Pechinsky
 */
public class StringMinLengthValidator extends StringLengthValidator {

    public StringMinLengthValidator(int minimum) {
        super(minimum, Integer.MAX_VALUE);
    }

    protected ValidationError newError() {
        return Validators.newError("String length should NOT be shorter than " + getMinimum());
    }
}