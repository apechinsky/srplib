package org.srplib.support;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
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

        assertThat(parse.getSize(), is(1));
        assertThat(parse.get(0), is(""));
    }

    @Test
    public void testParse() throws Exception {
        assertThat(path.getSize(), is(4));
        assertThat(path.get(0), is("1"));
        assertThat(path.get(1), is("2"));
        assertThat(path.get(2), is("3"));
        assertThat(path.get(3), is("4"));
    }

    @Test
    public void testFirst() throws Exception {
        assertThat(path.getFirst(), is("1"));
    }

    @Test
    public void testLast() throws Exception {
        assertThat(path.getLast(), is("4"));
    }

    @Test
    public void testParent() throws Exception {
        Path parent = path.getParent();

        assertThat(parent.toString(), is("1.2.3"));
    }

    @Test
    public void testParentOfSimplePath() throws Exception {
        Path path = Path.parse("1");

        Path parent = path.getParent();

        assertThat(parent.isEmpty(), is(true));
    }

    @Test
    public void testChild() throws Exception {
        Path child = path.getChild();

        assertThat(child.toString(), is("2.3.4"));
    }

    @Test
    public void testChildOfSimplePath() throws Exception {
        Path path = Path.parse("1");

        Path child = path.getChild();

        assertThat(child.isEmpty(), is(true));
    }
}
