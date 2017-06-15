package org.srplib.validation;

/**
 * Validation exception is used to report validation errors.
 *
 * @author Anton Pechinsky
 */
public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = -1L;

    private ValidationErrors validationErrors;

    /**
     * Creates exception.
     *
     * @param message String exception message
     * @param throwable Throwable an underlying exception (nullable)
     * @param validationErrors ValidationErrors validation errors object.
     */
    public ValidationException(String message, Throwable throwable, ValidationErrors validationErrors) {
        super(message, throwable);

        this.validationErrors = validationErrors == null
            ? new ValidationErrors()
            : validationErrors;
    }

    public ValidationException(ValidationErrors validationErrors) {
        this("Validation error", null, validationErrors);
    }

    public ValidationException(ValidationError validationError) {
        this(new ValidationErrors(validationError));
    }

    public ValidationErrors getValidationErrors() {
        return validationErrors;
    }
}
