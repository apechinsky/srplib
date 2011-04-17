package org.srplib.criteria;

import java.io.Serializable;

/**
 * Encapsulates sort property name and its sort mode (ascending or descending).
 *
 * @author Q-APE
 */
public class SortEntry implements Serializable {

    private static final long serialVersionUID = -2533765751101419502L;

    private String property;

    private SortMode mode;

    /**
     * For serialization purposes
     */
    public SortEntry() {
    }

    /**
     * A factory method for sort entry creation with {@link SortMode#ASC} type
     *
     * @param property String property name
     * @return an instance of SortEntry
     */
    public static SortEntry asc(String property) {
        return new SortEntry(property, SortMode.ASC);
    }

    /**
     * A factory method for sort entry creation with {@link SortMode#DESC} type
     *
     * @param property String property name
     * @return an instance of SortEntry
     */
    public static SortEntry desc(String property) {
        return new SortEntry(property, SortMode.DESC);
    }


    /**
     * Creates sort entry with specified property name and sort mode.
     *
     * @param property String property name
     * @param mode SortMode sorting mode
     */
    public SortEntry(String property, SortMode mode) {
        this.property = property;
        this.mode = mode;
    }

    /**
     * Returns property name.
     *
     * @return String property name
     */
    public String getProperty() {
        return property;
    }

    /**
     * Returns sorting mode.
     *
     * @return SortMode sorting mode
     */
    public SortMode getMode() {
        return mode;
    }

    @Override
    public String toString() {
        return property + " " + mode + " ";
    }
}
