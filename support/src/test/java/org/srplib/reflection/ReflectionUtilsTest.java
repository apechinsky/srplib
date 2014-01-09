package org.srplib.reflection;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hamcrest.collection.IsIterableContainingInOrder;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;
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
    public void testGetTypeParameter() {
        Object typed = new TypeToken<String>() {
        };

        Class parameterType = ReflectionUtils.getTypeParameter(typed.getClass());

        Assert.assertThat(parameterType, new IsEqual<Class>(String.class));
    }

    @Test
    public void testGetTypeParameters() {
        Object typed = new TypeTokenMultiple<String, Integer, Boolean>() {
        };

        List parameterTypes = ReflectionUtils.getTypeParameters(typed.getClass());

        Assert.assertThat(parameterTypes, IsIterableContainingInOrder.contains(String.class, Integer.class, Boolean.class));
    }

    @Test
    public void testGetTypeParameterNestedParameter() {
        Object typed = new TypeToken<List<String>>() {
        };

        Class parameterType = ReflectionUtils.getTypeParameter(typed.getClass());

        Assert.assertThat(parameterType, new IsEqual<Class>(List.class));
    }

    @Test
    public void testGetTypeParameterNoParameter() {
        Object typed = new TypeToken() {
        };

        Class parameterType = ReflectionUtils.getTypeParameter(typed.getClass());

        Assert.assertThat(parameterType, nullValue());
    }

    class TypeToken<T> {

    }

    class TypeTokenMultiple<J, A, V> {

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
    public void testFindMethodRecursivelyNonExisting() {
        Method method = ReflectionUtils.findMethodRecursively(Child.class, "nonExistingMethod");
        Assert.assertThat(method, nullValue());
    }

    @Test
    public void testGetMethodRecursively() {
        Method method = ReflectionUtils.findMethodRecursively(Child.class, "parentMethod");
        Assert.assertThat(method, is(method(Parent.class, "parentMethod")));
    }

    @Test(expected = ReflectionException.class)
    public void testGetMethodRecursivelyNotExisting() {
        ReflectionUtils.getMethodRecursively(Child.class, "nonExistingMethod");
    }

    @Test
    public void testGetField() {
        Field field = ReflectionUtils.getField(Child.class, "field");
        Assert.assertThat(field, is(field(Child.class, "field")));
    }

    @Test
    public void testGetFieldNonExisting() {
        try {
            ReflectionUtils.getField(Child.class, "nonExistingField");
        }
        catch (ReflectionException e) {
            Assert.assertThat(e.getMessage(), is("No declared field 'nonExistingField' in class " +
                "'org.srplib.reflection.ReflectionUtilsTest$Child'."));
        }
    }

    @Test
    public void testFindField() {
        Field field = ReflectionUtils.findField(Child.class, "field");
        Assert.assertThat(field, is(field(Child.class, "field")));
    }

    @Test
    public void testFindFieldNonExisting() {
        Field field = ReflectionUtils.findField(Child.class, "nonExistingField");
        Assert.assertThat(field, nullValue());
    }

    @Test
    public void testGetFieldRecursively() {
        Field field = ReflectionUtils.getFieldRecursively(Child.class, "parentField");
        Assert.assertThat(field, is(field(Parent.class, "parentField")));
    }

    @Test
    public void tesGetFieldRecursivelyNotExisting() {
        try {
            ReflectionUtils.getFieldRecursively(Child.class, "nonExistingField");
        }
        catch (ReflectionException e) {
            Assert.assertThat(e.getMessage(), is("No declared field 'nonExistingField' in class " +
                "'org.srplib.reflection.ReflectionUtilsTest$Child' or its superclasses."));
        }
    }

    @Test
    public void testFindFieldRecursively() {
        Field field = ReflectionUtils.findFieldRecursively(Child.class, "parentField");
        Assert.assertThat(field, is(field(Parent.class, "parentField")));
    }

    @Test
    public void tesFindFieldRecursivelyNotExisting() {
        Field field = ReflectionUtils.findFieldRecursively(Child.class, "nonExistingField");
        Assert.assertThat(field, nullValue());
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

        Assert.assertThat(((Parent) child).parentField, is("value"));
    }

    @Test
    public void testInvokeMethod() {
        Child child = new Child();

        Method method = ReflectionUtils.getMethod(Child.class, "childMethod", String.class);
        String value = ReflectionUtils.invokeMethod(child, method, "value");

        Assert.assertThat(value, is("value"));
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

        private String parentMethod(String string) {
            return string;
        }

        private void method() {

        }

    }

    private static class Child extends Parent {

        private String field;

        private String childField;

        private void childMethod() {

        }

        private String childMethod(String string) {
            return string;
        }

        private void method() {

        }


    }

}


