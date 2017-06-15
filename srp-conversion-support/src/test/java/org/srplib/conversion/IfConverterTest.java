package org.srplib.conversion;

import org.junit.Assert;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;

/**
 * Test for IfConverter.
 *
 * @author Anton Pechinsky
 */
public class IfConverterTest {

    private IfConverter<Object,Boolean> converter = new IfConverter<Object, Boolean>(true, false);

    @Test
    public void testTrueResolvesToTrue() throws Exception {
        Assert.assertThat(converter.convert(true), is(true));
    }

    @Test
    public void testFalseResolvesToFalse() throws Exception {
        Assert.assertThat(converter.convert(false), is(false));
    }

    @Test
    public void testNullResolvesToFalse() throws Exception {
        Assert.assertThat(converter.convert(null), is(false));
    }

    @Test
    public void testTrueStringResolvesToTrue() throws Exception {
        Assert.assertThat("Invalid string 'true' conversion.", converter.convert("true"), is(true));
        Assert.assertThat("Invalid string 'TRUE' conversion.", converter.convert("TRUE"), is(true));
    }

    @Test
    public void testFalseStringResolvesToFalse() throws Exception {
        Assert.assertThat(converter.convert("false"), is(false));
    }

    @Test
    public void testIntegerResolvesToFalse() throws Exception {
        Assert.assertThat(converter.convert("1"), is(false));
        Assert.assertThat(converter.convert(1), is(false));
    }
}
