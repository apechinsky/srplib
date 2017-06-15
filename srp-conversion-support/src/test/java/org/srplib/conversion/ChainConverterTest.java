package org.srplib.conversion;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

/**
 * Test for ChainConverter.
 *
 * @author Anton Pechinsky
 */
public class ChainConverterTest {

    @Test
    public void testConvert() throws Exception {
        Converter<String, Integer> converter1 = Mockito.mock(Converter.class);
        Converter<Integer, Boolean> converter2 = Mockito.mock(Converter.class);
        Converter<Boolean, Byte> converter3 = Mockito.mock(Converter.class);

        // prepare conversion: "3" -> 3 > true -> 1
        Mockito.when(converter1.convert("3")).thenReturn(3);
        Mockito.when(converter2.convert(3)).thenReturn(true);
        Mockito.when(converter3.convert(true)).thenReturn((byte)1);

        ChainConverter<String, Byte> converter = new ChainConverter<String, Byte>(converter1, converter2, converter3);

        // chain converter should return 1
        Assert.assertEquals(Byte.valueOf((byte)1), converter.convert("3"));

        // verify that each converter called
        verify(converter1).convert("3");
        verify(converter2).convert(3);
        verify(converter3).convert(true);
    }
}
