package org.srplib.reflection.classgraph;

import org.junit.Test;
import org.srplib.reflection.support.TestObject;

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