package org.srplib.conversion;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author Q-APE
 */
public class ConverterRegistryTest {

    private ConverterRegistry registry;

    @Before
    public void setUp() throws Exception {
        registry = new ConverterRegistry();
    }

    @Test
    public void testRegisterConverterUntyped() throws Exception {
        Converter converter = Mockito.mock(Converter.class);

        registry.registerConverterUntyped(String.class, Integer.class, converter);

        Assert.assertEquals(converter, registry.getConverter(String.class, Integer.class));
    }

    @Test
    public void testRegisterTwoWayConverter() throws Exception {
        Converter converter = Mockito.mock(TwoWayConverter.class);

        registry.registerConverterUntyped(String.class, Integer.class, converter);

        Assert.assertEquals(converter, registry.getConverter(String.class, Integer.class));

        Converter<Integer, String> backConverter = registry.getConverter(Integer.class, String.class);

        Assert.assertNotNull("Two converters should be registered if TwoWayConverter is passed to ConverterRegistry.",
            backConverter);
    }


    @Test
    public void testRegisterConverterClassOverlapping() throws Exception {
        Converter converter1 = Mockito.mock(Converter.class);
        registry.registerConverterUntyped(String.class, Integer.class, converter1);
        Assert.assertEquals(converter1, registry.getConverter(String.class, Integer.class));

        Converter converter2 = Mockito.mock(Converter.class);
        registry.registerConverterUntyped(String.class, Double.class, converter2);
        Assert.assertEquals(converter2, registry.getConverter(String.class, Double.class));

        Converter converter3 = Mockito.mock(Converter.class);
        registry.registerConverterUntyped(String.class, Boolean.class, converter3);
        Assert.assertEquals(converter3, registry.getConverter(String.class, Boolean.class));
    }

}
