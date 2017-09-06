package org.srplib.reflection.objectfactory;

import org.srplib.reflection.classgraph.ClassGraphNode;
import org.srplib.visitor.NodePath;

/**
 * Value objectfactory specific for ClassGraphFactory.
 *
 * <p>Unlike ValueFactory this class accepts a path from root class to current node. This gives implementing classes
 * a chance to use context (field name and parent class) for value generation.</p>
 *
 * @author Anton Pechinsky
 */
public interface NodeValueFactory<T> {

    /**
     * Returns value for specified path.
     *
     * @param path path to current node.
     * @return value
     */
    T get(NodePath<? extends ClassGraphNode> path);

}
