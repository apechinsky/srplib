package org.srplib.support;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.srplib.contract.Argument;

/**
 * Map builder.
 *
 * <p>The main purposes of this builder is to simplify map creation and initialization constructs.</p>
 *
 * <p>Map builder contains two types of methods:
 * <ul>
 *     <li>factory methods for direct creation of instances of {@link Map}</li>
 *     <li>builder methods for {@link Map} population.</li>
 * </ul>
 * </p>
 *
 * <pre>
 *     // Create map directly
 *     Map&lt;Key, Value&gt; map = MapBuilder.newHashMap();
 *     Map&lt;Key, Value&gt; map = MapBuilder.newLinkedHashMap();
 *
 *     // Create Map builder and add values
 *     Map&lt;Key, Value&gt; map = new MapBuilder&lt;Key, Value&gt;()
 *         .put(key1, value1)
 *         .put(key2, value2)
 *         .put(key3, value3)
 *         .build();
 *
 *      // unfortunately the following doesn't compile
 *     Map&lt;Key, Value&gt; map = MapBuilder.hashMap()
 *         .put(key1, value1)
 *         .build();
 * </pre>
 *
 * @author Anton Pechinsky
 */
public class MapBuilder<K, V> implements Builder<Map<K, V>> {

    private Map<K, V> map;

    /**
     * Create and return instance of {@link HashMap}.
     *
     * @param <K> map key type parameter
     * @param <V> map value type parameter
     * @return an instance of {@link HashMap}
     */
    public static <K, V> Map<K, V> newHashMap() {
        return new HashMap<K, V>();
    }

    /**
     * Create and return instance of {@link LinkedHashMap}.
     *
     * @param <K> map key type parameter
     * @param <V> map value type parameter
     * @return an instance of {@link LinkedHashMap}
     */
    public static <K, V> Map<K, V> newLinkedHashMap() {
        return new LinkedHashMap<K, V>();
    }

    /**
     * An alternative to constructor.
     *
     * @param map Map a map to use
     * @return MapBuilder map builder
     */
    public static <K, V> MapBuilder<K, V> map(Map<K, V> map) {
        return new MapBuilder<K, V>(map);
    }

    /**
     * Creates map builder with {@link HashMap}.
     *
     * @return MapBuilder map builder
     */
    public static <K, V> MapBuilder<K, V> hashMap() {
        Map<K, V> map = newHashMap();
        return map(map);
    }

    /**
     * Creates map builder with {@link LinkedHashMap}.
     *
     * @return MapBuilder map builder
     */
    public static <K, V> MapBuilder<K, V> linkedHashMap() {
        Map<K, V> map = MapBuilder.newLinkedHashMap();
        return map(map);
    }

    /**
     * Creates map builder with specified map.
     *
     * @param map Map a map to use
     */
    public MapBuilder(Map<K, V> map) {
        Argument.checkNotNull(map, "Map must not be null!");
        this.map = map;
    }

    /**
     * Creates map builder with {@link HashMap}.
     */
    public MapBuilder() {
        this(new HashMap<K, V>());
    }

    /**
     * Put key/value pair.
     *
     * @param key key
     * @param value value
     * @return this buider
     */
    public MapBuilder<K, V> put(K key, V value) {
        map.put(key, value);
        return this;
    }


    /**
     * Build and return underlying map.
     *
     * @return Map underlying map.
     */
    public Map<K, V> build() {
        return map;
    }
}
