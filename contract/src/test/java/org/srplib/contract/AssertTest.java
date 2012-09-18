package org.srplib.contract;

import org.junit.Test;

/**
 * Test class for Assert.
 *
 * @author Anton Pechinsky
 */
public class AssertTest {

    @Test
    public void testcheckTrue() {
        Assert.checkTrue(true, "message");
    }

    @Test(expected = IllegalStateException.class)
    public void testcheckTrueFail() {
        Assert.checkTrue(false, "message");
    }

    @Test
    public void testcheckFalse() {
        Assert.checkFalse(false, "message");
    }

    @Test(expected = IllegalStateException.class)
    public void testcheckFalseFail() {
        Assert.checkFalse(true, "message");
    }

    @Test
    public void testcheckNull() {
        Assert.checkNull(null, "message");
    }

    @Test(expected = IllegalStateException.class)
    public void testcheckNullFail() {
        Assert.checkNull("non-null", "message");
    }

    @Test
    public void testNotNull() {
        Assert.checkNotNull("non-null", "message");
    }

    @Test(expected = IllegalStateException.class)
    public void testNotNullFail() {
        Assert.checkNotNull(null, "message");
    }

    @Test
    public void testcheckBlank() {
        Assert.checkBlank(null, "message");
        Assert.checkBlank("", "message");
        Assert.checkBlank(" ", "message");
        Assert.checkBlank(" \n\r\t ", "message");
    }

    @Test(expected = IllegalStateException.class)
    public void testcheckBlankFail() {
        Assert.checkBlank("not-blank", "message");
    }

    @Test
    public void testcheckNotBlank() {
        Assert.checkNotBlank("not-blank", "message");
    }

    @Test(expected = IllegalStateException.class)
    public void testcheckNotBlankFail() {
        Assert.checkNotBlank(null, "message");
        Assert.checkNotBlank("", "message");
        Assert.checkNotBlank(" ", "message");
        Assert.checkNotBlank(" \n\r\t ", "message");
    }

    @Test
    public void testcheckEqual() {
        Assert.checkEqual(null, null, "message");
        Assert.checkEqual("1", "1", "message");
    }

    @Test(expected = IllegalStateException.class)
    public void testcheckEqualFail() {
        Assert.checkEqual(1, 2, "message");
        Assert.checkEqual("1", null, "message");
    }

    @Test
    public void testcheckNotEqual() {
        Assert.checkNotEqual(1, 2, "message");
    }

    @Test(expected = IllegalStateException.class)
    public void testcheckNotEqualFail() {
        Assert.checkNotEqual(null, null, "message");
        Assert.checkNotEqual("1", "1", "message");
    }
}
