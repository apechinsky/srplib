package org.srplib.reflection.objectgraph;

/**
 * Generic visitor interface.
 *
 * @author Anton Pechinsky
 * @deprecated use org.srplib.visitor package
 */
@Deprecated
public interface Visitor {

    /**
     * Method is called for each object of visited structure.
     *
     * @param object Object structure node.
     */
    void visit(Object object);

}
