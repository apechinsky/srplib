package org.srplib.reflection.deepclone;

import org.srplib.reflection.deepcompare.DeepComparator;
import org.srplib.reflection.deepcompare.comparators.ReferenceComparatorDecorator;

/**
 * @author Q-APE
 */
public class DeepConverterImpl<I, O> implements DeepConverter<I, O> {

    private Class<I> inputType;

    private Class<O> outputType;


    @Override
    public O convert(I input, DeepConverterContext context) {
        if (alreadyProcessed(input)) {
            return getProcessedResult(input);
        }
;        rememberProcessed(input);

        DeepComparator comparator = getConverter(input.getClass());

        comparator = new ReferenceComparatorDecorator(comparator);

        comparator.compare(object1, object2, context);
    }

    public O convert(Class<I> inputType, Class<O> outputType, I input, DeepConverterContext context) {

    }

    private DeepComparator getConverter(Class<?> aClass) {
        return null;
    }

    private boolean alreadyProcessed(I input) {
        return false;
    }

    private O getProcessedResult(I input) {
        return null;
    }

    private void rememberProcessed(I input) {

    }
}
