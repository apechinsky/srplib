package org.srplib.reflection.valuefactory;

import org.srplib.reflection.objectfactory.NodeValueFactory;

/**
 * @author Anton Pechinsky
 */
public class MapTypeMeta extends CompositeTypeMeta {

    private final Class<?> keyType;

    private final Class<?> valueType;

    public MapTypeMeta(Class<?> type, Class<?> keyType, Class<?> valueType, NodeValueFactory nodeValueFactory) {
        super(type, nodeValueFactory);
        this.keyType = keyType;
        this.valueType = valueType;
    }

    public Class<?> getKeyType() {
        return keyType;
    }

    public Class<?> getValueType() {
        return valueType;
    }
}
