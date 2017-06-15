package org.srplib.validation;

import java.io.Serializable;

/**
 * Models single validation error. Holds error message and optionally an invalid form field.
 *
 * @author Anton Pechinsky
 */
public interface ValidationError extends Serializable {

    /**
     * Returns validation error message.
     *
     * @return String error message.
     */
    String getError();

    /**
     * Returns validation context.
     *
     * <p>This may be a field, a form, a panel etc. In other words context is reference to object is being validated.</p>
     *
     * @return Object an object representing context, <code>null</code> if error isn't associated with particular context.
     */
    Object getContext();

}
