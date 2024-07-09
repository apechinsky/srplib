package org.srplib.conversion.registrar;

import org.srplib.conversion.Converter;
import org.srplib.conversion.Registrar;

/**
 * Base converters registrar.
 *
 * <p>
 *     Implements common functionality for most registrars:
 *     <ul>
 *         <li>stores source and target types</li>
 *         <li>stores converter instance</li>
 *     </ul>
 * </p>
 *
 * @param <I> input type
 * @param <O> output type
 */
public abstract class AbstractRegistrar<I, O> implements Registrar {

    private Class<I> inputType;

    private Class<O> outputType;

    private Converter<I, O> converter;

    /**
     * Constructor.
     *
     * @param inputType source type (non-null)
     * @param outputType target type (non-null)
     * @param converter converter (non-null)
     */
    public AbstractRegistrar(Class<I> inputType, Class<O> outputType, Converter<I, O> converter) {
        this.outputType = outputType;
        this.inputType = inputType;
        this.converter = converter;
    }

    public Class<I> getInputType() {
        return inputType;
    }

    public Class<O> getOutputType() {
        return outputType;
    }

    public Converter<I, O> getConverter() {
        return converter;
    }
}

