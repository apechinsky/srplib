package net.srplib.validation;

import net.srplib.validation.Validatable;
import net.srplib.validation.ValidationError;
import net.srplib.validation.Validator;

/**
 * Base abstract class for validators implementations.
 *
 * <p>This base class simplifies validators creation because subclasses are responsible only for checking validity condition
 * and providing validation error. This class populates validatable with validation error (if any)</p>
 *
 * TODO: does this class make sense? Subclasses have no chance to add value validation error.  
 *
 * @author Anton Pechinsky
 */
public abstract class AbstractValidator<T> implements Validator<T> {

    @Override
    public void validate(Validatable<T> validatable) {
        if (!isValid(validatable)) {
            validatable.addError(newError());
        }
    }

    /**
     * Subclasses should implement this method and check validatable for errors.
     *
     * @param validatable Validatable an object to validate
     * @return true if specified validatable is valid, <code>false</code> otherwise
     */
    protected abstract boolean isValid(Validatable<T> validatable);

    /**
     * Subclasses should implement this method and
     * @return
     */
    protected abstract ValidationError newError();

}
