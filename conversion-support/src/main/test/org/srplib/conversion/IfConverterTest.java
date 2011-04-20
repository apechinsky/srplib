package org.srplib.conversion;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for IfConverter.
 *
 * @author Anton Pechinsky
 */
public class IfConverterTest {

    @Test
    public void testConvert() throws Exception {
        IfConverter<Boolean> converter = new IfConverter<Boolean>(true, false);

        Assert.assertEquals("Null should be interpreted as false.", false, converter.convert(null));
        Assert.assertEquals("Boolean should be interpreted as is.", true, converter.convert(true));
        Assert.assertEquals(false, converter.convert(false));

        Assert.assertEquals(true, converter.convert("true"));
        Assert.assertEquals(true, converter.convert("TRUE"));
        Assert.assertEquals(false, converter.convert("false"));
        Assert.assertEquals(false, converter.convert("1"));
        Assert.assertEquals(false, converter.convert(1));
        Assert.assertEquals(false, converter.convert(1));
    }
}
