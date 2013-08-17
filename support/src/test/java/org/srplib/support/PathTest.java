package org.srplib.support;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Anton Pechinsky
 */
public class PathTest {

    @Test
    public void testParseNullProducesEmptyPath() throws Exception {
        Path parse = Path.parse(null);

        assertThat(parse.isEmpty(), is(true));
        assertThat(parse.size(), is(0));
    }

    @Test
    public void testParseEmptyString() throws Exception {
        Path parse = Path.parse("");

        assertThat(parse.size(), is(1));
        assertThat(parse.get(0), is(""));
    }

    @Test
    public void testEmptyPath() throws Exception {
        Path empty = Path.empty();

        assertThat(empty.size(), is(0));
        assertThat(empty.isEmpty(), is(true));
        assertThat(empty.isComplex(), is(false));
    }

    @Test(expected = IllegalStateException.class)
    public void testEmptyPathGetFirst() throws Exception {
        Path.empty().getFirst();
    }

    @Test(expected = IllegalStateException.class)
    public void testEmptyPathGetLast() throws Exception {
        Path.empty().getLast();
    }


    @Test
    public void testParse() throws Exception {
        Path path = Path.parse("1.2.3.4");

        assertThat(path.size(), is(4));
        assertThat(path.isEmpty(), is(false));
        assertThat(path.get(0), is("1"));
        assertThat(path.get(1), is("2"));
        assertThat(path.get(2), is("3"));
        assertThat(path.get(3), is("4"));
    }

    @Test
    public void testGetFirst() throws Exception {
        Path path = Path.parse("1.2.3.4");

        assertThat(path.getFirst(), is("1"));
    }

    @Test
    public void testGetFirstOfEmpty() throws Exception {
        Path path = Path.parse("");

        String first = path.getFirst();

        assertThat(path.size(), is(1));
        assertThat(first, is(""));
    }

    @Test
    public void testLast() throws Exception {
        Path path = Path.parse("1.2.3.4");

        assertThat(path.getLast(), is("4"));
    }

    @Test
    public void testSubpath() throws Exception {
        Path path = Path.parse("1.2.3.4");

        Path subpath = path.subpath();

        assertThat(subpath.toString(), is("2.3.4"));
    }

    public void testSubpathCustom() throws Exception {
        Path path = Path.parse("1.2.3.4");

        assertThat(path.subpath(1, 2).toString(), is("2.3.4"));
        assertThat(path.subpath(1, 1).toString(), is(""));
        assertThat(path.subpath(0, 1).toString(), is("1"));
    }

    @Test
    public void testSubpathOfSimplePath() throws Exception {
        Path path = Path.parse("1");

        Path subpath = path.subpath();

        assertThat(subpath.isEmpty(), is(true));
    }

    @Test
    public void testParent() throws Exception {
        Path path = Path.parse("1.2.3.4");

        Path parent = path.parent();

        assertThat(parent.toString(), is("1.2.3"));
    }

    @Test
    public void testParentOfSimplePath() throws Exception {
        Path path = Path.parse("1");

        Path parent = path.parent();

        assertThat(parent.isEmpty(), is(true));
    }

    @Test
    public void testSibling() throws Exception {
        Path path = Path.parse("1.2");

        Path sibling = path.sibling("5");

        assertThat(sibling.toString(), is("1.5"));
    }

    @Test
    public void testSiblingOfEmpty() throws Exception {
        Path path = Path.parse(".1.2.");

        Path sibling = path.sibling("5");

        assertThat(sibling.toString(), is(".1.5"));
    }

    @Test
    public void testSiblingOfSimplePath() throws Exception {
        Path path = Path.parse("1");

        Path sibling = path.sibling("5");

        assertThat(sibling.toString(), is("5"));
    }

    @Test
    public void testChild() throws Exception {
        Path path = Path.parse("1.2");

        Path child = path.child("3");

        assertThat(child.toString(), is("1.2.3"));
    }

    @Test
    public void testCustomSeparator() throws Exception {
        Path path = Path.parse("1:2:3:4", ":");

        assertThat(path.child("5").toString(), is("1:2:3:4:5"));
        assertThat(path.parent().toString(), is("1:2:3"));
        assertThat(path.sibling("7").toString(), is("1:2:3:7"));
    }

}
