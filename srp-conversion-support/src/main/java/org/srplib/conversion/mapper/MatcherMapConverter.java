package org.srplib.conversion.mapper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matcher;
import org.srplib.contract.Argument;
import org.srplib.contract.Assert;
import org.srplib.conversion.Converter;

/**
 * Converter-mapper. Converts one value set to other value set as well as default value behavior (default value or an exception).
 *
 * <p>Useful as a replacement for {@code switch...case...default} and {@code if...else} chains.</p>
 *
 * <ul>Typical use cases:
 * <li>error code to error message translation</li>
 * <li>object status to CSS class translation</li>
 * <li>etc.</li>
 * </ul>
 *
 * <p>Internally this implementation leverages Hamcrest {@link Matcher}. First matcher wins.</p>
 *
 * <p>Caution!  If matchers are order sensitive, use {@link java.util.LinkedHashMap} implementation. </p>
 *
 * @author Q-APE
 */
public class MatcherMapConverter<I, O> implements Converter<I, O>, Serializable {

    private static final long serialVersionUID = -1L;

    public static final String DEFAULT_NO_MATCH_MESSAGE_PATTERN = "No matching value for '%s'.";


    private Map<Matcher<I>, O> matcherMap;

    private O defaultValue;

    private boolean throwExceptionOnNoMatch = true;

    private String noMatchMessagePattern = DEFAULT_NO_MATCH_MESSAGE_PATTERN;

    /**
     * Creates converter using {@link Matcher} to object map.
     *
     * @param matcherMap Map matcher to object map (non-null). Use LinkedHashMap if matchers are ordered)
     * @throws IllegalArgumentException if matcherMap is null
     */
    public MatcherMapConverter(Map<Matcher<I>, O> matcherMap) {
        Argument.checkNotNull(matcherMap, "matcherMap must not be null!");

        this.matcherMap = matcherMap;
    }

    protected MatcherMapConverter() {
        this.matcherMap = new HashMap<Matcher<I>, O>();
    }

    protected final void put(Matcher<I> key, O value) {
        this.matcherMap.put(key, value);
    }

    /**
     * Return an object to be returned if no matcher found for specified value.
     *
     * @return Object a default object
     */
    public O getDefaultValue() {
        return defaultValue;
    }

    /**
     * Specifies whether to throw an exception if no match is found and no default value is specified.
     *
     * @param throwExceptionOnNoMatch boolean a flag. Default value is true.
     */
    public void setThrowExceptionOnNoMatch(boolean throwExceptionOnNoMatch) {
        this.throwExceptionOnNoMatch = throwExceptionOnNoMatch;
    }

    /**
     * Set an object to be returned if no matcher found for specified value.
     *
     * @param defaultValue Object a default value. If not specified then an exception will be thrown/
     */
    public void setDefaultValue(O defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * Set message pattern to be used to construct error message when no matches is found.
     *
     * @param pattern String message pattern with single string placeholder '%s'
     */
    public void setNoMatchMessagePattern(String pattern) {
        Assert.checkNotNull(pattern, "Message pattern must not be null!");
        Assert.checkTrue(pattern.contains("%s"), "Message pattern '%s' is not valid string format pattern!", pattern);

        this.noMatchMessagePattern = pattern;
    }

    /**
     * Tries to find target value for specified input value.
     *
     * @param input input value
     * @return output value if mapping is defined by this matcher or default value (if defined) or {@code null} depending on
     * throwExceptionOnNoMatch value otherwise throws an exception.
     * @throws IllegalArgumentException if mapping isn't found and default value isn't defined and
     * throwExceptionOnNoMatch == true.
     */
    @Override
    public O convert(I input) {
        for (Map.Entry<Matcher<I>, O> entry : matcherMap.entrySet()) {
            Matcher<I> matcher = entry.getKey();
            if (matcher.matches(input)) {
                return entry.getValue();
            }
        }
        Argument.checkFalse(defaultValue == null && throwExceptionOnNoMatch, noMatchMessagePattern, input);

        return defaultValue;
    }
}
