package org.srplib.reflection;

import java.lang.reflect.UndeclaredThrowableException;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

/**
 *
 * @author Q-APE
 */
public class ReflectionInvokerTest {

    @Test
    public void testCreateWithDefaultConstructor() throws Exception {
        TestBean bean = ReflectionInvoker.constructor(TestBean.class).invoke();
        Assert.assertThat(bean, CoreMatchers.instanceOf(TestBean.class));
    }

    @Test
    public void testConstructor() throws Exception {
        TestBean bean = ReflectionInvoker.constructor(TestBean.class).parameters(String.class, String.class, int.class)
            .invoke("1", "2", 3);

        Assert.assertThat(bean, instanceOf(TestBean.class));
        Assert.assertThat(bean.getParam1(), is("1"));
        Assert.assertThat(bean.getParam2(), is("2"));
        Assert.assertThat(bean.getParam3(), is(3));
    }

    @Test(expected = TestBeanRuntimeException.class)
    public void testConstructorThrowingUncheckedException() throws Exception {

        ReflectionInvoker.constructor(TestBean.class)
            .parameters(String.class, boolean.class)
            .invoke("message", true);
    }

    @Test
    public void testConstructorThrowingCheckedException() throws Exception {

        try {
            ReflectionInvoker.constructor(TestBean.class)
                .parameters(String.class, boolean.class)
                .invoke("message", false);
        }
        catch (UndeclaredThrowableException e) {
            Assert.assertThat(e.getCause(), instanceOf(TestBeanException.class));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorArgumentDontMatchParameter() throws Exception {
        TestBean bean = ReflectionInvoker.constructor(TestBean.class).parameters().invoke("redundant argument");

        Assert.assertThat(bean, instanceOf(TestBean.class));
    }

    @Test
    public void testMethodWithoutParameters() throws Exception {
        TestBean testBean = new TestBean("1", "2", 3);

        String value = ReflectionInvoker.<TestBean, String>method(testBean, "getParam1").invoke();

        Assert.assertThat(value, is("1"));
    }

    @Test
    public void testMethodWithParameters() throws Exception {
        TestBean testBean = new TestBean("1", "2", 3);

        ReflectionInvoker.<TestBean, String>method(testBean, "setParam1")
            .parameters(String.class)
            .invoke("11");

        Assert.assertThat(testBean.getParam1(), is("11"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMethodArgumentDontMatchParameters() throws Exception {
        TestBean testBean = new TestBean("1", "2", 3);

        ReflectionInvoker.<TestBean, String>method(testBean, "setParam1")
            .parameters(String.class)
            .invoke("11", "excessiveArgument");
    }

    @Test(expected = TestBeanRuntimeException.class)
    public void testMethodThrowingUncheckedException() throws Exception {

        ReflectionInvoker.method(new TestBean(), "throwException")
            .parameters(String.class, boolean.class)
            .invoke("message", true);
    }

    @Test
    public void testMethodThrowingCheckedException() throws Exception {

        try {
            ReflectionInvoker.method(new TestBean(), "throwException")
                .parameters(String.class, boolean.class)
                .invoke("message", false);
        }
        catch (UndeclaredThrowableException e) {
            Assert.assertThat(e.getCause(), instanceOf(TestBeanException.class));
        }
    }

    @Test(expected = TestBeanRuntimeException.class)
    public void testStaticMethodThrowingUncheckedException() throws Exception {

        ReflectionInvoker.method(TestBean.class, "throwExceptionStatic")

            .parameters(String.class, boolean.class)
            .invoke("message", true);
    }

    @Test
    public void testStaticMethodThrowingCheckedException() throws Exception {

        try {
            ReflectionInvoker.method(TestBean.class, "throwExceptionStatic")
                .parameters(String.class, boolean.class)
                .invoke("message", false);
        }
        catch (UndeclaredThrowableException e) {
            Assert.assertThat(e.getCause(), instanceOf(TestBeanException.class));
        }
    }
}
