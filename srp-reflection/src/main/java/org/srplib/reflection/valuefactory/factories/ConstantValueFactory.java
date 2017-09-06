package org.srplib.reflection.valuefactory.factories;

import org.srplib.reflection.valuefactory.ValueFactory;
import org.srplib.reflection.valuefactory.TypeMeta;

/**
 * @author Anton Pechinsky
 */
public class ConstantValueFactory<T> implements ValueFactory<T> {

    private T value;

    public ConstantValueFactory(T value) {
        this.value = value;
    }

    @Override
    public T get(TypeMeta meta) {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
