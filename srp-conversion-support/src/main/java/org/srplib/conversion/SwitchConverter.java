package org.srplib.conversion;

import java.util.Map;

import org.srplib.contract.Argument;
import org.srplib.contract.Assert;

/**
 * A converter encapsulating switch operator logic.
 *
 * <p>Input value is used as a key to configured map. If map contains no mapping for specified value then null is returned or
 * an exception is thrown depending on strict property</p>
 *
 * @author Anton Pechinsky
 */
public class SwitchConverter<I, O> implements Converter<I, O> {

    public static final String DEFAULT_NO_MATCH_MESSAGE_PATTERN = "There is no mapped value for key '%s'.";

    private Map<I, O> map;

    private O defaultValue;

    private boolean defaultValueSet = false;

    private String noMatchMessagePattern = DEFAULT_NO_MATCH_MESSAGE_PATTERN;


    /**
     * Construct converter using specified map.
     *
     * @param map Map input value to output value map
     * then IllegalArgumentException is thrown, if {@code false} then {@code null} is returned.
     */
    public SwitchConverter(Map<I, O> map) {
        Argument.checkNotNull(map, "Values map must not be null.");
        this.map = map;
        this.defaultValueSet = false;
    }

    public SwitchConverter(Map<I, O> map, O defaultValue) {
        Argument.checkNotNull(map, "Values map must not be null.");
        this.map = map;
        this.defaultValue = defaultValue;
        this.defaultValueSet = true;
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

    @Override
    public O convert(I input) {
        boolean valueExists = map.containsKey(input);

        Argument.checkTrue(valueExists || defaultValueSet, noMatchMessagePattern, input);

        return valueExists ? map.get(input) : defaultValue;
    }

}

