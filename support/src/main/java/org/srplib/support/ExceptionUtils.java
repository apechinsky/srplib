package org.srplib.support;

/**
 * @author Anton Pechinsky
 */
public class ExceptionUtils {

    /**
     * Returns specified {@link Throwable} as {@link RuntimeException}.
     *
     * <p>
     *  Sometimes client code is forced to catch {@link Throwable} but itself it doesn't want to declare Throwable in its
     *  throws clause. In such cases we need to rethrow errors and runtime exceptions.
     * </p>
     *
     * <pre>
     *  Result someMethod() { // throws Throwable
     *      try {
     *          externalApiDeclaringThrowable();
     *      }
     *      catch (RelevantException e) {
     *          // handle relevant exception
     *      }
     *      catch (Throwable e) {
     *          // rethrow other exceptions
     *          throw ExceptionUtils.asUnchecked(e)
     *      }
     * }
     * </pre>
     *
     * @param throwable Throwable
     * @return RuntimeException if throwable is {@link RuntimeException} it's returned as is, if throwable is an
     * {@link Error} it's rethrown, otherwise exception is wrapped into {@link RuntimeException} and rethrown.
     */
    public static RuntimeException asUnchecked(Throwable throwable) {
        if (throwable instanceof RuntimeException) {
            return (RuntimeException)throwable;
        }
        else if (throwable instanceof Error) {
            throw (Error)throwable;
        }
        else {
            return new RuntimeException(throwable);
        }
    }

    /**
     * Rethrows {@link Throwable} as {@link RuntimeException}.
     *
     * <p>Prefer {@link #asUnchecked(Throwable)} to this method.</p>
     *
     * @param throwable Throwable
     * @see #asUnchecked(Throwable)
     */
    public static void rethrow(Throwable throwable) {
        throw asUnchecked(throwable);
    }
}
