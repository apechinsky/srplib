package org.srplib.reflection.valuefactory.factories;

import java.util.Collection;

import org.srplib.reflection.ReflectionUtils;
import org.srplib.reflection.objectfactory.ClassGraphFactory;
import org.srplib.reflection.valuefactory.CollectionTypeMeta;
import org.srplib.reflection.valuefactory.TypeMeta;
import org.srplib.reflection.valuefactory.ValueFactory;

/**
 * @author Anton Pechinsky
 */
public class CollectionValueFactory<T extends Collection> implements ValueFactory<T> {

    private static final int ELEMENT_COUNT = 2;

    @Override
    public T get(TypeMeta meta) {

        T collection = (T) ReflectionUtils.newInstance(meta.getInstantiableType());

        if (meta instanceof CollectionTypeMeta) {
            createElements(collection, (CollectionTypeMeta) meta);
        }

        return collection;
    }

    private void createElements(T collection, CollectionTypeMeta meta) {

        for (int i = 0; i < ELEMENT_COUNT; i++) {

            Object element = ClassGraphFactory.newInstance(meta.getElementType(), meta.getNodeValueFactory());

            collection.add(element);

        }
    }

}
