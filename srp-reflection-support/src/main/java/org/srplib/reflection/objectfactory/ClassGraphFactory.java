package org.srplib.reflection.objectfactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.srplib.reflection.ReflectionUtils;
import org.srplib.reflection.classgraph.ClassGraph;
import org.srplib.reflection.classgraph.ClassGraphVisitor;
import org.srplib.visitor.NodePath;

/**
 * Glass graph visitor which creates an object graph for class is being visited.
 *
 * <p>Value creation strategy is defined by {@link NodeValueFactory} implementation.</p>
 *
 * How it works.
 * <ul>
 *     <li>Creates an instance of class on each invocation of {@link #visit} method</li>
 *
 *     <li>Remembers root object</li>
 *
 *     <li>assigns value of created instance to corresponding property of parent class. This is possible because
 *     ClassGraph provides a path ({@link NodePath}) from root to current node.</li>
 *
 *     <li>Created object is accessible via {@link #getResult()} method.</li>
 * </ul>
 *
 * Usage:
 * <pre>{@code
 *  ClassGraphFactory visitor = new ClassGraphFactory(valueFactory);
 *  new ClassGraph(ClassToCreate.class).accept(visitor);
 *  ClassToCreate instance = visitor.getResult();
 * }
 * </pre>
 *
 * @author Anton Pechinsky
 */
public class ClassGraphFactory implements ClassGraphVisitor<ClassGraphFactoryNode> {

    private ClassGraphFactoryNode root;

    private NodeValueFactory valueFactory;

    /**
     * Creates an object of specified class.
     *
     * <p>A one line method for creating object instance.</p>
     *
     * @param type Class a class to create
     * @param valueFactory NodeValueFactory class graph node value objectfactory strategy
     * @return created instance
     */
    public static <T> T newInstance(Class<T> type, NodeValueFactory valueFactory) {
        ClassGraphFactory visitor = new ClassGraphFactory(valueFactory);

        new ClassGraph(type).accept(visitor);

        return visitor.getResult();
    }

    /**
     * Constructor
     *
     * @param valueFactory NodeValueFactory class graph node value objectfactory strategy
     */
    public ClassGraphFactory(NodeValueFactory valueFactory) {
        this.valueFactory = valueFactory;
    }

    /**
     * Returns created object.
     *
     * @return resulting instance
     */
    public <T> T getResult() {
        return (T) root.getObject();
    }

    @Override
    public void visit(NodePath<ClassGraphFactoryNode> path) {
        ClassGraphFactoryNode current = path.getCurrent();

        rememberIfRootNode(current);

        if (!shouldCreateInstance(current)) {
            return;
        }

        Object value = valueFactory.get(path);

        current.setObject(value);

        if (path.hasParent()) {
            Field field = current.getField();
            ReflectionUtils.setFieldValue(path.getParent().getCurrent().getObject(), field, value);
        }
    }

    private void rememberIfRootNode(ClassGraphFactoryNode node) {
        if (node.isRoot()) {
            this.root = node;
        }
    }

    private boolean shouldCreateInstance(ClassGraphFactoryNode node) {
        return node.isRoot() || isAssignableField(node);
    }

    private boolean isAssignableField(ClassGraphFactoryNode node) {
        return !Modifier.isStatic(node.getField().getModifiers());
    }

    @Override
    public ClassGraphFactoryNode resolveNode(org.srplib.reflection.classgraph.ClassGraphNode node) {
        return new ClassGraphFactoryNode(node.getType(), node.getField());
    }

}


