package org.srplib.binding;

import org.srplib.validation.ValidationErrors;

/**
 * An interface for general purpose data binder.
 *
 * @author Anton Pechinsky
 */
public interface Binder<T> {

    /**
     * Bind specified object.
     *
     * @param object Object an object to bind.
     */
    void bind(T object);

    /**
     * Unbind specified object.
     *
     * @param object Object an object to unbind.
     * @return Object unbound object.
     * @throws org.srplib.validation.ValidationException in case of validation errors
     */
    T unbind(T object);

    /**
     * Unbind specified object.
     *
     * @param object Object an object to unbind.
     * @param errors ValidationErrors an object containing validation errors.
     * @return ValidationErrors validation errors object. Returns the same error instance as in a parameter.
     */
    ValidationErrors unbind(T object, ValidationErrors errors);
}
