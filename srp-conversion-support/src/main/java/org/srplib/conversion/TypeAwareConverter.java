package org.srplib.conversion;

/**
 * Source and target type aware converter.
 */
public interface TypeAwareConverter<I, O> extends Converter<I, O> {

    /**
     * Source type.
     */
    Class<I> getInputType();

    /**
     * Target type.
     */
    Class<O> getOutputType();

}
