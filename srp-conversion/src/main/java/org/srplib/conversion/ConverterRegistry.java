package org.srplib.conversion;

/**
 * Converter registry.
 *
 * <p>
 *     Responsible for
 *     <ul>
 *         <li>converters registration</li>
 *         <li>converters lookup</li>
 *     </ul>
 * </p>
 */
public interface ConverterRegistry {

    Class ANY_TYPE = Object.class;

    /**
     * Searches for converter for provided types pair.
     * <p>
     *     Implementations are responsible for superclasses handling strategy e.g. search or not superclasses converter if
     *     no converter found for provided classes.
     * </p>
     *
     * @param source Class source type.
     * @param target Class target type.
     * @return Converter converter for provided types or {@code null} if converter not found
     */
    <I, O> Converter<I, O> find(Class<I> source, Class<O> target);

    /**
     * Registers converter for provided types pair.
     *
     * @param source Class source type
     * @param target Class target type
     * @param converter Converter converter
     */
    <I, O> void add(Class<I> source, Class<O> target, Converter<I, O> converter);

    /**
     * Converter registration variation. Types, converter and convertation logic incapsulated in {@link Registrar}.
     *
     * @param registrar Registrar converter registrator
     * @see Registrar
     */
    default void add(Registrar registrar) {
        registrar.register(this);
    }

}
