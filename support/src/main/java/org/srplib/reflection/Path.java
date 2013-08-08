package org.srplib.reflection;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.srplib.contract.Argument;
import org.srplib.contract.Assert;

/**
 * Represents property/field path.
 *
 * @author Anton Pechinsky
 */
public class Path {

    private static final String SEPARATOR = ".";

    private List<String> path;

    /**
     * Parses dot separated string to path..
     *
     * @param string String dot separated path.
     * @return Path path
     */
    public static Path parse(String string) {
        Argument.checkNotNull(string, "string path must not be null.");

        String[] path = string.split("\\.");
        return new Path(Arrays.asList(path));
    }

    /**
     * Tests if string path is complex.
     *
     * @param path String path
     * @return true if path contains two or more segments.
     */

    public static boolean isComplex(String path) {
        return path.contains(SEPARATOR);
    }

    /**
     * Creates path from list of path segments.
     *
     * @param path List of path segments
     */
    public Path(List<String> path) {
        Argument.checkNotNull(path, "path");

        this.path = Collections.unmodifiableList(path);
    }

    /**
     * Returns path size.
     *
     * @return number of path segments.
     */
    public int getSize() {
        return path.size();
    }

    /**
     * Tests if path is complex (has two or more segments).
     *
     * @return true if path has two or more segments.
     */
    public boolean isComplex() {
        return getSize() > 1;
    }

    /**
     * Returns subpath of this path.
     *
     * @param start start segment index (zero-based, inclusive)
     * @param end end segment index (zero-based, exclusive)
     * @return Path subpath
     */
    public Path subpath(int start, int end) {
        List<String> newPath = path.subList(start, end);
        return new Path(newPath);
    }

    /**
     * Returns parent path (a path without specified number of last segment).
     *
     * @param end end segment index (zero-based, exclusive)
     * @return Path parent path.
     */
    public Path getParent(int end) {
        return subpath(0, end);
    }

    /**
     * Returns parent path (a path without last segment).
     *
     * <p>For example if source path is 'parent.parent.name' then this method return 'parent.parent'</p>
     *
     * @return Path parent path.
     */
    public Path getParent() {
        return getParent(path.size() - 1);
    }

    /**
     * Returns child path (a path without first segment)
     *
     * <p>For example if source path is 'parent.parent.name' then this method return 'parent.name'</p>
     *
     * @return Path child path
     */
    public Path getChild() {
        return subpath(1, getSize());
    }

    /**
     * Returns specified path segment.
     *
     * @param index segment index (zero-based)
     * @return String path segment
     */
    public String get(int index) {
        Argument.checkFalse(path.isEmpty(), "Can't get item of empty path.");

        return path.get(index);
    }

    /**
     * Returns first path segment.
     *
     * @return String path segment.
     * @throws IllegalStateException if path is empty
     */
    public String getFirst() {
        Argument.checkFalse(path.isEmpty(), "Can't get first item of empty path.");

        return path.get(0);
    }

    /**
     * Returns last path segment.
     *
     * @return String path segment.
     * @throws IllegalStateException if path is empty
     */
    public String getLast() {
        Argument.checkFalse(path.isEmpty(), "Can't get last item of empty path.");

        return get(path.size() - 1);
    }

    public String toString() {
        return join(path, 0, getSize(), SEPARATOR);
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
