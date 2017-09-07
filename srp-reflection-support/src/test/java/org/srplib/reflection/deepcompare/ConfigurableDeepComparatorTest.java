package org.srplib.reflection.deepcompare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.srplib.reflection.classgraph.ClassGraphNode;
import org.srplib.reflection.classgraph.TestObject;
import org.srplib.reflection.objectfactory.ClassGraphFactory;
import org.srplib.reflection.objectfactory.ConfigurableNodeValueFactory;
import org.srplib.reflection.valuefactory.CollectionTypeMeta;
import org.srplib.reflection.valuefactory.MapTypeMeta;
import org.srplib.reflection.valuefactory.NonDefaultValueFactory;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.srplib.reflection.deepcompare.support.DeepComparatorMatcher.deepCompare;

/**
 * @author Q-APE
 */
public class ConfigurableDeepComparatorTest {

    @Test
    public void testObjectComparison() throws Exception {

        TestObject expected = generateTestObject();
        TestObject actual = generateTestObject();

        assertThat(expected, deepCompare(actual));
    }


    @Test
    public void cyclicDependencies() throws Exception {
        List<Person> friends1 = new ArrayList<>();
        Person person1 = new Person("name", "surname", 23, friends1);
        friends1.add(person1);

        List<Person> friends2 = new ArrayList<>();
        Person person2 = new Person("name", "surname", 23, friends2);
        friends2.add(person2);

        assertThat(person1, deepCompare(person2));
    }

    @Test
    public void rememberProcessedShouldHandleInternedStrings() throws Exception {
        TwoStrings expected = new TwoStrings("s1", "s1");
        TwoStrings actual = new TwoStrings("s1", "s2");

        assertThat(actual, not(deepCompare(expected)));
    }

    public static TestObject generateTestObject() {
        ConfigurableNodeValueFactory nodeValueFactory = new ConfigurableNodeValueFactory(new NonDefaultValueFactory());

        nodeValueFactory.add(ClassGraphNode.create(TestObject.class, "listField"),
            new CollectionTypeMeta(ArrayList.class, String.class, nodeValueFactory));

        nodeValueFactory.add(ClassGraphNode.create(TestObject.class, "mapField"),
            new MapTypeMeta(HashMap.class, String.class, Integer.class, nodeValueFactory));

        return ClassGraphFactory.newInstance(TestObject.class, nodeValueFactory);
    }

    private class TwoStrings {

        String string1;

        String string2;

        public TwoStrings(String string1, String string2) {
            this.string1 = string1;
            this.string2 = string2;
        }
    }

    private class Person {

        String name;

        String surname;

        int age;

        List<Person> friends;

        public Person(String name, String surname, int age, List<Person> friends) {
            this.name = name;
            this.surname = surname;
            this.age = age;
            this.friends = friends;
        }
    }
}