package org.srplib.validation;

/**
 * Checks if value is in specified range.
 *
 * <p>May be applied to any subtype of {@link Comparable}.</p>
 *
 * @author Anton Pechinsky
 */
public class ValueRangeValidator<T extends Comparable<T>> extends org.srplib.validation.AbstractValidator<T> {

    private T lowerLimit;

    private T upperLimit;

    /**
     * Creates value range validators.
     *
     * @param lowerLimit lower limit (inclusive).
     * @param upperLimit upper limit (inclusive).
     */
    public ValueRangeValidator(T lowerLimit, T upperLimit) {
        if (lowerLimit.compareTo(upperLimit) > 0) {
            throw new IllegalArgumentException("Illegal range specification [" + lowerLimit + ", " + upperLimit + "]");
        }

        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }

    @Override
    protected boolean isValid(Validatable<T> validatable) {
        T value = validatable.getValue();
        return value.compareTo(lowerLimit) >= 0 && value.compareTo(upperLimit) <= 0;
    }

    @Override
    protected ValidationError newError() {
        return Validators.newError("Value is out of range [" + lowerLimit + ", " + upperLimit + "]");
    }
}