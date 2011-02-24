package net.srplib.validation;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * An implementation of {@link Validatable} interface.
 *
 * @author Anton Pechinsky
 */
public class DefaultValidatable<T> implements Validatable<T> {

    private final T value;

    private final ValidationContext context;

    private List<ValidationError> errors = new LinkedList<ValidationError>();

    public DefaultValidatable(T value, ValidationContext context) {
        this.value = value;
        this.context = context;
    }

    public DefaultValidatable(T value) {
        this(value, null);
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    @Override
    public void addError(ValidationError error) {
        errors.add(error);
    }

    @Override
    public List<ValidationError> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    @Override
    public ValidationContext getContext() {
        return context;
    }


}
