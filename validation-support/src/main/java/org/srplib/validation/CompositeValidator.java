package org.srplib.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Composite validators which aggregates other validators.
 *
 * <p>Useful for composing complex validators and representing them as a single validators. At validation time this
 * validators invokes all aggregated validators.</p>
 *
 * @author Anton Pechinsky
 */
public class CompositeValidator<T> implements Validator<T> {

    private List<Validator<T>> validators;

    /**
     * Creates composite validators.
     *
     * @param validators a list of validators to aggregate
     */
    public CompositeValidator(List<Validator<T>> validators) {
        if (validators == null) {
            throw new IllegalArgumentException("Validators collection must not be null.");
        }
        this.validators = new ArrayList<Validator<T>>(validators);
    }

    /**
     * Creates composite validators.
     *
     * @param validators a vararg array of validators to aggregate
     */
    public CompositeValidator(Validator<T>... validators) {
        this(Arrays.asList(validators));
    }

    /**
     * Adds validators.
     *
     * @param validators vararg array of validators.
     */
    public void add(Validator<T>... validators) {
        this.validators.addAll(Arrays.asList(validators));
    }

    @Override
    public void validate(Validatable<T> validatable) {
        for (Validator<T> validator : validators) {
            validator.validate(validatable);
        }
    }
}