package org.srplib.validation;

/**
 * A simple implementation of {@link ValidationError} holding error message and a reference to not validated component.
 *
 * @author Anton Pechinsky
 */
public class DefaultValidationError implements ValidationError {

    private final String message;

    private final Object context;

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
     * @param context Object an error context (field, form, etc.)
     */
    public DefaultValidationError(String message, Object context) {
        this.context = context;
        this.message = message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getContext() {
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
