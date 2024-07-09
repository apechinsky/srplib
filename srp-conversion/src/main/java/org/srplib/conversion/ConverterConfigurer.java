package org.srplib.conversion;

/**
 * Converter system configurer.
 *
 * <p>Implementation is hosting for converters registration.</p>
 * <pre>
 *     class MyConfigurer implements ConverterConfigurer {
 *
 *         public void configure(ConverterRegistry registry, ConversionService converter) {
 *
 *             registry.add(ClassA.class, ClassB.class, new ABConverter());
 *
 *             registry.add(oneway(ClassA.class, ClassB.class, new ABConverter()));
 *
 *             registry.add(twoway(ClassA.class, ClassB.class, new ABConverter()));
 *
 *             registry.add(twowayEnum(ClassA.class, ClassB.class, new ABConverter()));
 *
 *             registry.add(onewayEnum(ClassA.class, ClassB.class, new ABConverter()));
 *
 *             registry.add(new CustomRegistrar(ClassA.class, ClassB.class, new ABConverter()));
 *
 *             ...
 *         }
 *     }
 *
 *     ConversionService service = new ConversionServiceImpl(new MyConfigurer());
 * </pre>
 *
 * <p>Called by conversion service during converters registration phase.</p>
 */
public interface ConverterConfigurer {

    /**
     * Method called for converter registration.
     *
     * @param registry ConverterRegistry converters registry
     * @param converter ConversionService conversion service, can be used by registering converters
     * for call another converters
     */
    void configure(ConverterRegistry registry, ConversionService converter);

}

