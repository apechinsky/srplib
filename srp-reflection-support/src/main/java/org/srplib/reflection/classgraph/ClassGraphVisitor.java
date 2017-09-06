package org.srplib.reflection.classgraph;

import org.srplib.visitor.Visitor;

/**
 * Specialization of Visitor for ClassGraph object.
 *
 * @author Anton Pechinsky
 */
public interface ClassGraphVisitor<T extends ClassGraphNode> extends Visitor<T> {

    /**
     * Method is called for each node of visited structure.
     *
     * <p>Implementing classes may replace default node with custom implementation (e.g. having addidional attributes).</p>
     *
     * @param node ClassGraphNode default class graph node.
     */
    T resolveNode(ClassGraphNode node);

}
