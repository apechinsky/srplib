package org.srplib.reflection.deepcompare.support;

import java.util.List;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.srplib.contract.Argument;
import org.srplib.reflection.deepcompare.ConfigurableDeepComparator;

/**
 * A Hamcrest matcher for DeepComparator.
 *
 * @author Q-APE
 */
public class DeepComparatorMatcher extends BaseMatcher<Object> {

    private ConfigurableDeepComparator deepComparator;

    private Object expected;

    private List<String> diffs;

    public DeepComparatorMatcher(ConfigurableDeepComparator deepComparator, Object expected) {
        Argument.checkNotNull(deepComparator, "deepComparator must not be null!");
        Argument.checkNotNull(expected, "expected must not be null!");

        this.deepComparator = deepComparator;
        this.expected = expected;
    }

    @Override
    public boolean matches(Object actual) {
        diffs = deepComparator.compare(expected, actual);

        return diffs.isEmpty();
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(expected);
    }

    @Override
    public void describeMismatch(Object item, Description description) {
        description.appendValue(item);
        description.appendText("\n");

        for (String diff : diffs) {
            description.appendText(diff);
            description.appendText("\n");
        }
    }

    public static DeepComparatorMatcher deepCompare(Object expected) {
        ConfigurableDeepComparator deepComparator = new ConfigurableDeepComparator(new StandardConfiguration());
        return new DeepComparatorMatcher(deepComparator, expected);
    }

}
