package org.srplib.reflection.valuefactory.factories;

import org.srplib.reflection.valuefactory.ValueFactory;
import org.srplib.reflection.valuefactory.TypeMeta;

/**
 * @author Anton Pechinsky
 */
public class EnumValueFactory<T extends Enum> implements ValueFactory<T> {

    @Override
    public T get(TypeMeta meta) {
        return (T) meta.getType().getEnumConstants()[0];
    }
}
