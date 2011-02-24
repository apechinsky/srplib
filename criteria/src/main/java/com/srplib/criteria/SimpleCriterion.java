package com.srplib.criteria;

import java.io.Serializable;

/**
 * An implementation of {@link Criterion} containing property operation and value.
 *
 * @author Q-APE
 */
public class SimpleCriterion implements Criterion {

    private static final long serialVersionUID = -1L;

    private String property;

    private Operation operation;

    private Serializable value;

    /**
     * For serialization purposes
     */
    private SimpleCriterion() {
    }

    /**
     * Creates simple criteria.
     *
     * @param property String property/field name
     * @param operation Operation to use for comparison
     * @param value value to use for comparison.
     */
    public SimpleCriterion(String property, Operation operation, Object value) {
        this.property = property;
        this.operation = operation;
        this.value = (Serializable) value;
    }

    /**
     * Returns property/field name
     *
     * @return String property/field name.
     */
    public String getProperty() {
        return property;
    }

    /**
     * Returns operation for comparison.
     *
     * @return an instance of Operation
     */
    public Operation getOperation() {
        return operation;
    }

    /**
     * Returns value
     *
     * @return a value to use in comparison.
     */
    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "" + property + " " + operation.getSymbol() + " " + value + "";
    }
}
