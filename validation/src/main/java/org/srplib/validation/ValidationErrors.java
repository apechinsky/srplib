package org.srplib.validation;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Validation error collector object.
 *
 * <p>Is used by binding code to collect validation errors.</p>
 *
 * <p>Normally ValidationErrors is created by auto-binding code, but it can be created implicitly by throwing
 * validation exception:</p>
 * <pre>
 *  throw new ValidationException("error message");
 *  throw new ValidationException(Validators.newError("Error message", formField));
 * </pre>
 *
 * <p>or explicitly:
 * <pre>
 *  ValidationErrors validationErrors = new ValidationErrors();
 *  ...
 *  validationErrors.add(ValidationError.newError("Value should be positive.", ageField))
 *  ...
 *  if (validationErrorCollector.hasErrors()) {
 *      // take some action
 *  }
 * </pre>
 * </p>
 *
 * @author Anton Pechinsky
 */
public class ValidationErrors {

    private List<ValidationError> validationErrors = new LinkedList<ValidationError>();

    public ValidationErrors(List<ValidationError> validationErrors) {
        if (validationErrors == null) {
            throw new IllegalArgumentException("Validation errors list is null");
        }
        this.validationErrors.addAll(validationErrors);
    }

    public ValidationErrors(ValidationError... errors) {
        this(Arrays.asList(errors));
    }

    /**
     * Clears collected validation errors.
     */
    public void clear() {
        validationErrors.clear();
    }

    /**
     * Return all collected validation errors.
     *
     * @return List<ValidationError> list of validation erros.
     */
    public List<ValidationError> getErrors() {
        return Collections.unmodifiableList(validationErrors);
    }

    /**
     * Tests if this collector contains at least one validation error.
     *
     * @return true if this collection has validation errors, <code>false</code> otherwise.
     */
    public boolean hasErrors() {
        return !validationErrors.isEmpty();
    }

    /**
     * Adds validation error to this object.
     *
     * @param validationError ValidationError to add.
     */
    public void add(ValidationError... validationError) {
        validationErrors.addAll(Arrays.asList(validationError));
    }

    /**
     * Adds all validation errors to this object.
     *
     * @param validationErrors a List of ValidationError to add.
     */
    public void add(List<ValidationError> validationErrors) {
        this.validationErrors.addAll(validationErrors);
    }

    /**
     * Returns form fields with validation errors.
     *
     * @return Set of FormField
     */
    public Set<Object> getErrorContexts() {
        Set<Object> errorFields = new HashSet<Object>();
        for (ValidationError validationError : validationErrors) {
            if (validationError.getContext() != null) {
                errorFields.add(validationError.getContext());
            }
        }
        return errorFields;
    }

    /**
     * Find all validation errors for specified validation context.
     *
     * <p>Note, comparison is done by references!</p>
     *
     * @param context FormField to find errors for
     * @return List of {@link ValidationError}
     */
    public List<ValidationError> getErrorsFor(Object context) {
        List<ValidationError> fieldErrors = new LinkedList<ValidationError>();
        for (ValidationError validationError : validationErrors) {
            if (validationError.getContext() == context) {
                fieldErrors.add(validationError);
            }
        }
        return fieldErrors;
    }

    public String toString(String errorSeparator) {
        StringBuilder sb = new StringBuilder();
        for (ValidationError validationError : validationErrors) {
            if (validationError.getContext() != null) {
                sb.append(validationError.getContext().toString()).append(": ");
            }
            sb.append(validationError.getError());
            sb.append(errorSeparator);
        }
        return sb.toString();
    }
}
