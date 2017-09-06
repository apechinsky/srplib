package org.srplib.conversion.mapper;

import java.util.LinkedHashMap;
import java.util.Map;

import org.hamcrest.Matcher;
import org.hamcrest.core.Is;
import org.srplib.contract.Argument;

/**
 * A builder for creating {@link MatcherMapConverter}.
 *
 * <pre>
 *  MatcherMapConverter<String, String> codeToMessageConverter = MatcherMapBuilder.<String, String>create()
 *      .setDefaultValue("Unknown error!")
 *      .map("200", "Ok")
 *      .map(new RegexMatcher("3.."), "Redirect!")
 *      .map(new RegexMatcher("4.."), "Client error!")
 *      .map(new RegexMatcher("5.."), "Server error!")
 *      .build();
 *
 *  String message = codeToMessageConverter.convert(httpStatusCode);
 * </pre>
 *
 * @author Q-APE
 */
public class MatcherMapBuilder<I, O> {

    private Map<Matcher<I>, O> matcherMap;

    private O defaultValue;

    private boolean throwExceptionOnNoMatch = true;

    private String noMatchMessagePattern = MatcherMapConverter.DEFAULT_NO_MATCH_MESSAGE_PATTERN;

    /**
     * Creates empty mapper.
     *
     * @param <I> source type
     * @param <O> target type
     * @return MatcherMapBuilder empty mapper.
     */
    public static <I, O> MatcherMapBuilder<I, O> create() {
        return new MatcherMapBuilder<I, O>();
    }

    /**
     * Creates mapper from specified map.
     *
     * @param map Map a map to create mapper from.
     * @param <I> source type
     * @param <O> target type
     * @return MatcherMapBuilder mapper.
     */
    public static <I, O> MatcherMapBuilder<I, O> create(Map<I, O> map) {
        MatcherMapBuilder<I, O> mapper = MatcherMapBuilder.create();
        for (I key : map.keySet()) {
            mapper.map(key, map.get(key));
        }
        return mapper;
    }

    /**
     * MatcherMapBuilder constructor with no mapping and null default value.
     */
    public MatcherMapBuilder() {
        this.matcherMap = new LinkedHashMap<Matcher<I>, O>();
        this.defaultValue = null;
    }


    /**
     * MatcherMapBuilder constructor with predefined matcher map and specified default value.
     *
     * @param matcherMap Map<Matcher<I>, O> a non-null matcher to an object map
     * @param defaultValue default value object
     * @throws IllegalArgumentException if matcherMap is is null
     */
    public MatcherMapBuilder(Map<Matcher<I>, O> matcherMap, O defaultValue) {
        Argument.checkNotNull(matcherMap, "Matcher map must not be null!");

        this.matcherMap = matcherMap;
        this.defaultValue = defaultValue;
    }

    /**
     * Set default value.
     *
     * <p>Default value is used when no mapping is found for an object.</p>
     *
     * @param defaultValue Object default value.
     * @return this
     */
    public MatcherMapBuilder<I, O> setDefaultValue(O defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    /**
     * Set message pattern to be used to construct error message when no matches is found.
     *
     * @param pattern String message pattern with single string placeholder '%s'
     */
    public MatcherMapBuilder<I, O>  setNoMatchMessagePattern(String pattern) {
        this.noMatchMessagePattern = pattern;
        return this;
    }

    /**
     * Specifies whether to throw an exception if no match is found and no default value is specified.
     *
     * @param throwExceptionOnNoMatch boolean a flag. Default value is true.
     */
    public MatcherMapBuilder<I, O> throwExceptionOnNoMatch(boolean throwExceptionOnNoMatch) {
        this.throwExceptionOnNoMatch = throwExceptionOnNoMatch;
        return this;
    }

    /**
     * Maps specified Harcrest matcher to value.
     *
     * <p>This is the most flexible method. It's acceptable to pass any matcher from Harmcrest matcher library.</p>
     *
     * @param matcher Matcher hamcrest matcher
     * @param value value to map
     * @return this
     */
    public MatcherMapBuilder<I, O> map(Matcher<I> matcher, O value) {
        matcherMap.put(matcher, value);
        return this;
    }

    /**
     * Maps specified source value to specified output value.
     *
     * @param input source value
     * @param output target value
     * @return this
     */
    public MatcherMapBuilder<I, O> map(I input, O output) {
        matcherMap.put(Is.is(input), output);
        return this;
    }

    /**
     * Create {@link MatcherMapConverter} basing on previously specified parameters.
     *
     * @return MatcherMapConverter a converter.
     */
    public MatcherMapConverter<I, O> build() {
        MatcherMapConverter<I, O> matcherConverter = new MatcherMapConverter<I, O>(matcherMap);
        matcherConverter.setDefaultValue(defaultValue);
        matcherConverter.setNoMatchMessagePattern(noMatchMessagePattern);
        matcherConverter.setThrowExceptionOnNoMatch(throwExceptionOnNoMatch);
        return matcherConverter;
    }

}
