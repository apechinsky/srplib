package net.srplib.validation;

/**
 * A simple implementation of {@link ValidationError} holding error message and a reference to not validated component
 * ({@link ValidationContext}).
 *
 * @author Anton Pechinsky
 */
public class DefaultValidationError implements ValidationError {

    private final String message;

    private final ValidationContext context;

    /**
     * Creates validation error with error message.
     *
     * @param message String validation error message
     */
    public DefaultValidationError(String message) {
        this(message, null);
    }

    /**
     * Creates validation error with error message and invalid field.
     *
     * @param message String validation error message
     * @param context FormField an invalid form field
     */
    public DefaultValidationError(String message, ValidationContext context) {
        this.context = context;
        this.message = message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ValidationContext getContext() {
        return context;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getError() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
