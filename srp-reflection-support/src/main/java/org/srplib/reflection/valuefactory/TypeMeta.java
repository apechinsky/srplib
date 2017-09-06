package org.srplib.reflection.valuefactory;

import java.lang.reflect.Modifier;
import java.util.Objects;

/**
 * @author Anton Pechinsky
 */
public class TypeMeta {

    private final Class<?> type;

    public TypeMeta(Class<?> type) {
        this.type = type;
    }

    public Class<?> getType() {
        return type;
    }

    public Class<?> getInstantiableType() {
        checkInstantiable(type);

        return type;
    }

    public static void checkInstantiable(Class<?> type) {
        if (!isInstantiable(type)) {
            throw new ValueFactoryException(String.format("Type %s is not instantiable!", type));
        }
    }

    public static boolean isInstantiable(Class<?> type) {
        return !type.isInterface() && !Modifier.isAbstract(type.getModifiers());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TypeMeta typeMeta = (TypeMeta) o;
        return Objects.equals(type, typeMeta.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
