package org.srplib.support;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.srplib.contract.Argument;

/**
 * Contains static utility method for URI manipulation.
 *
 * <ul>Key features of this utilities:
 *  <li>Create sibling of specified URI/URL</li>
 *  <li>Create child of specified URI/URL</li>
 *  <li>Create parent of specified URI/URL</li>
 *  <li>Create relatives of specified URI/URL</li>
 * </ul>
 *
 * @author Anton Pechinsky
 */
public class PathUtils {

    public static final String PATH_SEPARATOR = "/";

    /**
     * Returns relative URI for specified URI.
     *
     * <p>Method leverages {@link URI#resolve(String)} method and supports back directory references.</p>
     *
     * @param uri URI base uri
     * @param relativePath String relative path
     * @return relative URI
     */
    public static URI getRelative(URI uri, String relativePath) {
        return uri.resolve(relativePath);
    }

    /**
     * Returns relative URL for specified URL.
     *
     * <p>Method leverages {@link URI#resolve(String)} method and supports back directory references.</p>
     *
     * @param url URL base uri
     * @param relativePath String relative path
     * @return relative URL
     */
    public static URL getRelative(URL url, String relativePath) {
        URI resolved = toURI(url).resolve(relativePath);
        return toURL(resolved);
    }

    /**
     * Returns sibling of specified URI.
     *
     * @param uri URI source uri
     * @param segment String sibling name
     * @return URI sibling
     */
    public static URI getSibling(URI uri, String segment) {
        Argument.checkNotNull(uri, "uri must not be null!");

        String sibling = getSibling(uri.getPath(), segment);
        return setPath(uri, sibling);
    }

    /**
     * Returns sibling of specified URL.
     *
     * @param url URL source url
     * @param segment String sibling name
     * @return URL sibling
     */
    public static URL getSibling(URL url, String segment) {
        Argument.checkNotNull(url, "url must not be null!");

        return toURL(getSibling(toURI(url), segment));
    }

    /**
     * Returns sibling of specified File.
     *
     * @param file File source file
     * @param segment String sibling name
     * @return File sibling
     */
    public static File getSibling(File file, String segment) {
        Argument.checkNotNull(file, "file must not be null!");

        String path = file.getPath();

        return new File(getSibling(path, segment));
    }

    /**
     * Returns child of specified URI.
     *
     * @param uri URI source url
     * @param segment String child path segment name
     * @return URL child
     */
    public static URI getChild(URI uri, String segment) {
        Argument.checkNotNull(uri, "uri must not be null!");

        String sibling = getChild(uri.getPath(), segment);
        return setPath(uri, sibling);
    }

    /**
     * Returns child of specified URL.
     *
     * @param url URL source url
     * @param segment String child path segment name
     * @return URL child
     */
    public static URL getChild(URL url, String segment) {
        Argument.checkNotNull(url, "url must not be null!");

        return toURL(getChild(toURI(url), segment));
    }

    /**
     * Returns child of specified file.
     *
     * @param file File source file
     * @param segment String child path segment name
     * @return File child
     */
    public static File getChild(File file, String segment) {
        Argument.checkNotNull(file, "file must not be null!");

        String path = file.getPath();

        return new File(getChild(path, segment));
    }

    /**
     * Returns parent of specified URI.
     *
     * @param uri URI source url
     * @return URL parent
     */
    public static URI getParent(URI uri) {
        Argument.checkNotNull(uri, "uri must not be null!");

        String sibling = getParent(uri.getPath());
        return setPath(uri, sibling);
    }

    /**
     * Returns parent of specified URL.
     *
     * @param url URL source url
     * @return URL parent
     */
    public static URL getParent(URL url) {
        Argument.checkNotNull(url, "url must not be null!");

        return toURL(getParent(toURI(url)));
    }

    /**
     * Returns parent of specified file.
     *
     * @param file File source file
     * @return File parent
     */
    public static File getParent(File file) {
        Argument.checkNotNull(file, "file must not be null!");

        String path = file.getPath();

        return new File(getParent(path));
    }


    /**
     * Returns sibling of specified path string.
     *
     * @param path String forward slash separated string
     * @param segment String sibling name
     * @return String sibling path string.
     */
    public static String getSibling(String path, String segment) {
        return Path.parse(path, PATH_SEPARATOR).sibling(segment).toString();
    }

    /**
     * Returns child of specified path string.
     *
     * @param path String forward slash separated string
     * @param segment String child name
     * @return String child path string.
     */
    public static String getChild(String path, String segment) {
        return Path.parse(path, PATH_SEPARATOR).child(segment).toString();
    }

    /**
     * Returns parent of specified path string.
     *
     * @param path String forward slash separated string
     * @return String parent path string.
     */
    public static String getParent(String path) {
        return Path.parse(path, PATH_SEPARATOR).parent().toString();
    }


    /**
     * Creates new URI from specified uri and replaces path with specified value.
     *
     * @param uri URI base uri
     * @param path String new path.
     * @return URI uri
     */
    public static URI setPath(URI uri, String path) {
        Argument.checkNotNull(uri, "uri must not be null!");

        try {
            return new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), path, uri.getQuery(), uri.getFragment());
        }
        catch (URISyntaxException e) {
            throw new IllegalArgumentException(String.format("Can't replace path in URI '%s' to '%s'.", uri, path));
        }
    }

    /**
     * Creates new URL from specified uri and replaces path with specified value.
     *
     * @param url URL base uri
     * @param path String new path.
     * @return URL url
     */
    public static URL setPath(URL url, String path) {
        Argument.checkNotNull(url, "url must not be null!");

        try {
            URI uri = toURI(url);
            return setPath(uri, path).toURL();
        }
        catch (MalformedURLException e) {
            throw new IllegalArgumentException(String.format("Can't replace path in URL '%s' to '%s'.", url, path));
        }
    }

    /**
     * Converts URL to URI and convert checked exceptions to unchecked ones.
     *
     * @param url URL source url
     * @return URI uri
     * @throws IllegalArgumentException with wrapped URISyntaxException
     */
    public static URI toURI(URL url) {
        Argument.checkNotNull(url, "url must not be null!");
        try {
            return url.toURI();
        }
        catch (URISyntaxException e) {
            throw new IllegalArgumentException(String.format("Can't convert URL '%s' to URI", url), e);
        }
    }

    /**
     * Converts URI to URL and convert checked exceptions to unchecked ones.
     *
     * @param uri URI source uri
     * @return URL url
     * @throws IllegalArgumentException with wrapped MalformedURLException
     */
    public static URL toURL(URI uri) {
        Argument.checkNotNull(uri, "uri must not be null!");
        try {
            return uri.toURL();
        }
        catch (MalformedURLException e) {
            throw new IllegalArgumentException(String.format("Can't convert URI '%s' to URL", uri), e);
        }
    }

}
