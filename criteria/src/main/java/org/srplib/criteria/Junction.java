package org.srplib.criteria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Represents junction operation for criteria joining (NOT, AND, OR etc.).
 *
 * @author Q-APE
 */
public class Junction implements Criterion {

    private static final long serialVersionUID = -1L;

    private JunctionType type;

    private List<Criterion> criteria;

    /**
     * For serialization purposes
     */
    private Junction() {
    }

    /**
     * Constructs junction with specified type and list of criteria
     *
     * @param type JunctionType type of junction
     * @param criteria List of {@link Criterion}
     */
    public Junction(JunctionType type, List<Criterion> criteria) {
        if (type == null) {
            throw new IllegalArgumentException("'type' should not be null");
        }
        if (criteria == null || criteria.isEmpty()) {
            throw new IllegalArgumentException("'criteria' should not be null or empty");
        }
        if (!type.isMultivalue() && criteria.size() > 1) {
            throw new IllegalArgumentException("'" + type + "' doesn't support multiple values.");
        }

        this.type = type;
        this.criteria = new ArrayList<Criterion>(criteria);
    }

    /**
     * Returns type of junction.
     *
     * @return JunctionType type of junction
     */
    public JunctionType getType() {
        return type;
    }

    /**
     * Returns joined criteria.
     *
     * @return List of {@link Criteria}.
     */
    public List<Criterion> getCriteria() {
        return Collections.unmodifiableList(criteria);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(type).append("(");
        for (Iterator<Criterion> iterator = criteria.iterator(); iterator.hasNext();) {
            stringBuilder.append(iterator.next());
            stringBuilder.append(iterator.hasNext() ? "," : "");
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
