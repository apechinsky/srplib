package org.srplib.conversion;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for SwitchConverter class.
 *
 * @author Anton Pechinsky
 */
public class SwitchConverterTest {

    @Test
    public void testConvert() throws Exception {
        SwitchConverter<Integer, String> converter = createTestConverter(false);

        Assert.assertEquals("ONE", converter.convert(1));
        Assert.assertEquals("TWO", converter.convert(2));
        Assert.assertEquals("THREE", converter.convert(3));
    }

    @Test
    public void testConvertUnmapped() throws Exception {
        SwitchConverter<Integer, String> converter = createTestConverter(false);
        Assert.assertEquals(null, converter.convert(5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertStrictUnmapped() throws Exception {
        SwitchConverter<Integer, String> converter = createTestConverter(true);

        // unmapped value should lead to IllegalArgumentException
        converter.convert(5);
    }

    private SwitchConverter<Integer, String> createTestConverter(boolean strict) {
        Map<Integer, String> numbers = new HashMap<Integer, String>();
        numbers.put(1, "ONE");
        numbers.put(2, "TWO");
        numbers.put(3, "THREE");

        return new SwitchConverter<Integer, String>(numbers, strict);
    }

}
