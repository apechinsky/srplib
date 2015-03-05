package org.srplib.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.srplib.contract.Argument;
import org.srplib.contract.Assert;

/**
 * Represents general purpose path.
 *
 * <ul>Use cases:
 *      <li>file system path manipulation.</li>
 *      <li>classpath manipulation.</li>
 *      <li>URL path manipulation.</li>
 * </ul>
 *
 * <p>
 *     Implementation trims empty last segment. For example "1/2/3/4" and "1/2/3/4/" are parsed to the same path segmens:
 *      [1,2,3,4]
 * </p>
 *
 * <p>Thread safety. Class is immutable, so it's thread safe..</p>
 *
 * @author Anton Pechinsky
 */
public class Path {

    private static final String DEFAULT_SEPARATOR = ".";

    public static final Path EMPTY = new Path(Collections.<String>emptyList());

    private final String separator;

    private final List<String> path;

    /**
     * Returns empty path;
     *
     * @return Path path with no segments.
     */
    public static Path empty() {
        return EMPTY;
    }

    /**
     * Parses dot separated string to path..
     *
     * @param string String dot separated path.
     * @return Path path
     */
    public static Path parse(String string, String separator) {
        Argument.checkNotNull(separator, "separator must not be null.");

        if (string == null) {
            return EMPTY;
        }
        String[] segments = string.split(Pattern.quote(separator));

        return new Path(Arrays.asList(segments), separator);
    }

    /**
     * Parses dot separated string to path..
     *
     * @param string String dot separated path.
     * @return Path path
     */
    public static Path parse(String string) {
        return parse(string, DEFAULT_SEPARATOR);
    }

    /**
     * Tests if string path is complex.
     *
     * TODO: since path supports different separators it make sense to move this method out of here
     *
     * @param path String path
     * @return true if path contains two or more segments.
     */
    public static boolean isComplex(String path) {
        return path.contains(DEFAULT_SEPARATOR);
    }

    /**
     * Creates path from list of path segments.
     *
     * @param path List of path segments
     */
    public Path(List<String> path, String separator) {
        Argument.checkNotNull(path, "Can't create path from null list.");
        Argument.checkNotNull(separator, "Can't create path with null separator.");

        this.path = Collections.unmodifiableList(path);
        this.separator = separator;
    }

    /**
     * Creates path from list of path segments.
     *
     * @param path List of path segments
     */
    public Path(List<String> path) {
        this(path, DEFAULT_SEPARATOR);
    }

    /**
     * Creates new Path containing the same path segments but with specified separator.
     *
     * @param separator String new path separator.
     * @return Path new path
     */
    public Path newWithSeparator(String separator) {
        // path is unmodifiable, so it's safe to pass it to new Path
        return new Path(path, separator);
    }

    /**
     * Returns path size.
     *
     * @return number of path segments.
     */
    public int size() {
        return path.size();
    }

    /**
     * Tests if path is empty (size == 0)
     *
     * @return true if no segments in path
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Tests if path is complex (has two or more segments).
     *
     * @return true if path has two or more segments.
     */
    public boolean isComplex() {
        return size() > 1;
    }

    /**
     * Returns subpath of this path.
     *
     * @param start start segment index (zero-based, inclusive)
     * @param end end segment index (zero-based, exclusive)
     * @return Path subpath
     * @throws IndexOutOfBoundsException for an illegal endpoint index value
     *         (<tt>fromIndex &lt; 0 || toIndex &gt; size ||
     *         fromIndex &gt; toIndex</tt>)
     */
    public Path subpath(int start, int end) {
        Argument.checkTrue(start >= 0, "Start index should be >= 0. Got: %d", start);
        Argument.checkTrue(end <= path.size(), "End index should be <= than path size. Got: %d", end);
        Argument.checkTrue(start <= end, "Start index should be <= than end index. Got start: %d end: %d", start, end);

        List<String> newPath = path.subList(start, end);
        return new Path(newPath, separator);
    }

    /**
     * Returns subpath (a path without first segment)
     *
     * <p>For example if source path is 'grand.parent.name' then this method return 'parent.name'</p>
     *
     * <p>Call to this method equivalent to:
     *  <pre>
     *      subpath(1, size());
     *  </pre>
     * </p>
     *
     * @return Path child path
     */
    public Path subpath() {
        return subpath(1, size());
    }

    /**
     * Returns parent path (a path without last segment).
     *
     * <p>For example if source path is 'parent.parent.name' then this method return 'parent.parent'</p>
     *
     * @return Path parent path.
     */
    public Path parent() {
        return subpath(0, path.size() - 1);
    }

    /**
     * Returns child path.
     *
     * <p>For example if source path is 'grand.parent' then following code return 'grand.parent.name':
     *  <pre>
     *      sibling('name')
     *  </pre>
     * </p>
     *
     * @return Path child path
     */
    public Path child(String segment) {
        ArrayList<String> segments = new ArrayList<String>(path);
        segments.add(segment);

        return new Path(segments, separator);
    }

    /**
     * Returns sibling path (a path to segment on the same level as current)
     *
     * <p>For example if source path is 'grand.parent.name' then following code return 'grand.parent.surname':
     *  <pre>
     *      sibling('surname')
     *  </pre>
     * </p>
     *
     * @return Path child path
     */
    public Path sibling(String segment) {
        return parent().child(segment);
    }

    /**
     * Returns specified path segment.
     *
     * @param index segment index (zero-based)
     * @return String path segment
     */
    public String get(int index) {
        Assert.checkFalse(path.isEmpty(), "Can't get item of empty path.");

        return path.get(index);
    }

    /**
     * Returns first path segment.
     *
     * @return String path segment.
     * @throws IllegalStateException if path is empty
     */
    public String getFirst() {
        Assert.checkFalse(path.isEmpty(), "Can't get first item of empty path.");

        return path.get(0);
    }

    /**
     * Returns last path segment.
     *
     * @return String path segment.
     * @throws IllegalStateException if path is empty
     */
    public String getLast() {
        Assert.checkFalse(path.isEmpty(), "Can't get last item of empty path.");

        return get(path.size() - 1);
    }

    public String toString() {
        return join(path, 0, size(), separator);
    }

    private String join(List<String> path, int startIndex, int endIndex, String separator) {
        StringBuffer sb = new StringBuffer();
        for (int i = startIndex; i < endIndex; i++) {
            sb.append(path.get(i));
            if (i < endIndex - 1) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }
}
