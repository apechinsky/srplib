package org.srplib.support;

import java.io.File;
import java.net.URI;
import java.net.URL;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Returns
 * @author Anton Pechinsky
 */
public class PathUtilsTest {

    @Test
    public void testGetSiblingUri() throws Exception {
        URI uri = new URI("http://www.srplib.org/support/util/Path.java");

        URI sibling = PathUtils.getSibling(uri, "PathUtils.java");

        assertThat(sibling.toString(), is("http://www.srplib.org/support/util/PathUtils.java"));
    }

    @Test
    public void testGetSiblingUrl() throws Exception {
        URL url = new URL("http://www.srplib.org/support/util/Path.java");

        URL sibling = PathUtils.getSibling(url, "PathUtils.java");

        assertThat(sibling.toString(), is("http://www.srplib.org/support/util/PathUtils.java"));
    }

    @Test
    public void testGetSiblingFile() throws Exception {
        File url = new File("/www.srplib.org/support/util/Path.java");

        File sibling = PathUtils.getSibling(url, "PathUtils.java");

        assertThat(sibling.toString(), is("/www.srplib.org/support/util/PathUtils.java"));
    }

    @Test
    public void testGetChildUri() throws Exception {
        URI uri = new URI("http://www.srplib.org/support/util");

        URI child = PathUtils.getChild(uri, "PathUtils.java");

        assertThat(child.toString(), is("http://www.srplib.org/support/util/PathUtils.java"));
    }

    @Test
    public void testGetChildUrl() throws Exception {
        URL url = new URL("http://www.srplib.org/support/util");

        URL child = PathUtils.getChild(url, "PathUtils.java");

        assertThat(child.toString(), is("http://www.srplib.org/support/util/PathUtils.java"));
    }

    @Test
    public void testGetChildFile() throws Exception {
        File url = new File("/www.srplib.org/support/util");

        File sibling = PathUtils.getChild(url, "PathUtils.java");

        assertThat(sibling.toString(), is("/www.srplib.org/support/util/PathUtils.java"));
    }

    @Test
    public void testGetParentUri() throws Exception {
        URI uri = new URI("http://www.srplib.org/support/util/Path.java");

        URI parent = PathUtils.getParent(uri);

        assertThat(parent.toString(), is("http://www.srplib.org/support/util"));
    }

    @Test
    public void testGetParentUrl() throws Exception {
        URL url = new URL("http://www.srplib.org/support/util/Path.java");

        URL parent = PathUtils.getParent(url);

        assertThat(parent.toString(), is("http://www.srplib.org/support/util"));
    }

    @Test
    public void testGetParentFile() throws Exception {
        File url = new File("/www.srplib.org/support/util/Path.java");

        File sibling = PathUtils.getParent(url);

        assertThat(sibling.toString(), is("/www.srplib.org/support/util"));
    }

}
