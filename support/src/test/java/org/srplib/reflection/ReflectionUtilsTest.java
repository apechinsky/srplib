package org.srplib.reflection;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
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

    @Test
    public void testgetDeclaredFieldsRecursively() {
        List<Field> fields = ReflectionUtils.getDeclaredFieldsRecursively(Child.class);

        Assert.assertThat(asSet(fields), is(asSet(
            field(Parent.class, "field"),
            field(Parent.class, "parentField"),
            field(Child.class, "field"),
            field(Child.class, "childField")
        )));
    }

    private <T> Set<T> asSet(List<T> fields) {
        return new HashSet<T>(fields);
    }

    private <T> Set<T> asSet(T... fields) {
        return asSet(Arrays.asList(fields));
    }

    private Field field(Class type, String field) {
        return ReflectionUtils.getDeclaredField(type, field);
    }


    private static class Parent {

        private String field;

        private String parentField;

    }

    private static class Child extends Parent {

        private String field;

        private String childField;

    }

}


