package org.srplib.conversion;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

/**
 * Test for SwitchConverter class.
 *
 * @author Anton Pechinsky
 */
public class SwitchConverterTest {

    @Test
    public void testKeyExist() throws Exception {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(1, "ONE");
        map.put(2, "TWO");
        map.put(3, "THREE");
        SwitchConverter<Integer, String> converter = new SwitchConverter<Integer, String>(map);

        Assert.assertThat(converter.convert(1), is("ONE"));
        Assert.assertThat(converter.convert(2), is("TWO"));
        Assert.assertThat(converter.convert(3), is("THREE"));
    }

    @Test
    public void testNullValue() throws Exception {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(5, null);
        SwitchConverter<Integer, String> converter = new SwitchConverter<Integer, String>(map);

        Assert.assertThat(converter.convert(5), nullValue());
    }

    @Test
    public void testDefaultValue() throws Exception {
        Map<Integer, String> map = Collections.emptyMap();
        SwitchConverter<Integer, String> converter = new SwitchConverter<Integer, String>(map, "5");

        Assert.assertThat(converter.convert(5), is("5"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoKeyAndNoDefaultValue() throws Exception {
        Map<Integer, String> map = Collections.emptyMap();
        SwitchConverter<Integer, String> converter = new SwitchConverter<Integer, String>(map);

        converter.convert(5);
    }

}
