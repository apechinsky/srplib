package org.srplib.reflection.deepcompare.comparators;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.srplib.reflection.deepcompare.DeepComparator;
import org.srplib.reflection.deepcompare.DeepComparatorContext;

/**
 * Test for ReferenceComparatorDecorator
 *
 * @author Anton Pechinsky
 */
@RunWith(MockitoJUnitRunner.class)
public class ReferenceComparatorDecoratorTest {

    private static final Object OBJECT = new Object();

    @Mock
    private DeepComparatorContext context;

    @Mock
    private DeepComparator<Object> delegate;

    private ReferenceComparatorDecorator<Object> comparator;

    @Before
    public void setUp() throws Exception {
        comparator = new ReferenceComparatorDecorator<>(delegate);
    }

    @Test
    public void theSame() throws Exception {
        comparator.compare(OBJECT, OBJECT, context);

        Mockito.verifyNoMoreInteractions(context);
    }

    @Test
    public void firstIsNull() throws Exception {
        comparator.compare(null, OBJECT, context);

        Mockito.verify(context).registerMismatch(
            "Compare null and non-null values. Expected: '%s' actual: '%s'", null, OBJECT);

        Mockito.verifyNoMoreInteractions(context);
    }

    @Test
    public void secondIsNull() throws Exception {
        comparator.compare(OBJECT, null, context);

        Mockito.verify(context).registerMismatch(
            "Compare null and non-null values. Expected: '%s' actual: '%s'", OBJECT, null);

        Mockito.verifyNoMoreInteractions(context);
    }

    @Test
    public void ignoreClasses() throws Exception {
        DeepComparator<Object> comparator = new ReferenceComparatorDecorator<>(delegate, false);

        comparator.compare("a", 5, context);

        Mockito.verify(delegate).compare("a", 5, context);

        Mockito.verifyNoMoreInteractions(context);
    }

    @Test
    public void checkClasses() throws Exception {
        DeepComparator<Object> comparator = new ReferenceComparatorDecorator<>(delegate, true);

        comparator.compare("a", 5, context);

        Mockito.verify(context).registerMismatch(
            "Different classes. Expected: '%s' actual: '%s'", "a".getClass(), Integer.class);

        Mockito.verifyNoMoreInteractions(context);
    }

    @Test
    public void notEqual() throws Exception {
        comparator.compare("a", "b", context);

        Mockito.verify(delegate).compare("a", "b", context);

        Mockito.verifyNoMoreInteractions(delegate, context);
    }
}