package org.srplib.reflection.deepcompare.comparators;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.srplib.contract.Argument;
import org.srplib.reflection.ReflectionUtils;
import org.srplib.reflection.deepcompare.DeepComparator;
import org.srplib.reflection.deepcompare.DeepComparatorContext;
import org.srplib.support.Predicate;

/**
 * Compares objects field by field using reflection.
 *
 * @author Anton Pechinsky
 */
public class ReflectionDeepComparator implements DeepComparator<Object> {

    private Predicate<Field> comparables;

    /**
     * Constructor.
     *
     * @param comparables Predicate comparable class filter. Used to filter out static and other fields which are
     * should not be compared.
     */
    public ReflectionDeepComparator(Predicate<Field> comparables) {
        Argument.checkNotNull(comparables, "comparables must not be null!");
        this.comparables = comparables;
    }

    /**
     * Constructor with default field filter.
     */
    public ReflectionDeepComparator() {
        this(new NonStaticAndNonSynthenticFilter());
    }

    @Override
    public void compare(Object object1, Object object2, DeepComparatorContext context) {

        for (Field field : ReflectionUtils.getFieldsRecursively(object1.getClass())) {

            if (!comparables.test(field)) {
                continue;
            }

            Object value1 = ReflectionUtils.getFieldValue(object1, field);
            Object value2 = ReflectionUtils.getFieldValue(object2, field);

            context.compareNested(value1, value2, field.getName());
        }
    }

    private static class NonStaticAndNonSynthenticFilter implements Predicate<Field> {

        @Override
        public boolean test(Field field) {
            return !ReflectionUtils.isSyntheticName(field.getName()) && !Modifier.isStatic(field.getModifiers());
        }
    }
}
