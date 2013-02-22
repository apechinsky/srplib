package org.srplib.model;

import org.junit.Assert;
import org.junit.Test;
import org.srplib.reflection.ReflectionException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

/**
 * Test form BeanPropertyValueAdapter
 *
 * @author Anton Pechinsky
 */
public class BeanPropertyValueAdapterTest {

    @Test
    public void testForBeanValueType() throws Exception {
        TestBean testBean = TestBean.create();

        BeanPropertyValueAdapter<TestBean, String> idModel = BeanPropertyValueAdapter.forBean(testBean, "id");
        BeanPropertyValueAdapter<TestBean, String> nameModel = BeanPropertyValueAdapter.forBean(testBean, "name");
        BeanPropertyValueAdapter<TestBean, Integer> ageModel = BeanPropertyValueAdapter.forBean(testBean, "age");

        Assert.assertThat(idModel.getType(), equalTo(String.class));
        Assert.assertThat(nameModel.getType(), equalTo(String.class));
        Assert.assertThat(ageModel.getType(), equalTo(int.class));
    }

    @Test
    public void testForBeanGet() throws Exception {
        TestBean testBean = TestBean.create();

        BeanPropertyValueAdapter<TestBean, String> idModel = BeanPropertyValueAdapter.forBean(testBean, "id");
        BeanPropertyValueAdapter<TestBean, String> nameModel = BeanPropertyValueAdapter.forBean(testBean, "name");
        BeanPropertyValueAdapter<TestBean, Integer> ageModel = BeanPropertyValueAdapter.forBean(testBean, "age");

        Assert.assertThat(idModel.getValue(), is(testBean.getId()));
        Assert.assertThat(nameModel.getValue(), is(testBean.getName()));
        Assert.assertThat(ageModel.getValue(), is(testBean.getAge()));
    }

    @Test
    public void testForBeanSet() throws Exception {
        TestBean testBean = TestBean.create();

        BeanPropertyValueAdapter<TestBean, String> idModel = BeanPropertyValueAdapter.forBean(testBean, "id");
        BeanPropertyValueAdapter<TestBean, String> nameModel = BeanPropertyValueAdapter.forBean(testBean, "name");
        BeanPropertyValueAdapter<TestBean, Integer> ageModel = BeanPropertyValueAdapter.forBean(testBean, "age");

        idModel.setValue("MyId");
        nameModel.setValue("MyName");
        ageModel.setValue(27);

        Assert.assertThat(testBean.getId(), is("MyId"));
        Assert.assertThat(testBean.getName(), is("MyName"));
        Assert.assertThat(testBean.getAge(), is(27));
    }

    @Test(expected = ReflectionException.class)
    public void testForBeanUnexistingProperty() throws Exception {
        TestBean testBean = TestBean.create();

        BeanPropertyValueAdapter.forBean(testBean, "unexistingProperty");
    }

    @Test
    public void testForClassGet() throws Exception {
        TestBean testBean = TestBean.create();

        BeanPropertyValueAdapter<TestBean, String> idModel = BeanPropertyValueAdapter.forClass(TestBean.class, "id");
        BeanPropertyValueAdapter<TestBean, String> nameModel = BeanPropertyValueAdapter.forClass(TestBean.class, "name");
        BeanPropertyValueAdapter<TestBean, Integer> ageModel = BeanPropertyValueAdapter.forClass(TestBean.class, "age");

        idModel.setContext(testBean);
        nameModel.setContext(testBean);
        ageModel.setContext(testBean);

        Assert.assertThat(idModel.getValue(), is(testBean.getId()));
        Assert.assertThat(nameModel.getValue(), is(testBean.getName()));
        Assert.assertThat(ageModel.getValue(), is(testBean.getAge()));
    }

    @Test(expected = IllegalStateException.class)
    public void testForClassNoContext() throws Exception {
        BeanPropertyValueAdapter<TestBean, String> idModel = BeanPropertyValueAdapter.forClass(TestBean.class, "id");

        idModel.getValue();
    }

}
