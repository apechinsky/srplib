package org.srplib.model;

/**
 * A context aware extension of {@link ValueModel} interface.
 *
 * <p>Some models may retrieve values from external sources (context) and these sources not may not available all the time.
 * Models of this type should be attached to context before model's value is queried.</p>
 *
 * <p>A typical example of such model is BeanPropertyValueModel which contains name of property but doesn't contain
 * bean itself. At unbind time a bean is injected by {@link #setContext(Object)} method and model's value is queried.</p>
 *
 * <p>If no context specified when value is queried then an exception is thrown.</p>
 *
 * @author Anton Pechinsky
 */
public interface ContextDependentValueModel<C, T> extends ValueModel<T> {

    /**
     * Set context.
     *
     * @param context an object representing context.
     */
    void setContext(C context);

}
