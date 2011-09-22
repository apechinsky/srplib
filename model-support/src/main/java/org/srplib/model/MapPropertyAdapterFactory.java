package org.srplib.model;

import java.util.Map;

import org.srplib.contract.Argument;

/**
 * Implementation of {@link PropertyAdapterFactory} which creates instances of {@link ValueModel} for value of map under
 * specified keys.
 *
 * @author Q-APE
 */
public class MapPropertyAdapterFactory<T> implements PropertyAdapterFactory<T> {

    private Map<String, T> map;

    /**
     * Creates factory for specified map.
     *
     * @param map a Map to create value models for.
     */
    public MapPropertyAdapterFactory(Map<String, T> map) {
        Argument.checkNotNull(map, "Map must not be null!");
        this.map = map;
    }

    /**
     * Creates factory.
     */
    public MapPropertyAdapterFactory() {
    }

    @Override
    public ValueModel<T> newAdapter(String property) {
        return map == null
            ? new MapValueAdapter<String, T>((Class<T>)null, property)
            : new MapValueAdapter<String, T>(map, property);
    }
}
