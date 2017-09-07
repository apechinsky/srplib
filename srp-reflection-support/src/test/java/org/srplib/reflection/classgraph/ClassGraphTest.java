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

}