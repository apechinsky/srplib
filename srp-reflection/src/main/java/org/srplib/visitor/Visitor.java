package org.srplib.visitor;

/**
 * @author Anton Pechinsky
 */
public interface Visitor<T> {

    void visit(NodePath<T> element);

}
