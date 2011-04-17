package org.srplib.criteria;

/**
 * Types of criteria join operations.
 *
 * @author Q-APE
 */
public enum JunctionType {

    /**
     * Represents logical NOT operation.
     */
    NOT(false),

    /**
     * Represents logical AND operation.
     */
    AND(true),

    /**
     * Represents logical OR operation.
     */
    OR(true);

    private boolean multivalue;

    JunctionType(boolean supportsMultipleValues) {
        this.multivalue = supportsMultipleValues;
    }

    /**
     * Tests if current operation is able to join multiple criteria
     *
     * @return true if multiple criteria is supported
     */
    public boolean isMultivalue() {
        return multivalue;
    }
}
