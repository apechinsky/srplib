package org.srplib.reflection.classgraph;

import java.lang.reflect.Field;

import org.srplib.contract.Argument;
import org.srplib.reflection.ReflectionUtils;
import org.srplib.visitor.Element;
import org.srplib.support.Predicate;
import org.srplib.visitor.NodePath;

/**
 * Encapsulates java class graph navigation algorithm.
 *
 * @author Anton Pechinsky
 */
public class ClassGraph<N extends ClassGraphNode, V extends ClassGraphVisitor<N>> implements Element<N, V> {

    private Class root;

    private Predicate<ClassGraphNode> filter;

    /**
     * Creates object graph with specified root class and node filter.
     *
     * @param root Object root object.
     * @param filter Predicate node filter
     */
    public ClassGraph(Class root, Predicate<ClassGraphNode> filter) {
        Argument.checkNotNull(root, "root must not be null!");
        Argument.checkNotNull(filter, "filter must not be null!");

        this.root = root;
        this.filter = filter;

    }

    /**
     * Creates object graph with specified root class and default node filter.
     *
     * @param root Object root object.
     */
    public ClassGraph(Class root) {
        this(root, new TraversableClassesFilter());
    }

    @Override
    public void accept(V visitor) {
        N node = visitor.resolveNode(ClassGraphNode.create(root));

        traverse(new NodePath<>(node), visitor);
    }

    private void traverse(NodePath<N> path, V visitor) {
        visitor.visit(path);

        if (!isTraversable(path)) {
            return;
        }

        ClassGraphNode node = path.getCurrent();

        for (Field field : ReflectionUtils.getFieldsRecursively(node.getType())) {
            N newNode = visitor.resolveNode(ClassGraphNode.create(field));

            traverse(path.add(newNode), visitor);
        }
    }

    private boolean isTraversable(NodePath<N> path) {
        return filter.test(path.getCurrent());
    }
}
