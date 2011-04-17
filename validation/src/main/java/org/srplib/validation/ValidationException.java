package org.srplib.validation;

/**
 * Validation exception is used to report validation errors.
 *
 * @author Anton Pechinsky
 */
public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = -1L;

    private ValidationErrors validationErrors;

    public ValidationException(ValidationErrors validationErrors) {
        this.validationErrors = validationErrors;
    }

    public ValidationException(ValidationError validationError) {
        this(new ValidationErrors(validationError));
    }

    public ValidationErrors getValidationErrors() {
        return validationErrors;
    }
}
