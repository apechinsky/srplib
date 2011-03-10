package net.srplib.conversion;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
        Converter converter1 = EasyMock.createMock(Converter.class);
        registry.registerConverterUntyped(String.class, Integer.class, converter1);
        Assert.assertEquals(converter1, registry.getConverter(String.class, Integer.class));
    }

    @Test
    public void testRegisterConverterClassOverlapping() throws Exception {
        Converter converter1 = EasyMock.createMock(Converter.class);
        registry.registerConverterUntyped(String.class, Integer.class, converter1);
        Assert.assertEquals(converter1, registry.getConverter(String.class, Integer.class));

        Converter converter2 = EasyMock.createMock(Converter.class);
        registry.registerConverterUntyped(String.class, Double.class, converter2);
        Assert.assertEquals(converter2, registry.getConverter(String.class, Double.class));

        Converter converter3 = EasyMock.createMock(Converter.class);
        registry.registerConverterUntyped(String.class, Boolean.class, converter3);
        Assert.assertEquals(converter3, registry.getConverter(String.class, Boolean.class));
    }

}
