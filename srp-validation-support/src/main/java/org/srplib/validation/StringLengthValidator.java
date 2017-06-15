package org.srplib.validation;

/**
 * Validator for checking if string lengths is in specified range.
 *
 * @author Anton Pechinsky
 */
public class StringLengthValidator extends org.srplib.validation.AbstractValidator<String> {

    private final int minimum;

    private final int maximum;

    /**
     * Creates validators with specified range.
     *
     * @param minimum minimal string length (inclusive).
     * @param maximum maximal string length (inclusive).
     */
    public StringLengthValidator(int minimum, int maximum) {
        if (minimum > maximum) {
            throw new IllegalArgumentException("Illegal range specification [" + minimum + ", " + maximum + "]");
        }
        this.minimum = minimum;
        this.maximum = maximum;
    }

    /**
     * Returns lower bound of range.
     *
     * @return lower bound (inclusive)
     */
    protected int getMinimum() {
        return minimum;
    }

    /**
     * Returns upper bound of range.
     *
     * @return upper bound (inclusive)
     */
    protected int getMaximum() {
        return maximum;
    }

    protected boolean isValid(Validatable<String> validatable) {
        String value = validatable.getValue();
        return value.length() >= minimum && value.length() <= maximum;
    }

    protected ValidationError newError() {
        return Validators.newError("String length should be in range " + minimum + " , " + maximum + ".");
    }
}