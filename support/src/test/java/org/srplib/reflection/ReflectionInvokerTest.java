package org.srplib.reflection;

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
    public void testCreateWithConstructor() throws Exception {
        TestBean bean = ReflectionInvoker.constructor(TestBean.class).parameters(String.class, String.class, int.class)
            .invoke("1", "2", 3);

        Assert.assertThat(bean, instanceOf(TestBean.class));
        Assert.assertThat(bean.getParam1(), is("1"));
        Assert.assertThat(bean.getParam2(), is("2"));
        Assert.assertThat(bean.getParam3(), is(3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testArgumentDontMatchParametersConstructor() throws Exception {
        TestBean bean = ReflectionInvoker.constructor(TestBean.class).parameters().invoke("redundant parameter");

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
    public void testArgumentDontMatchParametersMethod() throws Exception {
        TestBean testBean = new TestBean("1", "2", 3);

        ReflectionInvoker.<TestBean, String>method(testBean, "setParam1")
            .parameters(String.class)
            .invoke("11", "excessiveArgument");
    }

}
