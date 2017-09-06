package org.srplib.reflection.valuefactory.factories;

import java.lang.reflect.Array;

import org.srplib.reflection.objectfactory.ClassGraphFactory;
import org.srplib.reflection.objectfactory.ConfigurableNodeValueFactory;
import org.srplib.reflection.objectfactory.NodeValueFactory;
import org.srplib.reflection.valuefactory.CompositeTypeMeta;
import org.srplib.reflection.valuefactory.NonDefaultValueFactory;
import org.srplib.reflection.valuefactory.TypeMeta;
import org.srplib.reflection.valuefactory.ValueFactory;

/**
 * @author Anton Pechinsky
 */
public class ArrayValueFactory<T> implements ValueFactory<T> {

    private static final int ELEMENT_COUNT = 2;

    @Override
    public T get(TypeMeta meta) {
        Class<?> componentType = meta.getType().getComponentType();

        T array = (T) Array.newInstance(componentType, ELEMENT_COUNT);

        createElements(array, meta);

        return array;
    }

    private void createElements(T array, TypeMeta meta) {

        NodeValueFactory nodeValueFactory = meta instanceof CompositeTypeMeta
            ? ((CompositeTypeMeta)meta).getNodeValueFactory()
            : new ConfigurableNodeValueFactory(new NonDefaultValueFactory());

        for (int i = 0; i < ELEMENT_COUNT; i++) {

            Object element = ClassGraphFactory.newInstance(meta.getType().getComponentType(), nodeValueFactory);

            Array.set(array, i, element);
        }
    }
}
