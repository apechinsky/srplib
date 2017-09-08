package org.srplib.reflection.objectfactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.srplib.reflection.classgraph.ClassGraphNode;
import org.srplib.reflection.valuefactory.TypeMeta;
import org.srplib.reflection.valuefactory.ValueFactory;
import org.srplib.visitor.NodePath;

/**
 * @author Q-APE
 */
@RunWith(MockitoJUnitRunner.class)
public class ConfigurableNodeValueFactoryTest {

    @Mock
    private ValueFactory valueFactory;

    @Test
    public void testOverrideTypeMeta() throws Exception {
        ClassGraphNode parentNode = ClassGraphNode.create(Parent.class, "name");
        TypeMeta parentNodeMeta = new TypeMeta(Integer.class);

        ConfigurableNodeValueFactory nodeValueFactory = new ConfigurableNodeValueFactory(valueFactory);
        nodeValueFactory.add(parentNode, parentNodeMeta);

        nodeValueFactory.get(new NodePath<>(parentNode));
        Mockito.verify(valueFactory).get(parentNodeMeta);
    }

    @Test
    public void testOverrideTypeMetaNameCollision() throws Exception {
        ClassGraphNode parentNode = ClassGraphNode.create(Parent.class, "name");
        ClassGraphNode childNode = ClassGraphNode.create(Child.class, "name");
        TypeMeta parentNodeMeta = new TypeMeta(Integer.class);
        TypeMeta childNodeMeta = new TypeMeta(Boolean.class);

        ConfigurableNodeValueFactory nodeValueFactory = new ConfigurableNodeValueFactory(valueFactory);
        nodeValueFactory.add(parentNode, parentNodeMeta);
        nodeValueFactory.add(childNode, childNodeMeta);

        nodeValueFactory.get(new NodePath<>(parentNode));
        Mockito.verify(valueFactory).get(parentNodeMeta);

        nodeValueFactory.get(new NodePath<>(childNode));
        Mockito.verify(valueFactory).get(childNodeMeta);
    }

    private class Parent {

        private String name;

        private String description;

    }

    private class Child {

        private String name;

    }
}