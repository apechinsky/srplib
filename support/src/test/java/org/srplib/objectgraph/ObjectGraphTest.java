package org.srplib.objectgraph;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.srplib.contract.Assert;
import org.srplib.objectgraph.ObjectGraph;

/**
 *
 * @author Anton Pechinsky
 */
public class ObjectGraphTest {

    @Test
    public void testCyclicReferences() throws Exception {
        Person person1 = new Person();
        Person person2 = new Person();

        person1.friend = person2;
        person2.friend = person1;

        ObjectGraph objectGraph = new ObjectGraph(person1);
        objectGraph.accept(new TestVisitor());
    }


    private class Person {

        private Person friend;

    }

    private class TestVisitor implements Visitor {

        private Set visited = new HashSet();


        @Override
        public void visit(Object object) {
            Assert.checkFalse(visited.contains(object), "Object already has been visited [%s]", object);
            visited.add(object);

        }
    }
}
