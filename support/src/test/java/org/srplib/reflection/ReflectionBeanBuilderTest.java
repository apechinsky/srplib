package org.srplib.reflection;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

public class ReflectionBeanBuilderTest {

    @Test
    public void testCreateWithDefaultConstructor() throws Exception {
        TestBean bean = ReflectionBeanBuilder.create(TestBean.class).newInstance();
        Assert.assertThat(bean, CoreMatchers.instanceOf(TestBean.class));
    }

    @Test
    public void testCreateWithConstructor() throws Exception {
        TestBean bean = ReflectionBeanBuilder.create(TestBean.class)
            .parameters(String.class, String.class, int.class)
            .newInstance("1", "2", 3);
        Assert.assertThat(bean, instanceOf(TestBean.class));
        Assert.assertThat(bean.getParam1(), is("1"));
        Assert.assertThat(bean.getParam2(), is("2"));
        Assert.assertThat(bean.getParam3(), is(3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testActualParametersDontMatchFormal() throws Exception {
        new ReflectionBeanBuilder<TestBean>(TestBean.class)
            .parameters()
            .newInstance("redundant parameter");
    }

    @Test
    public void testConstructorException() throws Exception {
        try {
            ReflectionBeanBuilder.create(TestBean.class)
                .errorMessage("CustomMessage(%s).", "parameter")
                .parameters(String.class)
                .newInstance("ConstructorExceptionMessage.");

            Assert.fail("Should throw exception if underlying constructor throws exception.");
        }
        catch (ReflectionException e) {
            String expectedErrorMessage = "CustomMessage(parameter). Instance creation error " +
                "org.srplib.reflection.TestBean.constructor(java.lang.String) " +
                "arguments: [ConstructorExceptionMessage.]";
            Assert.assertThat(e.getMessage(), is(expectedErrorMessage));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnexistingConstructor() throws Exception {
        ReflectionBeanBuilder.create(TestBean.class).parameters(int.class);
    }

}
