package org.srplib.visitor;

/**
 * @author Anton Pechinsky
 */
public interface Element<N, V extends Visitor<N>> {

    void accept(V visitor);

}
