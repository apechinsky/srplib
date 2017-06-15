package org.srplib.conversion;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for EmptyConverter
 *
 * @author Anton Pechinsky
 */
public class EmptyConverterTest {

    @Test
    public void testConvert() throws Exception {
        EmptyConverter<? super Object> converter = EmptyConverter.instance();

        Assert.assertEquals("string", converter.convert("string"));
        Assert.assertEquals(3, converter.convert(3));
        Assert.assertEquals(Boolean.TRUE, converter.convert(Boolean.TRUE));
    }

    @Test
    public void testConvertBack() throws Exception {
        EmptyConverter<? super Object> converter = EmptyConverter.instance();

        Assert.assertEquals("string", converter.convertBack("string"));
        Assert.assertEquals(3, converter.convertBack(3));
        Assert.assertEquals(Boolean.TRUE, converter.convertBack(Boolean.TRUE));
    }
}
