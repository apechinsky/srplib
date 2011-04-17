package org.srplib.validation;

/**
 * Validates strings with regular expression.
 *
 * @author Anton Pechinsky
 */
public class RegexpValidator extends org.srplib.validation.AbstractValidator<String> {

    private final String regexp;

    /**
    * Creates validators.
    *
    * @param regexp String regular expression.
    */
    public RegexpValidator(String regexp) {
        this.regexp = regexp;
    }

    protected boolean isValid(Validatable<String> validatable) {
        String value = validatable.getValue();
        return value.matches(regexp);
    }

    protected ValidationError newError() {
        // TODO: replace with string formatting
        return Validators.newError("String doesn't match pattern '" + regexp + "'");
    }
}