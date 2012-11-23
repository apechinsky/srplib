package org.srplib.support;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;

/**
 * @author Anton Pechinsky
 */
public class MapBuilderTest {
    @Test
    public void testGetAndPut() throws Exception {
        Map<String, Integer> map = new MapBuilder<String, Integer>()
            .put("1", 1)
            .put("2", 2)
            .put("3", 3)
            .build();

        Assert.assertThat(map.get("1"), is(1));
        Assert.assertThat(map.get("2"), is(2));
        Assert.assertThat(map.get("3"), is(3));
    }


}
