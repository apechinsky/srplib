package org.srplib.reflection.objectfactory;

import java.util.HashMap;
import java.util.Map;

import org.srplib.contract.Utils;
import org.srplib.reflection.classgraph.ClassGraphNode;
import org.srplib.support.CompositeKey;
import org.srplib.reflection.valuefactory.TypeMeta;
import org.srplib.reflection.valuefactory.ValueFactory;
import org.srplib.reflection.valuefactory.ValueFactoryException;
import org.srplib.visitor.NodePath;

/**
 * Configurable implementation of {@link NodeValueFactory} supporting registration of value factories and type metadata.
 *
 * <p>Type metadata is used to provide essential data needed to create objects. For example:
 *  <ul>
 *      <li>implementation class for interface or abstract classes</li>
 *      <li>type information for erasure</li>
 *  </ul>
 *
 * @author Anton Pechinsky
 */
public class ConfigurableNodeValueFactory implements NodeValueFactory<Object> {

    private final Map<CompositeKey, TypeMeta> nodeValueMeta = new HashMap<>();

    private final Map<CompositeKey, ValueFactory<?>> valueFactories = new HashMap<>();

    private final ValueFactory defaultValueFactory;

    public ConfigurableNodeValueFactory(ValueFactory<?> defaultValueFactory) {
        this.defaultValueFactory = defaultValueFactory;
    }

    public void add(ClassGraphNode node, TypeMeta typeMeta) {
        nodeValueMeta.put(getKey(node), typeMeta);
    }

    public void add(ClassGraphNode node, ValueFactory valueFactory) {
        valueFactories.put(getKey(node), valueFactory);
    }


    @Override
    public Object get(NodePath<? extends ClassGraphNode> path) {
        try {
            ClassGraphNode node = path.getCurrent();

            TypeMeta typeMeta = getValueMeta(node);

            ValueFactory valueFactory = getValueFactory(node);

            return valueFactory.get(typeMeta);
        }
        catch (ValueFactoryException e) {
            throw new ValueFactoryException(String.format("Can't create value for node path '%s'", path), e);
        }
    }

    private TypeMeta getValueMeta(ClassGraphNode node) {
        TypeMeta typeMeta = nodeValueMeta.get(getKey(node));

        return Utils.getDefaultIfNull(typeMeta, new TypeMeta(node.getType()));
    }


    private ValueFactory getValueFactory(ClassGraphNode node) {
        ValueFactory valueFactory = valueFactories.get(getKey(node));

        return Utils.getDefaultIfNull(valueFactory, defaultValueFactory);
    }

    private CompositeKey getKey(ClassGraphNode node) {
        String fieldName = node.getField() != null ? node.getField().getName() : null;
        return new CompositeKey(node.getType(), fieldName);
    }

}
