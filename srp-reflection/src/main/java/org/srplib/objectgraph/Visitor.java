package org.srplib.objectgraph;

/**
 * Generic visitor interface.
 *
 * @author Anton Pechinsky
 */
public interface Visitor {

    /**
     * Method is called for each object of visited structure.
     *
     * @param object Object structure node.
     */
    void visit(Object object);

}
