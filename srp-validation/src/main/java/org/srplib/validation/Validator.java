package org.srplib.validation;

/**
 * Base interface for object that can validate {@link Validatable} objects.
 *
 * @author Anton Pechinsky
 */
public interface Validator<T> {

    /**
     * Validates provided validatable object. Implementation should validate value from validatable object and set validation 
     * error if value isn't valid.
     *
     * @param validatable Validadable object to validate.
     * @see Validatable
     */
    void validate(Validatable<T> validatable);

}
