package org.srplib.reflection.deepcompare;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.srplib.contract.Argument;
import org.srplib.conversion.Converter;
import org.srplib.reflection.deepcompare.comparators.ReferenceComparatorDecorator;
import org.srplib.support.CompositeKey;

/**
 * Implements deep object comparison logic.
 *
 * @author Anton Pechinsky
 */
public class ConfigurableDeepComparator implements DeepComparator {

    private Set<CompositeKey> processedIdentities = new HashSet<>();

    private Converter<Class, DeepComparator> comparators;

    /**
     * Constructor.
     *
     * @param comparators Converter class to comparator registry.
     */
    public ConfigurableDeepComparator(Converter<Class, DeepComparator> comparators) {
        Argument.checkNotNull(comparators, "comparators must not be null!");

        this.comparators = comparators;
    }

    /**
     * Compares two objects using default configuration.
     *
     * @param object1 first object
     * @param object2 second object
     * @return list of mismatch description strings or empty list if no mismatches found.
     */
    public List<String> compare(Object object1, Object object2) {
        DeepComparatorContextImpl context = new DeepComparatorContextImpl(this);

        compare(object1, object2, context);

        return context.getMismatches();
    }

    @SuppressWarnings("unchecked")
    public void compare(Object object1, Object object2, DeepComparatorContext context) {

        if (alreadyProcessed(object1, object2)) {
            return;
        }
        rememberProcessed(object1, object2);

        DeepComparator comparator = getComparator(object1.getClass());

        comparator = new ReferenceComparatorDecorator(comparator);

        comparator.compare(object1, object2, context);
    }

    private DeepComparator getComparator(Class<?> type) {
        return comparators.convert(type);
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
