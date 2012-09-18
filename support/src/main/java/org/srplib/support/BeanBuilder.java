package org.srplib.support;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Q-APE
 */
public class BeanBuilder<B>  {

    public static final String BUILD_METHOD = "build";

    public static <T, B extends Builder<T>> B create(T object, Class<? extends Builder<B>> builderInterface) {
        BeanBuilderInvocationHandler invocationHandler = new BeanBuilderInvocationHandler(object);

        return (B)Proxy.newProxyInstance(
            BeanBuilder.class.getClassLoader(), new Class[] {builderInterface }, invocationHandler);
    }

    private static class BeanBuilderInvocationHandler implements InvocationHandler {

        private Object object;

        private BeanBuilderInvocationHandler(Object object) {
            this.object = object;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object result;
            if (BUILD_METHOD.equals(method.getName())) {
                // Build method. Return final object.
                result = object;
            }
            else {
                // property set method.
                Method originalMethod = getOriginalMethod(method);
                originalMethod.invoke(object, args);
                result = proxy;

            }
            return result;
        }

        private Method getOriginalMethod(Method method) {
            String originalMethodName = "set" + firstCharToUpper(method.getName());
            try {
                return object.getClass().getDeclaredMethod(originalMethodName, method.getParameterTypes());
            }
            catch (NoSuchMethodException e) {
                throw new IllegalStateException(String.format(
                    "Original method not found in class '%s'. Called builder's method: '%s', " +
                        "expected corresponding object's method: '%s'",
                    object.getClass().getName(), method.getName(), originalMethodName), e);
            }
        }

        private String firstCharToUpper(String string) {
            return Character.toUpperCase(string.charAt(0)) + string.substring(1);
        }
    }

}
