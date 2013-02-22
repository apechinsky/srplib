package org.srplib.reflection;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.srplib.reflection.ReflectionBeanBuilder;
import org.srplib.reflection.ReflectionException;
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
        Assert.assertThat(bean.param1, is("1"));
        Assert.assertThat(bean.param2, is("2"));
        Assert.assertThat(bean.param3, is(3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testActualParametersDontMatchFormal() throws Exception {
        new ReflectionBeanBuilder<TestBean>(TestBean.class)
            .parameters()
            .newInstance("redundant parameter");
    }

//"CustomMessage(parameter). Instance creation error [class: org.srplib.reflection.ReflectionBeanBuilderTest$TestBean, parameters: [class java.lang.String] values: [ConstructorExceptionMessage.]]. ConstructorExceptionMessage."
//"CustomMessage(parameter). Instance creation error [class: org.srplib.reflection.ReflectionBeanBuilderTest$TestBean, parameters: [class java.lang.String] values: [ConstructorExceptionMessage.]]."

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
                "org.srplib.reflection.ReflectionBeanBuilderTest$TestBean.constructor(java.lang.String) " +
                "arguments: [ConstructorExceptionMessage.]";
            Assert.assertThat(e.getMessage(), is(expectedErrorMessage));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnexistingConstructor() throws Exception {
        ReflectionBeanBuilder.create(TestBean.class).parameters(int.class);
    }

    private static class TestBean {

        private String param1;

        private String param2;

        private int param3;

        public TestBean() {
        }

        public TestBean(String exceptionMessage) {
            if (exceptionMessage != null) {
                throw new IllegalStateException(exceptionMessage);
            }
        }

        public TestBean(String param1, String param2, int param3) {
            this.param1 = param1;
            this.param2 = param2;
            this.param3 = param3;
        }
    }
}
