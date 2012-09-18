package org.srplib.support;

import java.lang.reflect.Proxy;

/**
 * Helper class containing static utility methods.
 *
 * @author Anton Pechinsky
 */
public class ProxyUtils {

    /**
     * Creates an empty implementation of specified interface. In newly created object all all methods do nothing.
     *
     * <p>Main use case is easy creation of "null value" objects and free functional code from empty inner classes. </p>
     *
     * <pre>
     *     // usage example
     *     // MouseListener listener = Utils.emptyInstance(MouseListener.class);
     * </pre>
     *
     * @param interfaceClass Class an interface to create implementation for
     * @return an empty implementation
     */
    @SuppressWarnings("unchecked")
    public static <T> T emptyInstance(Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(
            interfaceClass.getClassLoader(), new Class[] {interfaceClass}, new EmptyInvocationHandler());
    }

}
