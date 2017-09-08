package org.srplib.reflection.valuefactory;

import org.srplib.reflection.objectfactory.NodeValueFactory;

/**
 * @author Anton Pechinsky
 */
public class CollectionTypeMeta extends CompositeTypeMeta {

    private final Class<?> elementType;

    public CollectionTypeMeta(Class<?> type, Class<?> elementType, NodeValueFactory nodeValueFactory) {
        super(type, nodeValueFactory);
        this.elementType = elementType;
    }

    public Class<?> getElementType() {
        return elementType;
    }

    @Override
    public String toString() {
        return String.format("CollectionTypeMeta(type:%s, item:%s)", getType(), getElementType());
    }
}
