package org.srplib.support;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class VersionTest {

    @Test
    public void testValueOfNormal() {
        assertThat(Version.valueOf("1.2.3"), is(version(1, 2, 3)));
    }

    @Test
    public void testValueOfLong() {
        assertThat(Version.valueOf("44.123.345"), is(version(44, 123, 345)));
    }

    @Test
    public void testValueOfQualifier() {
        assertThat(Version.valueOf("1.2.3.RELEASE1").format(), is("1.2.3.RELEASE1"));
    }

    @Test
    public void testNextMajor() {
        assertThat(version("1.2.3").nextMajor().format(), is("2.2.3"));
    }

    @Test
    public void testNextMinor() {
        assertThat(version("1.2.3").nextMinor().format(), is("1.3.3"));
    }

    @Test
    public void testNextPatch() {
        assertThat(version("1.2.3").nextPatch().format(), is("1.2.4"));
    }

    @Test
    public void testCompareToMajor() {
        assertThat(version("10.2.3").compareTo(version("2.2.3")), is(1));
    }

    @Test
    public void testCompareToMinor() {
        assertThat(version("2.2.3").compareTo(version("1.20.0")), is(1));
    }

    @Test
    public void testCompareToPatch() {
        assertThat(version("1.2.3").compareTo(version("1.2.2")), is(1));
    }

    private Version version(String string) {
        return Version.valueOf(string);
    }

    private Version version(int major, int minor, int patch) {
        return new Version(major, minor, patch, null);
    }
}