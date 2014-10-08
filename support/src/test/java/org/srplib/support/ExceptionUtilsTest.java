package org.srplib.support;

import java.lang.reflect.UndeclaredThrowableException;

import org.junit.Test;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.*;

public class ExceptionUtilsTest {

    @Test
    public void testAsUncheckedUncheckedExceptionReturnedAsIs() throws Exception {
        RuntimeException exception = new RuntimeException();

        RuntimeException returned = ExceptionUtils.asUnchecked(exception);

        assertThat(returned, sameInstance(exception));
    }

    @Test
    public void testAsUncheckedCheckedExceptionsIsWrapped() throws Exception {
        Exception exception = new Exception();

        RuntimeException returned = ExceptionUtils.asUnchecked(exception);

        assertThat(returned, instanceOf(UndeclaredThrowableException.class));
    }

    @Test
    public void testAsUncheckedErrorIsRethrown() throws Exception {
        Error exception = new Error();

        try {
            ExceptionUtils.asUnchecked(exception);
        }
        catch (Error e) {
            assertThat(e, sameInstance(exception));
        }
    }
}