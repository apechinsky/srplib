package org.srplib.reflection.valuefactory.factories;

import org.srplib.reflection.valuefactory.TypeMeta;
import org.srplib.reflection.valuefactory.ValueFactory;

/**
 * @author Anton Pechinsky
 */
public class StringValueFactory implements ValueFactory<String> {

    public String initial;

    public static ValueFactory<String> empty() {
        return new ConstantValueFactory<>(new String(""));
    }


    public StringValueFactory(String initial) {
        this.initial = initial;
    }

    @Override
    public String get(TypeMeta meta) {

        return new String(initial);
    }

}
