package org.srplib.reflection.valuefactory.factories;

import java.util.Map;

import org.srplib.reflection.ReflectionUtils;
import org.srplib.reflection.objectfactory.ClassGraphFactory;
import org.srplib.reflection.valuefactory.MapTypeMeta;
import org.srplib.reflection.valuefactory.TypeMeta;
import org.srplib.reflection.valuefactory.ValueFactory;

/**
 * @author Anton Pechinsky
 */
public class MapValueFactory<T extends Map> implements ValueFactory<T> {

    private static final int ELEMENT_COUNT = 2;

    @Override
    public T get(TypeMeta meta) {

        T map = (T) ReflectionUtils.newInstance(meta.getInstantiableType());

        if (meta instanceof MapTypeMeta) {
            createEntries(map, (MapTypeMeta) meta);
        }


        return map;
    }

    private void createEntries(T map, MapTypeMeta meta) {

        for (int i = 0; i < ELEMENT_COUNT; i++) {
            Object key = ClassGraphFactory.newInstance(meta.getKeyType(), meta.getNodeValueFactory());
            Object value = ClassGraphFactory.newInstance(meta.getValueType(), meta.getNodeValueFactory());

            map.put(key, value);
        }
    }
}
