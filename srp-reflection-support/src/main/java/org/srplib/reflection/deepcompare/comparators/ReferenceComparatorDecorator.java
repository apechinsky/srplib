package org.srplib.reflection.deepcompare.comparators;

import org.srplib.contract.Argument;
import org.srplib.reflection.deepcompare.DeepComparator;
import org.srplib.reflection.deepcompare.DeepComparatorContext;

/**
 * A comparator decorator which performs standard object reference checks.
 *
 * <ul>
 *     <li>Reference equality: object1 == object2</li>
 *
 *     <li>Null checks</li>
 *
 *     <li>delegates comparison to delegate comparator</li>
 * </ul>
 *
 * <p>Encourages to reuse reference check logic and frees other comparators from implementing it.</p>
 *
 * @author Anton Pechinsky
 */
public class ReferenceComparatorDecorator<T> implements DeepComparator<T> {

    private DeepComparator<T> delegate;

    public ReferenceComparatorDecorator(DeepComparator<T> delegate) {
        Argument.checkNotNull(delegate, "delegate must not be null!");

        this.delegate = delegate;
    }

    @Override
    public void compare(T object1, T object2, DeepComparatorContext context) {
        if (object1 == object2) {
            return;
        }

        if (object1 == null || object2 == null) {
            context.registerMismatch(String.format("Expected: '%s' actual: '%s'", object1, object2));
            return;
        }

        delegate.compare(object1, object2, context);
    }
}
