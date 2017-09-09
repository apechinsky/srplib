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

    private boolean checkClass;

    public ReferenceComparatorDecorator(DeepComparator<T> delegate, boolean checkClass) {
        Argument.checkNotNull(delegate, "delegate must not be null!");

        this.delegate = delegate;
        this.checkClass = checkClass;
    }

    public ReferenceComparatorDecorator(DeepComparator<T> delegate) {
        this(delegate, false);
    }

    @Override
    public void compare(T object1, T object2, DeepComparatorContext context) {
        if (object1 == object2) {
            return;
        }

        if (object1 == null || object2 == null) {
            context.registerMismatch("Compare null and non-null values. Expected: '%s' actual: '%s'", object1, object2);
            return;
        }

        if (checkClass && object1.getClass() != object2.getClass()) {
            context.registerMismatch("Different classes. Expected: '%s' actual: '%s'",
                object1.getClass(), object2.getClass());
            return;
        }

        delegate.compare(object1, object2, context);
    }
}
