package org.srplib.reflection.deepcompare.comparators;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.srplib.reflection.deepcompare.DeepComparatorContext;

/**
 * Test for ArrayDeepComparator
 *
 * @author Anton Pechinsky
 */
@RunWith(MockitoJUnitRunner.class)
public class ArrayDeepComparatorTest {

    @Mock
    private DeepComparatorContext context;

    private ArrayDeepComparator comparator = new ArrayDeepComparator();

    @Test
    public void normal() throws Exception {
        Integer[] array1 = {1, 2, 0};
        Integer[] array2 = {1, 0, 3};

        comparator.compare(array1, array2, context);

        Mockito.verify(context).compareNested(1, 1, "[0]");
        Mockito.verify(context).compareNested(2, 0, "[1]");
        Mockito.verify(context).compareNested(0, 3, "[2]");

        Mockito.verifyNoMoreInteractions(context);
    }

    @Test
    public void differentSizes() throws Exception {
        Integer[] array1 = {1, 2, 3};
        Integer[] array2 = {1, 2, 3, 4};

        comparator.compare(array1, array2, context);

        Mockito.verify(context).registerMismatch(
            "Array size mismatch. Expected: %d actual: %d", array1.length, array2.length);

        Mockito.verifyNoMoreInteractions(context);
    }

    @Test
    public void emptyArrays() throws Exception {
        comparator.compare(new int[0], new int[0], context);

        Mockito.verifyNoMoreInteractions(context);
    }

}