package org.srplib.reflection.valuefactory;

import org.srplib.reflection.objectfactory.NodeValueFactory;

/**
 * @author Anton Pechinsky
 */
public class CompositeTypeMeta extends TypeMeta {

    private final NodeValueFactory nodeValueFactory;

    public CompositeTypeMeta(Class<?> type, NodeValueFactory nodeValueFactory) {
        super(type);
        this.nodeValueFactory = nodeValueFactory;
    }

    public NodeValueFactory getNodeValueFactory() {
        return nodeValueFactory;
    }
}
