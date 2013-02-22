package org.srplib.objectgraph;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.core.Is.is;

/**
 * Test for ObjectGraph.
 *
 * @author Anton Pechinsky
 */
public class ObjectGraphTest {

    private ObjectTracker tracker;

    @Before
    public void setUp() throws Exception {
        this.tracker = new ObjectTracker();
    }

    @Test
    public void testTraversal() throws Exception {
        TestObject testObject = new TestObject();

        ObjectTrackerVisitor trackerVisitor = new ObjectTrackerVisitor();
        new ObjectGraph(testObject).accept(trackerVisitor);

        Assert.assertThat(trackerVisitor.getTracker().objects, is(tracker.objects));
    }


    @Test
    public void testCyclicReferences() throws Exception {
        Person person1 = new Person();
        Person person2 = new Person();

        person1.friend = person2;
        person2.friend = person1;

        ObjectGraph objectGraph = new ObjectGraph(person1);
        objectGraph.accept(new ObjectTrackerVisitor());
    }


    private class Person {

        private Person friend;

    }

    /**
     * ObjectGraph test object.
     *
     * <p>Registers traversable objects for later verification.</p>
     */
    private class TestObject {

        private int intValue = 123;

        private String stringValue = "111";

        private String[] stringArray = { "1", "2", "3" };

        private Set<String> set = new HashSet<String>(Arrays.<String>asList("5", "6", "7"));

        private TestObject() {
            tracker.track(this);
            tracker.track(stringArray);
            tracker.track(set);
        }
    }


    private class ObjectTrackerVisitor implements Visitor {

        private ObjectTracker tracker = new ObjectTracker();

        @Override
        public void visit(Object object) {
            tracker.track(object);
        }

        public ObjectTracker getTracker() {
            return tracker;
        }
    }


    private class ObjectTracker {

        private Set objects = new HashSet();

        public <T> T track(T value) {
            org.srplib.contract.Assert.checkFalse(objects.contains(value), "Object already has been tracked [%s]", value);

            objects.add(value);

            return value;
        }
    }
}
