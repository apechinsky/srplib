package org.srplib.reflection.deepcompare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.srplib.visitor.NodePath;

/**
 * Implementation of deep comparator context.
 *
 * <ul>
 *     <li>Maintains a reference ot root comparator.</li>
 *
 *     <li>Maintains a path from root object to current node. Path is used to provide meaningful diagnostic message
 *     (e.g. "Mismatch at path 'root.person.address.[2].street ....")
 *     </li>
 *
 *     <li>Maintains list of mismatch messages</li>
 * </ul>
 *
 * @author Anton Pechinsky
 */
public class DeepComparatorContextImpl implements DeepComparatorContext {

    private final DeepComparator rootComparator;

    private final NodePath<String> path;

    private List<String> mismatches;

    /**
     * Constructor.
     *
     * <p>Root comparator have a knowledge of configuration and maintains a set of converters. Root comparator
     * supports recursive nature of deep object comparison.</p>
     *
     * @param rootComparator DeepComparator root comparator.
     */
    public DeepComparatorContextImpl(DeepComparator rootComparator) {
        this(rootComparator, new NodePath<>("root"), new ArrayList<String>());
    }

    /**
     * Constructor.
     *
     * <p>This constructor is used internally.</p>
     *
     * @param rootComparator DeepComparator root comparator
     * @param root NodePath a path from root to current node
     * @param mismatches List a list of mismatches
     */
    private DeepComparatorContextImpl(DeepComparator rootComparator, NodePath<String> root, List<String> mismatches) {
        this.rootComparator = rootComparator;
        this.path = root;
        this.mismatches = mismatches;
    }

    /**
     * Compares specified object pair with current node tag.
     *
     * <p>Used by implementations of {@link DeepComparator} to compare nested objects.</p>
     *
     * @param object1 first object
     * @param object2 second object
     * @param node String an arbitrary name of node. This may be a field name, collection index or some other info helping
     * to build a path from root object to current node.
     */
    public void compareNested(Object object1, Object object2, String node) {
        compare(object1, object2, path.add(node));
    }

    private void compare(Object object1, Object object2, NodePath<String> path) {
        rootComparator.compare(object1, object2, new DeepComparatorContextImpl(rootComparator, path, mismatches));
    }

    /**
     * Compares specified object pair.
     *
     * <p>A version of method {@link #compareNested} without node information.</p>
     *
     * @param object1 first object
     * @param object2 second object
     */
    public void compare(Object object1, Object object2) {
        compare(object1, object2, path);
    }

    /**
     * Registers comparison mismatch.
     *
     * <p>Used by implementations of {@link DeepComparator} to register differences.</p>
     *
     * @param pattern String mismatch message pattern.
     * @param arguments String mismatch message arguments.
     */
    public void registerMismatch(String pattern, Object... arguments) {
        String providedMessage = String.format(pattern, arguments);

        String contextMessage = String.format("Mismatch at path '%s'. %s", path.format("."), providedMessage);

        mismatches.add(contextMessage);
    }

    /**
     * Returns of list of mismatches.
     *
     * @return list of strings
     */
    public List<String> getMismatches() {
        return Collections.unmodifiableList(mismatches);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (String mismatch : mismatches) {
            sb.append(mismatch).append("\n");
        }
        return sb.toString();
    }

}
