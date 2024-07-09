package org.srplib.conversion;

/**
 * Converter registrar.
 *
 * <p>
 *     Incapsulates converter registration parameters and registration logic.
 *     Allows to add different converter registration ways specific for application without need to add additional methods in
 *     {@link ConverterRegistry} interface.
 * </p>
 *
 * <p>
 *     Implementations must call {@link ConverterRegistry#add(Class, Class, Converter)} to register converter.
 * </p>
 *
 * <p>
 *     One implementation can register several converters.
 * </p>
 */
public interface Registrar {

    /**
     * Calls by convertation infrastructure during configuration converters registry.
     *
     * @param registry ConverterRegistry converters registry.
     */
    void register(ConverterRegistry registry);

}

