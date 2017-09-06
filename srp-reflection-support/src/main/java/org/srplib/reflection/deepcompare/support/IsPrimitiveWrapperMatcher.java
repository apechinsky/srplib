package org.srplib.reflection.deepcompare.support;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.srplib.reflection.ReflectionUtils;

/**
 * A matcher checking if class is Java primitive wrapper type.
 *
 * @author Anton Pechinsky
 */
public class IsPrimitiveWrapperMatcher extends TypeSafeMatcher<Class<?>> {

    @Override
    protected boolean matchesSafely(Class<?> item) {
        return ReflectionUtils.isPrimitiveWrapper(item);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("primitive wrapper");
    }

    /**
     * Matcher objectfactory method.
     *
     * @return Matcher
     */
    public static Matcher<Class<?>> isPrimitiveWrapper() {
        return new IsPrimitiveWrapperMatcher();
    }

}
