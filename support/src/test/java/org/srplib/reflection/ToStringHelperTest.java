package org.srplib.reflection;

import java.io.Serializable;

import org.junit.Assert;
import org.junit.Test;
import static org.hamcrest.core.Is.is;

/**
 * @author Q-APE
 */
public class ToStringHelperTest {

    @Test
    public void testToStringMethodSignature() throws Exception {
        String signature =
            ToStringHelper.toString(getClass(), "test", new Class[] {String.class, Long.class, Serializable.class});

        String expectedSignature =
            "org.srplib.reflection.ToStringHelperTest.test(java.lang.String,java.lang.Long,java.io.Serializable)";

        Assert.assertThat(signature, is(expectedSignature));
    }

    @Test
    public void testJoinEmptyValueList() throws Exception {
        Assert.assertThat(ToStringHelper.join(","), is(""));
    }

    @Test
    public void testJoinSingleValue() throws Exception {
        Assert.assertThat(ToStringHelper.join(",", "1"), is("1"));
    }

    @Test
    public void testJoin() throws Exception {
        Assert.assertThat(ToStringHelper.join(",", "1", "2"), is("1,2"));
    }


}
