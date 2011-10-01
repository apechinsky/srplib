package org.srplib.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.srplib.contract.Argument;

/**
 * List builder.
 *
 * <ul>The main purposes of this builder is:
 * <li>simplify list creation and initialization.</li>
 * <li>replace unsafe generic varargs.</li>
 * </ul>
 *
 * <p>List builder contains two types of methods:
 * <ul>
 *     <li>factory methods for direct creation of instances of {@link java.util.Map}</li>
 *     <li>builder methods for {@link java.util.Map} population.</li>
 * </ul>
 * </p>
 *
 * <pre>
 *     // Create and initialize list
 *     List&lt;T&gt; list = ListBuilder.newArrayList()
 *         .add(value1)
 *         .add(value2)
 *         .add(value3)
 *         .build();
 *
 *     // Possible unsafe operation
 *     method(List&lt;T&gt;... lists);
 *     method(list1, list2, list3); // implicit generics array creation!!!
 *
 *     // Replacement (more typing but safe)
 *     method(List&lt;List&lt;T&gt;&gt; lists);
 *     method(ListBuilder.create().add(value1).add(value2).add(value3).build())
 * </pre>
 *
 * @author Anton Pechinsky
 */
public class ListBuilder<T> implements Builder<List<T>> {

    private List<T> list;

    /**
     * An alternative to constructor.
     *
     * @param array an array of initial items
     * @return ListBuilder list builder
     */
    public static <T> List<T> newArrayList(T... array) {
        return new ArrayList<T>(Arrays.asList(array));
    }


    /**
     * An alternative to constructor.
     *
     * @param array an array of initial items
     * @return ListBuilder list builder
     */
    public static <T> List<T> newLinkedList(T... array) {
        return new LinkedList<T>(Arrays.asList(array));
    }


    /**
     * An alternative to constructor.
     *
     * @param list List a list to use
     * @return ListBuilder list builder
     */
    public static <T> ListBuilder<T> list(List<T> list) {
        return new ListBuilder<T>(list);
    }

    /**
     * Creates ListBuilder with underlying ArrayList and add specified vararg parameters.
     *
     * @param array vararg parameter
     * @return ListBuilder list builder
     */
    public static <T> ListBuilder<T> arrayList(T... array) {
        return list(newArrayList(array));
    }

    /**
     * Creates ListBuilder with underlying {@link LinkedList} and add specified vararg parameters.
     *
     * @param array vararg parameter
     * @return ListBuilder list builder
     */
    public static <T> ListBuilder<T> linkedList(T... array) {
        return list(newLinkedList(array));
    }

    /**
     * Creates list builder with specified list.
     *
     * @param list List a list to use
     */
    public ListBuilder(List<T> list) {
        Argument.checkNotNull(list, "List must not be null!");
        this.list = list;
    }

    /**
     * Creates list builder with {@link java.util.ArrayList}.
     */
    public ListBuilder() {
        this(new ArrayList<T>());
    }

    /**
     * Add value to list.
     *
     * @param value value
     * @return this buider
     */
    public ListBuilder<T> add(T value) {
        list.add(value);
        return this;
    }


    /**
     * Build and return underlying list.
     *
     * @return List underlying list.
     */
    public List<T> build() {
        return list;
    }
}
