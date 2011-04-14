package net.srplib.validation;

import java.util.List;

/**
 * Encapsulates an object which can be validated.
 *
 * <p>Validatable is unaware of UI framework and contains only validation specific logic.</p>
 *
 * <p>Usage patterns:
 * <ol>
 *  <li>Extract value from to be-validated-component and wap it with Validatable implementation (e.g. DefaultValidatable).</li>
 *  <li>Pass resulting validatable to all associated validators</li>
 *  <li>Query validatable.hasErrors() method</li>
 * </ol>
 *
 * </p>
 *
 * @author Anton Pechinsky
 */
public interface Validatable<T> {

    /**
     * Returns value to be validated.
     *
     * @return an object representing value.
     */
    T getValue();

    /**
     * Tests if this object has errors or not.
     *
     * @return true if object has errors, <code>false</code> otherwise
     */
    boolean hasErrors();

    /**
     * Adds error to this object.
     *
     * @param error ValidationError an error.
     */
    void addError(ValidationError error);


    /**
     * Returns validation error associated with this object.
     *
     * @return a List of validation errors. If there are errors then empty list is returned.
     */
    List<ValidationError> getErrors();


    /**
     * Returns a reference to component which is being validated.
     *
     * @return Object validation context
     */
    Object getContext();

}
