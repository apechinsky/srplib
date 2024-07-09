package org.srplib.conversion;

import java.util.List;

import org.srplib.contract.Argument;
import org.srplib.contract.Assert;
import org.srplib.reflection.ReflectionUtils;

/**
 * Base class for converter which knows about types of converting objects and can resolve these types by class declaration.
 */
public abstract class AbstractTypeAwareConverter<I, O> implements TypeAwareConverter<I, O> {

    private final Class<I> inputType;
    private final Class<O> outputType;

    /**
     * Constructor without types declaration.
     * <p>Extracts source and target types from type parameters (Generics)</p>
     */
    protected AbstractTypeAwareConverter() {
        List<Class<?>> parameters = ReflectionUtils.getTypeParameters(getClass());

        Assert.checkTrue(parameters.size() == 2, "Can't infer parameters of type '%s'", getClass());

        inputType = (Class<I>) parameters.get(0);
        outputType = (Class<O>) parameters.get(1);
    }

    /**
     * Construction with explicit types.
     *
     * @param inputType source type (non-null)
     * @param outputType target type (non-null)
     */
    protected AbstractTypeAwareConverter(Class<I> inputType, Class<O> outputType) {
        Argument.checkNotNullWithGenericMessage(inputType, "inputType");
        Argument.checkNotNullWithGenericMessage(outputType, "outputType");
        this.inputType = inputType;
        this.outputType = outputType;
    }

    @Override
    public Class<I> getInputType() {
        return inputType;
    }

    @Override
    public Class<O> getOutputType() {
        return outputType;
    }

}
