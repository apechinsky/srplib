package net.srplib.validation;


/**
 * Contains static factory method constructing validators.
 *
 * @author Anton Pechinsky
 */
public class Validators {

    /**
     * Creates new validation error basing on simple string message.
     *
     * @param message String error message.
     * @return ValidationError validation error
     */
    public static ValidationError newError(String message) {
        return new DefaultValidationError(message);
    }

    /**
     * Creates new validation error basing on simple string message and associate it with specified form field.
     *
     * @param message String error message.
     * @param formField FormField a field where validation error has been detected.
     * @return ValidationError validation error
     */
    public static ValidationError newError(String message, Object formField) {
        return new DefaultValidationError(message, formField);
    }

    /**
     * Creates string length validators. Checks if string length is in specified range.
     *
     * @param minimum minimal string length (inclusive).
     * @param maximum maximal string length (inclusive).
     * @return Validator<String>
     * @see StringLengthValidator
     */
    public static Validator<String> length(int minimum, int maximum) {
        return new StringLengthValidator(minimum, maximum);
    }

    /**
     * Creates minimal string length validators. Checks if string length is not less than specified minimum.
     *
     * @param minimum minimal string length (inclusive).
     * @return Validator<String>
     */
    public static Validator<String> minLength(int minimum) {
        return new StringMinLengthValidator(minimum);
    }

    /**
     * Creates maximal string length validators. Checks if string length is not greater than specified maximum.
     *
     * @param maximum maximal string length (inclusive).
     * @return Validator<String>
     */
    public static Validator<String> maxLength(int maximum) {
        return new StringMaxLengthValidator(maximum);
    }

    /**
     * Creates validators which matches string against specified regular expression.
     *
     * @param regexp String regular expression.
     * @return Validator<String>
     */
    public static Validator<String> regexp(String regexp) {
        return new RegexpValidator(regexp);
    }

    /**
     * Creates email address validators.
     *
     * @return Validator<String> email address validators.
     * @see EmailAddressValidator
     */
    public static Validator<String> email() {
        return new EmailAddressValidator();
    }

    /**
     * Creates phone validators.
     *
     * @return Validator<String> phone validators.
     */
    public static Validator<String> phone() {
        return new PhoneNumberValidator();
    }

    /**
     * Creates value range validators. Checks if value is in specified range.
     * May be applied to any subtype of {@link Comparable}.
     *
     * @param minimum minimal value (inclusive).
     * @param maximum maximal value (inclusive).
     * @return Validator<String> value range validators.
     * @see ValueRangeValidator
     */
    public static <T extends Comparable<T>> Validator<T> range(T minimum, T maximum) {
        return new ValueRangeValidator<T>(minimum, maximum);
    }

    /**
     * Creates number validators. Checks if {@link Number} is positive.
     *
     * @return Validator<String> positive number validators.
     * @see PositiveNumberValidator
     */
    public static <T extends Number> Validator<T> positive() {
        return new PositiveNumberValidator<T>();
    }
}
