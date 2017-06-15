package org.srplib.conversion;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author Anton Pechinsky
 */
public class DelegateTwoWayConverterTest {

    private DelegateTwoWayConverter<String, Integer> twoWayConverter;

    private Converter<String, Integer> converter;

    private Converter<Integer, String> reverseConverter;

    @Before
    public void setUp() throws Exception {
        converter = Mockito.mock(Converter.class);
        reverseConverter = Mockito.mock(Converter.class);
        twoWayConverter = new DelegateTwoWayConverter<String, Integer>(converter, reverseConverter);
    }

    @Test
    public void testConvert() throws Exception {
        Mockito.when(converter.convert("s1")).thenReturn(1);
        Mockito.when(converter.convert("s2")).thenReturn(2);
        Mockito.when(converter.convert("s3")).thenReturn(3);

        Assert.assertEquals(Integer.valueOf(1), twoWayConverter.convert("s1"));
        Assert.assertEquals(Integer.valueOf(2), twoWayConverter.convert("s2"));
        Assert.assertEquals(Integer.valueOf(3), twoWayConverter.convert("s3"));
    }

    @Test
    public void testConvertBack() throws Exception {
        Mockito.when(reverseConverter.convert(1)).thenReturn("s1");
        Mockito.when(reverseConverter.convert(2)).thenReturn("s2");
        Mockito.when(reverseConverter.convert(3)).thenReturn("s3");

        Assert.assertEquals("s1", twoWayConverter.convertBack(1));
        Assert.assertEquals("s2", twoWayConverter.convertBack(2));
        Assert.assertEquals("s3", twoWayConverter.convertBack(3));
    }
}
