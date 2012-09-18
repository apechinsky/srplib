package org.srplib.support;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * An empty implementation of {@link InvocationHandler}.
 *
 * <p>The main purpose is easy creation of null value objects.</p>
 *
 * @author Q-APE
 */
public class EmptyInvocationHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // do nothing since this is an empty implementation
        return null;
    }

}