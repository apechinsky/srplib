package org.srplib.model;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.srplib.support.MapBuilder;

import static org.hamcrest.CoreMatchers.is;

/**
 * @author Q-APE
 */
public class MapValueAdapterTest {

    @Test
    public void testSetValue() throws Exception {
        Map<String, Object> map = MapBuilder.newHashMap();

        ValueModel<Object> idModel = MapValueAdapter.create(map, "id");
        ValueModel<Object> nameModel = MapValueAdapter.create(map, "name");
        ValueModel<Object> ageModel = MapValueAdapter.create(map, "age");

        TestBean testBean = TestBean.create();

        idModel.setValue(testBean.getId());
        nameModel.setValue(testBean.getName());
        ageModel.setValue(testBean.getAge());

        Assert.assertThat(idModel.getValue(), is((Object) testBean.getId()));
        Assert.assertThat(nameModel.getValue(), is((Object) testBean.getName()));
        Assert.assertThat(ageModel.getValue(), is((Object) testBean.getAge()));


    }
}
