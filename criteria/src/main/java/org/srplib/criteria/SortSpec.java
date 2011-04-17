package org.srplib.criteria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Models sort clause. Maintains list of sort properties and their sort types.
 *
 * @author Q-APE
 */
public class SortSpec implements Serializable {

    private static final long serialVersionUID = 5281766070650983916L;
    
    private List<SortEntry> entries;

    /**
     * Creates empty sort clause.
     *
     * @return SortClause containing empty entry list.
     */
    public static SortSpec empty() {
        return new SortSpec(Collections.<SortEntry>emptyList());
    }

    /**
     * Factory method for quick sort clause creation.
     *
     * @param property String sort property name
     * @param mode SortMode to use for specified property
     * @return SortClause for specified property
     */
    public static SortSpec create(String property, SortMode mode) {
        return new SortSpec(Arrays.asList(new SortEntry(property, mode)));
    }

    public SortSpec() {
        this(Collections.<SortEntry>emptyList());
    }

    /**
     * Creates sort clause with specified list of sort entries.
     *
     * @param entries List of {@link SortEntry}
     */
    private SortSpec(List<SortEntry> entries) {
        if (entries == null) {
            throw new IllegalArgumentException("Sort entries must not be null");
        }
        this.entries = new ArrayList<SortEntry>(entries);
    }

    /**
     * Returns list of sort entries.
     *
     * @return List of {@link SortEntry}
     */
    public List<SortEntry> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    /**
     * Adds new sort entry to current sort clause.
     *
     * @param property String sort property name
     * @param mode SortMode to use for specified property
     * @return new instance of {@link SortSpec}. Return type is u
     */
    public SortSpec add(String property, SortMode mode) {
        return add(new SortEntry(property, mode));
    }

    public SortSpec add(SortEntry sortEntry) {
        List<SortEntry> newEntries = new ArrayList<SortEntry>(entries);
        newEntries.add(sortEntry);
        return new SortSpec(newEntries);
    }

    @Override
    public String toString() {
        return "SortSpec {" + entries + "}";
    }
}
