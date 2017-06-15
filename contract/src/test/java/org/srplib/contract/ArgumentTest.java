package org.srplib.contract;

import org.junit.Test;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Test class for Argument.
 *
 * @author Anton Pechinsky
 */
public class ArgumentTest {

    @Test
    public void testcheckTrue() {
        Argument.checkTrue(true, "message");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testcheckTrueFail() {
        Argument.checkTrue(false, "message");
    }

    @Test
    public void testcheckFalse() {
        Argument.checkFalse(false, "message");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testcheckFalseFail() {
        Argument.checkFalse(true, "message");
    }

    @Test
    public void testcheckNull() {
        Argument.checkNull(null, "message");
    }


    @Test(expected = IllegalArgumentException.class)
    public void testcheckNullFail() {
        Argument.checkNull("non-null", "message");
    }

    @Test
    public void testNotNull() {
        Argument.checkNotNull("non-null", "message");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotNullFail() {
        Argument.checkNotNull(null, "message");
    }

    @Test
    public void testcheckBlank() {
        Argument.checkBlank(null, "message");
        Argument.checkBlank("", "message");
        Argument.checkBlank(" ", "message");
        Argument.checkBlank(" \n\r\t ", "message");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testcheckBlankFail() {
        Argument.checkBlank("not-blank", "message");
    }

    @Test
    public void testcheckNotBlank() {
        Argument.checkNotBlank("not-blank", "message");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testcheckNotBlankFail() {
        Argument.checkNotBlank(null, "message");
        Argument.checkNotBlank("", "message");
        Argument.checkNotBlank(" ", "message");
        Argument.checkNotBlank(" \n\r\t ", "message");
    }

    @Test
    public void testcheckEqual() {
        Argument.checkEqual(null, null, "message");
        Argument.checkEqual("1", "1", "message");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testcheckEqualFail() {
        Argument.checkEqual(1, 2, "message");
        Argument.checkEqual("1", null, "message");
    }

    @Test
    public void testcheckNotEqual() {
        Argument.checkNotEqual(1, 2, "message");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testcheckNotEqualFail() {
        Argument.checkNotEqual(null, null, "message");
        Argument.checkNotEqual("1", "1", "message");
    }

    @Test
    public void failure() throws Exception {
        RuntimeException exception = Argument.failure("message");

        assertThat(exception, instanceOf(IllegalArgumentException.class));
        assertThat(exception.getMessage(), is("message"));
    }
}
