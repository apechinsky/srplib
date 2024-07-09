package org.srplib.conversion;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Generic object conversion service.
 * <p>Used to structure conversion layers code (DTO <-> model and so on)</p>
 */
public interface ConversionService {

    /**
     * Converts value from source type into target type
     * <p>Basic interface method to convert values.</p>
     *
     * @param inputType source type
     * @param outputType target type
     * @param input converting value
     * @return converted value
     * @throws ConverterException in case of conversion errors
     */
    <I, O> O convert(Class<I> inputType, Class<O> outputType, I input);

    /**
     * {@link #convert(Class, Class, Object)} analog with automatic source type resolving.
     *
     * @param outputType target type
     * @param input converting value
     * @return converted value
     */
    default <I, O> O convert(Class<O> outputType, I input) {
        return convert(getInputType(input), outputType, input);
    }

    /**
     * {@link #convert(Class, Class, Object)} analog with automatic source and target type resolving.
     *
     * @param input converting value
     * @return converted value
     */
    default <I, O> O convert(I input) {
        return convert(getInputType(input), (Class<O>)ConverterRegistry.ANY_TYPE, input);
    }

    /**
     * Objects stream/array/collection conversion.
     *
     * @param outputType target type for stream elements
     * @param input converting collection (nullable)
     * @return converted objects stream/array/collection (nullable). {@code null} if input is {@code null}.
     */
    default <I, O> Stream<O> convertStream(Class<O> outputType, Stream<I> input) {
        if (input == null) {
            return null;
        }
        return input.map(i -> convert(outputType, i));
    }

    /**
     * Objects stream/array/collection conversion.
     *
     * @param outputType target type for stream elements
     * @param input converting collection (nullable)
     * @return converted objects stream/array/collection (nullable). {@code null} if input is {@code null}.
     */
    default <I, O> List<O> convertList(Class<O> outputType, I... input) {
        if (input == null) {
            return null;
        }
        return convertStream(outputType, Arrays.stream(input)).collect(Collectors.toList());
    }

    /**
     * Objects stream/array/collection conversion.
     *
     * @param outputType target type for stream elements
     * @param input converting collection (nullable)
     * @return converted objects stream/array/collection (nullable). {@code null} if input is {@code null}.
     */
    default <I, O> List<O> convertList(Class<O> outputType, Collection<I> input) {
        if (input == null) {
            return null;
        }
        return convertStream(outputType, input.stream()).collect(Collectors.toList());
    }

    /**
     * Objects stream/array/collection conversion.
     *
     * @param generator resulting array constructor
     * @param input converting collection (nullable)
     * @return converted objects stream/array/collection (nullable). {@code null} if input is {@code null}.
     */
    default <I, O> O[] convertArray(IntFunction<O[]> generator, I... input) {
        if (input == null) {
            return null;
        }
        return convertStream(getComponentType(generator), Arrays.stream(input)).toArray(generator);
    }

    /**
     * Objects stream/array/collection conversion.
     *
     * @param generator resulting array constructor
     * @param input converting collection (nullable)
     * @return converted objects stream/array/collection (nullable). {@code null} if input is {@code null}.
     */
    default <I, O> O[] convertArray(IntFunction<O[]> generator, Collection<I> input) {
        if (input == null) {
            return null;
        }
        return convertStream(getComponentType(generator), input.stream()).toArray(generator);
    }

    static <X> Class<X> getInputType(X obj) {
        return (Class) (obj == null ? Void.class : obj.getClass());
    }

    static <X> Class<X> getComponentType(IntFunction<X[]> generator) {
        return (Class) generator.apply(0).getClass().getComponentType();
    }
}

