package org.srplib.validation;

/**
 * Validates if string length is not more than specified value.
 *
 * @author Q-GMA
 */
public class StringMaxLengthValidator extends StringLengthValidator {
    /**
     * Creates validators with specified range.
     *
     * @param maximum maximal string length (inclusive).
     */
    public StringMaxLengthValidator(int maximum) {
        super(0, maximum);
    }

    protected ValidationError newError() {
        return Validators.newError("Field should be filled with up to " + getMaximum() + " characters");
    }
}
