package org.srplib.reflection.objectfactory;

import java.lang.reflect.Field;

import org.srplib.reflection.classgraph.ClassGraphNode;

/**
 * An extension of {@link ClassGraphFactoryNode} with additional property: an object.
 *
 * <p>Used by {@link ClassGraphFactory} to keep node instances.</p>
 *
 * @author Anton Pechinsky
 */
class ClassGraphFactoryNode extends ClassGraphNode {

    private Object object;

    ClassGraphFactoryNode(Class type, Field field) {
        super(type, field);
    }

    Object getObject() {
        return object;
    }

    void setObject(Object object) {
        this.object = object;
    }

}
