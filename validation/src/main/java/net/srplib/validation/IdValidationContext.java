package net.srplib.validation;

import net.srplib.validation.ValidationContext;

/**
 * @author Anton Pechinsky
 */
public class IdValidationContext<T> implements ValidationContext<T> {

    private T id;

    public IdValidationContext(T id) {
        this.id = id;
    }

    @Override
    public T getReference() {
        return id;
    }

}
