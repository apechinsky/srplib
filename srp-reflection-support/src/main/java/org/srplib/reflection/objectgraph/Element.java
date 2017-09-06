package org.srplib.reflection.objectgraph;

/**
 * An interface of visitable structure in Visitor pattern.
 *
 * @author Anton Pechinsky
 */
public interface Element {

    /**
     * Implementing classes should call this method on each node of encapsulated structure.
     *
     * @param visitor Visitor visitor
     */
    void accept(Visitor visitor);

}
