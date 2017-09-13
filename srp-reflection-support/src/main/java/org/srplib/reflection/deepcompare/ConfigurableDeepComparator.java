package org.srplib.reflection.deepcompare;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.srplib.contract.Argument;
import org.srplib.reflection.deepcompare.comparators.ReferenceComparatorDecorator;
import org.srplib.reflection.deepcompare.support.StandardConfiguration;
import org.srplib.support.CompositeKey;

/**
 * Implements deep object comparison logic.
 *
 * @author Anton Pechinsky
 */
public class ConfigurableDeepComparator implements DeepComparator {

    private Set<CompositeKey> processedIdentities = new HashSet<>();

    private DeepComparatorConfiguration configuration;

    /**
     * Constructor.
     *
     * @param configuration DeepComparatorConfiguraiton class to comparator registry.
     */
    public ConfigurableDeepComparator(DeepComparatorConfiguration configuration) {
        Argument.checkNotNull(configuration, "configuration must not be null!");

        this.configuration = configuration;
    }

    /**
     * Constructs comparator with default configuration.
     */
    public ConfigurableDeepComparator() {
        this(new StandardConfiguration());
    }

    /**
     * Compares two objects using default configuration.
     *
     * @param object1 first object
     * @param object2 second object
     * @return list of mismatch description strings or empty list if no mismatches found.
     */
    public List<String> compare(Object object1, Object object2) {
        ReferenceComparatorDecorator rootComparator = new ReferenceComparatorDecorator(this);

        DeepComparatorContextImpl context = new DeepComparatorContextImpl(rootComparator);

        compare(object1, object2, context);

        return context.getMismatches();
    }

    @SuppressWarnings("unchecked")
    public void compare(Object object1, Object object2, DeepComparatorContext context) {

        if (alreadyProcessed(object1, object2)) {
            return;
        }
        rememberProcessed(object1, object2);

        DeepComparator comparator = configuration.getComparator(object1.getClass());

        comparator = new ReferenceComparatorDecorator(comparator);

        comparator.compare(object1, object2, context);
    }

    private void rememberProcessed(Object object1, Object object2) {
        CompositeKey key = getKey(object1, object2);
        processedIdentities.add(key);
    }

    private boolean alreadyProcessed(Object object1, Object object2) {
        CompositeKey key = getKey(object1, object2);
        return processedIdentities.contains(key);
    }

    private CompositeKey getKey(Object object1, Object object2) {
        return new CompositeKey(identity(object1), identity(object2));
    }

    private int identity(Object object) {
        return System.identityHashCode(object);
    }

}
