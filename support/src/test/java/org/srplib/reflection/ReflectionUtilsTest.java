package org.srplib.reflection;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import org.hamcrest.CoreMatchers;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

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
    public void testGetTypeParameter() {
        Object typed = new TypeToken<String>() {};

        Class parameterType = ReflectionUtils.getTypeParameter(typed.getClass());

        Assert.assertThat(parameterType, new IsEqual<Class>(String.class));
    }

    @Test
    public void testGetTypeParameterNestedParameter() {
        Object typed = new TypeToken<List<String>>() {};

        Class parameterType = ReflectionUtils.getTypeParameter(typed.getClass());

        Assert.assertThat(parameterType, new IsEqual<Class>(List.class));
    }

    @Test
    public void testGetTypeParameterNoParameter() {
        Object typed = new TypeToken() {};

        Class parameterType = ReflectionUtils.getTypeParameter(typed.getClass());

        Assert.assertThat(parameterType, nullValue());
    }

    class TypeToken<T> {
    }

    @Test
    public void testGetMethod() {
        Method method = ReflectionUtils.getMethod(Child.class, "childMethod");
        Assert.assertThat(method, is(method(Child.class, "childMethod")));
    }

    @Test(expected = ReflectionException.class)
    public void testGetMethodNonExisting() {
        ReflectionUtils.getMethod(Child.class, "nonExistingMethod");
    }

    @Test
    public void testFindMethod() {
        Method method = ReflectionUtils.findMethod(Child.class, "childMethod");
        Assert.assertThat(method, is(method(Child.class, "childMethod")));
    }

    @Test
    public void testFindMethodNonExisting() {
        Method method = ReflectionUtils.findMethod(Child.class, "nonExistingMethod");
        Assert.assertThat(method, nullValue());
    }

    @Test
    public void testFindMethodRecursively() {
        Method method = ReflectionUtils.findMethodRecursively(Child.class, "parentMethod");
        Assert.assertThat(method, is(method(Parent.class, "parentMethod")));
    }

    @Test
    public void testGetMethodNonExistinRecursively() {
        Method method = ReflectionUtils.findMethodRecursively(Child.class, "parentMethod");
        Assert.assertThat(method, is(method(Parent.class, "parentMethod")));
    }

    @Test
    public void testFindMethodNonExistinRecursively() {
        Method method = ReflectionUtils.findMethodRecursively(Child.class, "nonExistingMethod");
        Assert.assertThat(method, nullValue());
    }

    @Test(expected = ReflectionException.class)
    public void testGetMethodRecursively() {
        ReflectionUtils.getMethodRecursively(Child.class, "nonExistingMethod");
    }

    @Test
    public void testGetField() {
        Field field = ReflectionUtils.getField(Child.class, "field");
        Assert.assertThat(field, is(field(Child.class, "field")));
    }

    @Test(expected = ReflectionException.class)
    public void testGetFieldNonExisting() {
        ReflectionUtils.getField(Child.class, "nonExistingfield");
    }

    @Test
    public void testFindField() {
        Field field = ReflectionUtils.findField(Child.class, "field");
        Assert.assertThat(field, is(field(Child.class, "field")));
    }

    @Test
    public void testFindFieldNonExisting() {
        Field field = ReflectionUtils.findField(Child.class, "nonExistingfield");
        Assert.assertThat(field, nullValue());
    }

    @Test
    public void testGetFieldRecursively() {
        Field field = ReflectionUtils.getFieldRecursively(Child.class, "parentField");
        Assert.assertThat(field, is(field(Parent.class, "parentField")));
    }

    @Test
    public void testGetFieldsRecursively() {
        List<Field> fields = ReflectionUtils.getFieldsRecursively(Child.class);

        Assert.assertThat(asSet(fields), is(asSet(
            field(Parent.class, "field"),
            field(Parent.class, "parentField"),
            field(Child.class, "field"),
            field(Child.class, "childField")
        )));
    }

    @Test
    public void testSetFieldValue() {
        Child child = new Child();

        ReflectionUtils.setFieldValue(child, "childField", "value");

        Assert.assertThat(child.childField, is("value"));
    }

    @Test
    public void testSetFieldValueViaField() {
        Child child = new Child();

        Field field = ReflectionUtils.getField(Child.class, "childField");
        ReflectionUtils.setFieldValue(child, field, "value");

        Assert.assertThat(child.childField, is("value"));
    }

    @Test(expected = ReflectionException.class)
    public void testSetFieldValueParent() {
        Child child = new Child();

        ReflectionUtils.setFieldValue(child, "parentField", "value");
    }


    @Test
    public void testSetFieldValueParentViaField() {
        Child child = new Child();

        Field field = ReflectionUtils.getField(Parent.class, "parentField");
        ReflectionUtils.setFieldValue(child, field, "value");

        Assert.assertThat(((Parent)child).parentField, is("value"));
    }


    private <T> Set<T> asSet(List<T> fields) {
        return new HashSet<T>(fields);
    }

    private <T> Set<T> asSet(T... fields) {
        return asSet(Arrays.asList(fields));
    }

    private Field field(Class type, String field) {
        return ReflectionUtils.getField(type, field);
    }
    private Method method(Class type, String field) {
        return ReflectionUtils.getMethod(type, field);
    }


    private static class Parent {

        private String field;

        private String parentField;

        private void parentMethod() {

        }

        private void method() {

        }

    }

    private static class Child extends Parent {

        private String field;

        private String childField;

        private void childMethod() {

        }
        private void method() {

        }

    }

}


