package org.srplib.support;

/**
 * @author Anton Pechinsky
 */
public class ExceptionUtils {

    /**
     * Rethrows Throwable. Applied in top level code which catches {@link Throwable} handle wants to rethrow it
     * without translation.
     *
     * <pre>
     *     try {
     *        // do useful work
     *     }
     *     catch (Throwable e) {
     *         // do something
     *         ExceptionUtils.rethrow(e)
     *     }
     * </pre>
     *
     * @param throwable Throwable
     */
    public static void rethrow(Throwable throwable) {
        if (throwable instanceof RuntimeException) {
            throw (RuntimeException) throwable;
        }
        else if (throwable instanceof Error) {
            throw (Error)throwable;
        }
        else {
            throw new IllegalStateException("Checked exception of type '" + throwable.getClass() +
                "' passed to handleThrowable()", throwable);
        }
    }

}
