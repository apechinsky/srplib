package org.srplib.reflection.deepcompare;

import java.util.ArrayList;
import java.util.List;

import org.srplib.visitor.NodePath;

/**
 *
 * @author Anton Pechinsky
 */
public class DeepComparatorContextImpl implements DeepComparatorContext {

    private final DeepComparator rootComparator;

    private final NodePath<String> path;

    private List<String> mismatches;

    private DeepComparatorContextImpl(DeepComparator rootComparator, NodePath<String> root, List<String> mismatches) {
        this.rootComparator = rootComparator;
        this.path = root;
        this.mismatches = mismatches;
    }

    public DeepComparatorContextImpl(DeepComparator rootComparator) {
        this(rootComparator, new NodePath<>("root"), new ArrayList<>());
    }

    private void compare(Object object1, Object object2, NodePath<String> path) {
        rootComparator.compare(object1, object2, new DeepComparatorContextImpl(rootComparator, path, mismatches));
    }

    public void compareNested(Object object1, Object object2, String node) {
        compare(object1, object2, path.add(node));
    }

    public void compare(Object object1, Object object2) {
        compare(object1, object2, path);
    }

    public void registerMismatch(String message) {
        String mismatch = String.format("Mismatch at path '%s'. %s", path.format("."), message);
        mismatches.add(mismatch);
    }

    public List<String> getMismatches() {
        return mismatches;
    }

    public String toStringMismatch() {
        StringBuffer sb = new StringBuffer();
        for (String mismatch : mismatches) {
            sb.append(mismatch).append("\n");
        }
        return sb.toString();
    }

}
