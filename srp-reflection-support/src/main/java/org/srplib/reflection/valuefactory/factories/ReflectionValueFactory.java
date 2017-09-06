package org.srplib.reflection.valuefactory.factories;

import org.objenesis.ObjenesisHelper;
import org.srplib.reflection.valuefactory.TypeMeta;
import org.srplib.reflection.valuefactory.ValueFactory;

/**
 * @author Anton Pechinsky
 */
public class ReflectionValueFactory<T> implements ValueFactory<T> {

    @Override
    public T get(TypeMeta meta) {
        Class<?> type = meta.getType();
        return (T)ObjenesisHelper.newInstance(type);
//        return (T)ReflectionUtils.newInstance(meta.getType());
    }
}
