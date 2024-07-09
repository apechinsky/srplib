package org.srplib.reflection.valuefactory.factories;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * @author Anton Pechinsky
 */
public class IsArrayMatcher extends TypeSafeMatcher<Class<?>> {

    @Override
    protected boolean matchesSafely(Class<?> item) {
        return item.isArray();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("isArray");
    }

    public static Matcher<Class<?>> isArray() {
        return new IsArrayMatcher();
    }

}
