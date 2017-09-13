package org.srplib.reflection.deepcompare;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import static java.util.Arrays.asList;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.srplib.reflection.classgraph.ClassGraphNode;
import org.srplib.reflection.deepcompare.support.StandardConfiguration;
import org.srplib.reflection.objectfactory.ClassGraphFactory;
import org.srplib.reflection.objectfactory.ConfigurableNodeValueFactory;
import org.srplib.reflection.support.TestObject;
import org.srplib.reflection.valuefactory.CollectionTypeMeta;
import org.srplib.reflection.valuefactory.MapTypeMeta;
import org.srplib.reflection.valuefactory.NonDefaultValueFactory;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.srplib.reflection.deepcompare.support.DeepComparatorMatcher.deepCompare;

/**
 * @author Q-APE
 */
public class ConfigurableDeepComparatorTest {

    private ConfigurableDeepComparator comparator;

    public ConfigurableDeepComparatorTest() {
        comparator = new ConfigurableDeepComparator(new StandardConfiguration());
    }

    @Test
    public void testObjectComparison() throws Exception {
        TestObject expected = generateTestObject();
        TestObject actual = generateTestObject();

        assertThat(actual, deepCompare(expected));
    }

    @Test
    public void nullValues() throws Exception {
        Person expectedNested = new Person("nestedName", null, 23, Arrays.<Person>asList());
        Person expected = new Person("name", null, 23, asList(expectedNested));

        Person actualNested = new Person("nestedName", "actualSurname", 23, null);
        Person actual = new Person("name", "name", 23, asList(actualNested));


        List<String> compare = comparator.compare(expected, actual);


        assertThat(compare, Matchers.hasSize(3));

        assertThat(compare.get(0), is("Mismatch at path 'root.surname'. " +
            "Compare null and non-null values. Expected: 'null' actual: 'name'"));

        assertThat(compare.get(1), is("Mismatch at path 'root.friends.[0].surname'. " +
            "Compare null and non-null values. Expected: 'null' actual: 'actualSurname'"));

        assertThat(compare.get(2), is("Mismatch at path 'root.friends.[0].friends'. " +
            "Compare null and non-null values. Expected: '[]' actual: 'null'"));
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