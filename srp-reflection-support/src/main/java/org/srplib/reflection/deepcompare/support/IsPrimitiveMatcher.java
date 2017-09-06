package org.srplib.reflection.deepcompare.support;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * A matcher checking if class is Java primitive type.
 *
 * @author Anton Pechinsky
 */
public class IsPrimitiveMatcher extends TypeSafeMatcher<Class<?>> {

    @Override
    protected boolean matchesSafely(Class<?> item) {
        return item.isPrimitive();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("primitive");
    }

    /**
     * Matcher objectfactory method.
     *
     * @return Matcher
     */
    public static Matcher<Class<?>> isPrimitive() {
        return new IsPrimitiveMatcher();
    }

}
