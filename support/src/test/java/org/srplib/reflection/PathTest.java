package org.srplib.reflection;

import org.junit.Assert;
import org.junit.Test;
import static org.hamcrest.Matchers.is;

/**
 *
 * @author Q-APE
 */
public class PathTest {

    private Path path;

    public PathTest() {
        path = Path.parse("1.2.3.4");
    }


    @Test(expected = IllegalArgumentException.class)
    public void testParseNull() throws Exception {
        Path.parse(null);
    }

    @Test
    public void testParseEmptyString() throws Exception {
        Path parse = Path.parse("");

        Assert.assertThat(parse.getSize(), is(1));
        Assert.assertThat(parse.get(0), is(""));
    }

    @Test
    public void testParse() throws Exception {
        Assert.assertThat(path.getSize(), is(4));
        Assert.assertThat(path.get(0), is("1"));
        Assert.assertThat(path.get(1), is("2"));
        Assert.assertThat(path.get(2), is("3"));
        Assert.assertThat(path.get(3), is("4"));
    }

    @Test
    public void testFirst() throws Exception {
        Assert.assertThat(path.getFirst(), is("1"));
    }

    @Test
    public void testLast() throws Exception {
        Assert.assertThat(path.getLast(), is("4"));
    }
}
