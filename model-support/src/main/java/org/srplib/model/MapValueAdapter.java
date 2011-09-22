package org.srplib.model;

import java.util.Map;

import org.srplib.contract.Assert;

/**
 * A value model adapter for java bean property.
 *
 * <p>Note that this is model is context dependent (see. {@link org.srplib.model.ContextDependentValueModel}</p>
 *
 * @author Anton Pechinsky
 */
public class MapValueAdapter<K, V> extends AbstractValueModel<V> implements ContextDependentValueModel<Map<K, V>, V> {

    private K key;

    private Map<K, V> map;

    /**
     * Creates value model for specified key of specified class.
     *
     * <p>Before value access methods are invoked this value model need an object (context) whose key will be accessed.</p>
     *
     * <pre>
     *     // create model
     *     MapValueAdapter valueModel = new MapValueAdapter(String.class, "name");
     *     ...
     *     // later this model may be used for specific object
     *     valueModel.setContext(attributesMap);
     * </pre>
     *
     * @param valueClass Class a value class
     * @param key String key name to be accessed.
     */
    public MapValueAdapter(Class<V> valueClass, K key) {
        super(valueClass);
        this.key = key;
    }

    /**
     * Creates value model for specified object.
     *
     * <p>Object is set as context.</p>
     *
     * <p>Call to this method equivalent to:
     * <pre>
     *  MapValueAdapter valueModel = new MapValueAdapter(String.class, "name");
     *  valueModel.setContext(attributesMap);
     * </pre>
     * </p>
     *
     * @param map Map a map to use
     * @param key String key name.
     */
    public MapValueAdapter(Map<K, V> map, K key) {
        this((Class<V>) null, key);
        this.map = map;
    }

    /**
     * Sets value of specified bean property.
     *
     * @param value Object property value
     * @throws IllegalStateException if no context specified.
     */
    @Override
    public void setValue(V value) {
        Assert.checkNotNull(map, "Context object isn't set!");
        map.put(key, value);
    }

    /**
     * Returns value of specified bean property.
     *
     * @return Object property value
     * @throws IllegalStateException if no context specified.
     */
    @Override
    public V getValue() {
        Assert.checkNotNull(map, "Context object isn't set!");
        return map.get(key);
    }

    @Override
    public void setContext(Map<K, V> context) {
        this.map = context;
    }
}
