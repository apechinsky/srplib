package org.srplib.reflection;

import org.junit.Assert;
import org.junit.Test;
import org.srplib.reflection.ProxyUtils;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

/**
 *
 * @author Anton Pechinsky
 */
public class ProxyUtilsTest {

    private TestInterface testInterface;

    public ProxyUtilsTest() {
        testInterface = ProxyUtils.emptyInstance(TestInterface.class);
    }

    @Test
    public void testVoidMethod() throws Exception {
        testInterface.getVoid();
    }

    @Test
    public void testReturnsObjectMethod() throws Exception {
        Assert.assertThat(testInterface.getString(), nullValue());
    }

    @Test
    public void testReturnPrimitiveMethod() throws Exception {
        Assert.assertThat(testInterface.getInt(), is(0));
    }

    private interface TestInterface {

        void getVoid();

        String getString();

        int getInt();

    }
}
