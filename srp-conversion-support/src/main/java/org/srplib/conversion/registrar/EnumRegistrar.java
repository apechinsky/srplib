package org.srplib.conversion.registrar;

import org.srplib.conversion.ConvertBackConverter;
import org.srplib.conversion.ConverterRegistry;
import org.srplib.conversion.EnumConverter;
import org.srplib.conversion.Registrar;
import org.srplib.conversion.TwoWayConverter;

/**
 * Enum-types converter registrar.
 *
 * <p>Allows to simplify enum-types converters registration because requires only source and target
 * types, converter implementation is {@link EnumConverter}</p>
 */
public class EnumRegistrar<I extends Enum, O extends Enum> extends AbstractRegistrar<I, O> {

    private final boolean twoway;

    /**
     * Registrar constructor.
     *
     * @param source source type
     * @param target target type
     * @param twoway flag to register back converter
     */
    public EnumRegistrar(Class<I> source, Class<O> target, boolean twoway) {
        super(source, target, new EnumConverter<>(source, target));
        this.twoway = twoway;
    }

    /**
     * Factory method to create oneway converter.
     *
     * @param source source type
     * @param target target type
     * @param <I> source Enum type parameter
     * @param <O> target Enum type parameter
     * @return registrar
     */
    public static <I extends Enum, O extends Enum> Registrar onewayEnum(Class<I> source, Class<O> target) {
        return new EnumRegistrar<>(source, target, false);
    }

    /**
     * Factory method to create twoway converter
     *
     * @param source source type
     * @param target target type
     * @param <I> source Enum type parameter
     * @param <O> target Enum type parameter
     * @return registrar
     */
    public static <I extends Enum, O extends Enum> Registrar twowayEnum(Class<I> source, Class<O> target) {
        return new EnumRegistrar<>(source, target, true);
    }

    @Override
    public void register(ConverterRegistry registry) {
        registry.add(getInputType(), getOutputType(), getConverter());
        if (twoway) {
            registry.add(getOutputType(), getInputType(), new ConvertBackConverter<>((TwoWayConverter<I, O>) getConverter()));
        }

    }
}
