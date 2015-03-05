package org.srplib.support;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.srplib.contract.Argument;

/**
 * Implementation of semantic versioning (<a href='http://semver.org'>http://semver.org</a>)
 *
 * <p>String representation has the following format:
 *  <pre>
 *      major.minor.patch[.qualifier]
 *  </pre>
 * </p>
 *
 * @author Q-APE
 */
public class Version implements Comparable<Version> {

    public static final Pattern VERSION_PATTERN = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)(.(\\w+))*");

    /**
     * Zero version (0.0.0)
     */
    public static final Version ZERO = valueOf("0.0.0");

    /**
     * Initial version (0.1.0)
     */
    public static final Version INITIAL = valueOf("0.1.0");


    private int major;

    private int minor;

    private int patch;

    private String qualifier;

    /**
     * Parses string and creates {@link Version} object
     *
     * @param string String string representation of semantic version
     * @return Version
     */
    public static Version valueOf(String string) {
        Argument.checkNotNull(string, "Version string must not be null!");

        Matcher matcher = VERSION_PATTERN.matcher(string);

        Argument.checkTrue(matcher.matches(),
            "Version string '%s' doesn't conform semantic version format 'major.minor.patch[.qualifier]'.", string);

        int major = Integer.parseInt(matcher.group(1));
        int minor = Integer.parseInt(matcher.group(2));
        int patch = Integer.parseInt(matcher.group(3));
        String qualifier = matcher.group(5);

        return new Version(major, minor, patch, qualifier);
    }

    /**
     * Creates version.
     *
     * @param major int major part
     * @param minor int minor part
     * @param patch int patch part
     * @param qualifier String optional qualifier
     */
    public Version(int major, int minor, int patch, String qualifier) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.qualifier = qualifier;
    }

    /**
     * Returns major version part.
     *
     * @return a number
     */
    public int getMajor() {
        return major;
    }

    /**
     * Returns minor version part.
     *
     * @return a number
     */
    public int getMinor() {
        return minor;
    }

    /**
     * Returns patch version part.
     *
     * @return a number
     */
    public int getPatch() {
        return patch;
    }

    /**
     * Returns version qualifier
     *
     * @return a string
     */
    public String getQualifier() {
        return qualifier;
    }

    /**
     * Creates new version by incrementing major part of this version.
     *
     * @param clearQualifier if true method clears qualifier (2.1.0.RELEASE -> 3.1.0)
     * @return Version
     */
    public Version nextMajor(boolean clearQualifier) {
        return new Version(major + 1, minor, patch, getQualifierOrEmpty(clearQualifier));
    }

    /**
     * Creates new version by incrementing major part of this version.
     *
     * <p>Qualifier is preserved.</p>
     * @return Version
     */
    public Version nextMajor() {
        return nextMajor(true);
    }

    /**
     * Creates new version by incrementing minor part of this version.
     *
     * @param clearQualifier if true method clears qualifier (2.1.0.RELEASE -> 2.2.0)
     * @return Version
     */
    public Version nextMinor(boolean clearQualifier) {
        return new Version(major, minor + 1, patch, getQualifierOrEmpty(clearQualifier));
    }

    /**
     * Creates new version by incrementing minor part of this version.
     *
     * <p>Qualifier is preserved.</p>
     * @return Version
     */
    public Version nextMinor() {
        return nextMinor(true);
    }

    /**
     * Creates new version by incrementing patch part of this version.
     *
     * @param clearQualifier if true method clears qualifier (2.1.0.RELEASE -> 2.1.1)
     * @return Version
     */
    public Version nextPatch(boolean clearQualifier) {
        return new Version(major, minor , patch + 1, getQualifierOrEmpty(clearQualifier));
    }

    /**
     * Creates new version by incrementing patch part of this version.
     *
     * <p>Qualifier is preserved.</p>
     * @return Version
     */
    public Version nextPatch() {
        return nextPatch(true);
    }

    /**
     * Returns string representation of version.
     *
     * @return String
     */
    public String format() {
        String qualifierString = qualifier == null ? "" : "." + qualifier;
        return String.format("%d.%d.%d%s", major, minor, patch, qualifierString);
    }

    private String getQualifierOrEmpty(boolean clearQualifier) {
        return clearQualifier ? null : qualifier;
    }

    @Override
    public String toString() {
        return "SemanticVersion{" +
            "major=" + major +
            ", minor=" + minor +
            ", patch='" + patch + '\'' +
            '}';
    }

    @Override
    public int compareTo(Version that) {
        int majorCompare = compareTo(this.major, that.major);

        if (majorCompare != 0) {
            return majorCompare;
        }

        int minorCompare = compareTo(this.minor, that.minor);

        if (minorCompare != 0) {
            return minorCompare;
        }

        int patchCompare = compareTo(this.patch, that.patch);

        if (patchCompare != 0) {
            return patchCompare;
        }

        if (this.qualifier == null)  {
            return that.qualifier == null ? 0 : -1;
        }

        return that.qualifier == null ? 1 : this.qualifier.compareTo(that.qualifier);
    }

    private int compareTo(int value1, int value2) {
        return value1 > value2 ? 1 : (value2 > value1 ? -1 : 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Version version = (Version) o;

        if (major != version.major) {
            return false;
        }
        if (minor != version.minor) {
            return false;
        }
        if (patch != version.patch) {
            return false;
        }
        if (qualifier != null ? !qualifier.equals(version.qualifier) : version.qualifier != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = major;
        result = 31 * result + minor;
        result = 31 * result + patch;
        result = 31 * result + (qualifier != null ? qualifier.hashCode() : 0);
        return result;
    }
}
