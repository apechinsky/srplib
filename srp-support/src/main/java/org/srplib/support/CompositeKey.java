package org.srplib.support;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.srplib.contract.Argument;

/**
 * Immutable composite key for map like structures.
 *
 * <p>Useful for creating object registries for searching object by multiple criteria.</p>
 *
 * <p>Keeps key components in list and reuses its equals and hashCode.</p>
 *
 * TODO: Consider immutable list from common-collections or google guava.
 *
 * @author Anton Pechinsky
 */
public final class CompositeKey implements Serializable {

    private final List<?> components;

    /**
     * Creates composite key.
     *
     * @param components List of key components
     */
    public CompositeKey(List<?> components) {
        Argument.checkNotNull(components, "Composite key component list must not be null!");
        this.components = Collections.unmodifiableList(components);
    }

    public CompositeKey(Object... components) {
        this(Arrays.asList(components));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != getClass()) {
            return false;
        }
        CompositeKey that = (CompositeKey)o;
        return components.equals(that.components);
    }

    @Override
    public int hashCode() {
        return components.hashCode();
    }

    @Override
    public String toString() {
        return "Key " + components.toString();
    }
}
