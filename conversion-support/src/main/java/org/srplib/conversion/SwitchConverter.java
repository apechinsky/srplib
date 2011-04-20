package org.srplib.conversion;

import java.util.Map;

import org.srplib.contract.Argument;

/**
 * A converter encapsulating switch operator logic.
 *
 * <p>Input value is used as a key to configured map. If map contains no mapping for specified value then null is returned or
 * an exception is thrown depending on strict property</p>
 *
 * @author Q-APE
 */
public class SwitchConverter<I, O> implements Converter<I, O> {

    private Map<I, O> map;

    private boolean strict;

    /**
     * Construct converter using specified map.
     *
     * @param map Map input value to output value map
     * @param strict boolean a flag indicating what to do if no mapping is found for input value. If {@code true}
     * then IllegalArgumentException is thrown, if {@code false} then {@code null} is returned.
     */
    public SwitchConverter(Map<I, O> map, boolean strict) {
        Argument.checkNotNull(map, "Values map must not be null.");
        this.map = map;
        this.strict = strict;
    }

    /**
     * Construct converter using specified map.
     *
     * <p>Call to this method is equivalent to {@code new SwitchConverter(map, true)}.</p>
     *
     * @param map Map input value to output value map
     */
    public SwitchConverter(Map<I, O> map) {
        this(map, true);
    }

    @Override
    public O convert(I input) {
        O value = map.get(input);

        if (strict && value == null) {
            throw new IllegalArgumentException(String.format("There is no mapped value for key '%s'", "" + input));
        }

        return value;
    }
}

