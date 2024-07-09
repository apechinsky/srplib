package org.srplib.conversion.registry;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.srplib.contract.Assert;
import org.srplib.conversion.Converter;
import org.srplib.conversion.ConverterException;
import org.srplib.conversion.ConverterRegistry;

/**
 * A class for organizing operation.
 *
 * <p>Contains method for registering and obtaining type operation</p>
 */
public class SupertypeConverterRegistry implements ConverterRegistry {

    /**
     * Converters tree.
     * <p>
     *     Contains data in the following structure: (source class -> (target class -> converter))
     * </p>
     * <p>
     *     Example
     *     <pre>
     *         Map(
     *              String.class -> Map(
     *                  Integer.class -> new StringToIntegerConverter(),
     *                  Double.class -> new StringToDoubleConverter(),
     *              )
     *         )
     *     </pre>
     * </p>
     */
    private Map<Class, Map<Class, Converter>> converterTree = new HashMap<>();


    @Override
    public <I, O> void add(Class<I> inputType, Class<O> outputType, Converter<I, O> converter) {
        Map<Class, Converter> map = converterTree.computeIfAbsent(inputType, type -> new HashMap<>());

        Assert.checkFalse(map.containsKey(outputType),
            "Registering several operation for %s -> %s", inputType, outputType);

        map.put(outputType, converter);
    }

    @Override
    public <I, O> Converter<I, O> find(Class<I> source, Class<O> target) {
        Map<Class<O>, Converter<I, O>> converters = findConverters(source, target);

        switch (converters.size()) {
            case 0:
                return null;
            case 1:
                return converters.values().iterator().next();
            default:
                throw new ConverterException(
                    String.format("Too many operation found for %s -> %s", source, target));
        }
    }

    /**
     * @param inputType source type
     * @param outputType target type
     *
     * @return converters subset as a map of (output type -> converter) for two provided types
     */
    private <I, O> Map<Class<O>, Converter<I, O>> findConverters(Class<I> inputType, Class<O> outputType) {

        for (Class inputSuperType = inputType; inputSuperType != null; inputSuperType = inputSuperType.getSuperclass()) {
            Map<Class, Converter> map = converterTree.getOrDefault(inputSuperType, Collections.emptyMap());
            Set<Class> possibleOutputClasses = map.keySet().stream().filter(outputType::isAssignableFrom)
                .collect(Collectors.toSet());

            if (!possibleOutputClasses.isEmpty()) {
                Map<Class<O>, Converter<I, O>> result = new HashMap<>((Map) map);
                result.keySet().retainAll(possibleOutputClasses);
                return result;
            }

        }
        return Collections.emptyMap();
    }

    private <I, O> Class<O> getOutputType(Class<I> inputType) {
        Map<Class<O>, Converter<I, O>> converters = findConverters(inputType, ANY_TYPE);
        switch (converters.size()) {
            case 0:
                return null;
            case 1:
                return converters.keySet().iterator().next();
            default:
                throw new IllegalStateException(
                    String.format("Several opposite types found for %s: %s", inputType, converters.keySet()));
        }
    }

}
