package org.srplib.reflection.deepcompare.comparators;

import java.util.List;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.srplib.reflection.deepcompare.DeepComparatorContext;

/**
 * Test for ListDeepComparator
 *
 * @author Anton Pechinsky
 */
@RunWith(MockitoJUnitRunner.class)
public class ListDeepComparatorTest {

    @Mock
    private DeepComparatorContext context;

    private ListDeepComparator comparator = new ListDeepComparator();

    @Test
    public void normal() throws Exception {
        List<Integer> list1 = asList(1, 2, 0);
        List<Integer> list2 = asList(1, 0, 3);

        comparator.compare(list1, list2, context);

        Mockito.verify(context).compareNested(1, 1, "[0]");
        Mockito.verify(context).compareNested(2, 0, "[1]");
        Mockito.verify(context).compareNested(0, 3, "[2]");

        Mockito.verifyNoMoreInteractions(context);
    }

    @Test
    public void differentSizes() throws Exception {
        List<Integer> list1 = asList(1, 2, 3);
        List<Integer> list2 = asList(1, 2, 3, 4);

        comparator.compare(list1, list2, context);

        Mockito.verify(context).registerMismatch(
            "Lists have different size. Expected: %d actual: %d", list1.size(), list2.size());

        Mockito.verifyNoMoreInteractions(context);
    }

    @Test
    public void emptyLists() throws Exception {
        comparator.compare(emptyList(), emptyList(), context);

        Mockito.verifyNoMoreInteractions(context);
    }

}