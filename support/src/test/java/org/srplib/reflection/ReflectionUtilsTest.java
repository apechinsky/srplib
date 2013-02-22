package org.srplib.reflection;

import java.io.Serializable;

import org.junit.Assert;
import org.junit.Test;
import org.srplib.reflection.ReflectionUtils;
import static org.hamcrest.core.Is.is;

/**
 * Test for ReflectionUtils
 *
 * @author Anton Pechinsky
 */
public class ReflectionUtilsTest {

    private String test;

    private void test(String string, Long l, Serializable serializable) {
        // this method is used by reflection test methods. Do not delete!
    }

    @Test
    public void testToStringMethodSignature() throws Exception {
        String signature =
            ReflectionUtils.toString(getClass(), "test", new Class[] {String.class, Long.class, Serializable.class});

        String expectedSignature =
            "org.srplib.reflection.ReflectionUtilsTest.test(java.lang.String,java.lang.Long,java.io.Serializable)";

        Assert.assertThat(signature, is(expectedSignature));
    }

    @Test
    public void testJoinEmptyValueList() throws Exception {
        Assert.assertThat(ReflectionUtils.join(","), is(""));
    }

    @Test
    public void testJoinSingleValue() throws Exception {
        Assert.assertThat(ReflectionUtils.join(",", "1"), is("1"));
    }

    @Test
    public void testJoin() throws Exception {
        Assert.assertThat(ReflectionUtils.join(",", "1", "2"), is("1,2"));
    }
}
