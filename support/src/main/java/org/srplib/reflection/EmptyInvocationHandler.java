package org.srplib.reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.srplib.reflection.ReflectionUtils;

/**
 * An empty implementation of {@link InvocationHandler}.
 *
 * <p>The main purpose is easy creation of null value objects.</p>
 *
 * @author Anton Pechinsky
 */
public class EmptyInvocationHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // do nothing since this is an empty implementation

        Class returnType = method.getReturnType();

        return ReflectionUtils.getInitValue(returnType);
    }

}
