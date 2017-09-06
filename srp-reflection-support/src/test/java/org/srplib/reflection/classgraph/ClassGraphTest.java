package org.srplib.reflection.classgraph;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;
import org.srplib.reflection.deepcompare.ConfigurableDeepComparator;
import org.srplib.reflection.deepcompare.support.StandardComparators;
import org.srplib.reflection.objectfactory.ClassGraphFactory;
import org.srplib.reflection.objectfactory.ConfigurableNodeValueFactory;
import org.srplib.reflection.valuefactory.CollectionTypeMeta;
import org.srplib.reflection.valuefactory.MapTypeMeta;
import org.srplib.reflection.valuefactory.NonDefaultValueFactory;

/**
 * @author Q-APE
 */
public class ClassGraphTest {

    @Test
    public void testPrint() throws Exception {
        ClassGraph<ClassGraphNode, ClassGraphVisitor<ClassGraphNode>> graph = new ClassGraph<>(TestObject.class);
        graph.accept(new PrintClassGraphVisitor());
    }

    @Test
    public void testCreate() throws Exception {

        ConfigurableNodeValueFactory nodeValueFactory = new ConfigurableNodeValueFactory(new NonDefaultValueFactory());

        nodeValueFactory.add(ClassGraphNode.create(TestObject.class, "listField"),
            new CollectionTypeMeta(ArrayList.class, String.class, nodeValueFactory));

        nodeValueFactory.add(ClassGraphNode.create(TestObject.class, "mapField"),
            new MapTypeMeta(HashMap.class, String.class, Integer.class, nodeValueFactory));

        TestObject result1 = ClassGraphFactory.newInstance(TestObject.class, nodeValueFactory);
        TestObject result2 = ClassGraphFactory.newInstance(TestObject.class, nodeValueFactory);

        new ConfigurableDeepComparator(new StandardComparators()).compare(result1, result2);
    }



    @Test
    public void testCompare() throws Exception {

    }


}