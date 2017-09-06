package org.srplib.reflection.valuefactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.srplib.reflection.valuefactory.factories.ArrayValueFactory;
import org.srplib.reflection.valuefactory.factories.CollectionValueFactory;
import org.srplib.reflection.valuefactory.factories.DateValueFactory;
import org.srplib.reflection.valuefactory.factories.EnumValueFactory;
import org.srplib.reflection.valuefactory.factories.MapValueFactory;
import org.srplib.reflection.valuefactory.factories.ReflectionValueFactory;
import static org.hamcrest.object.IsCompatibleType.typeCompatibleWith;
import static org.srplib.reflection.valuefactory.factories.IsArrayMatcher.isArray;

/**
 * Universal value object factory creating primitive/wrapper classes with non-default values.
 *
 * @author Anton Pechinsky
 */
public class NonDefaultValueFactory implements ValueFactory {

    private final ValueFactory delegate;

    public NonDefaultValueFactory() {

        delegate = ConfigurableValueFactory.Builder.create()

            .register(boolean.class, true)
            .register(byte.class, (byte) 1)
            .register(short.class, (short) 1)
            .register(char.class, 'a')
            .register(int.class, 1)
            .register(long.class, 1L)
            .register(float.class, 1f)
            .register(double.class, 1d)

            .register(Boolean.class, true)
            .register(Byte.class, (byte) 1)
            .register(Character.class, 'a')
            .register(Short.class, (short) 1)
            .register(Integer.class, 1)
            .register(Long.class, 1L)
            .register(Float.class, 1f)
            .register(Double.class, 1d)
            .register(String.class, "string")

            .register(BigDecimal.class, BigDecimal.ONE)

            .register(BigInteger.class, BigInteger.ONE)

            .register(Date.class, new DateValueFactory())

            .register(typeCompatibleWith(Map.class), new MapValueFactory())

            .register(typeCompatibleWith(Collection.class), new CollectionValueFactory())

            .register(typeCompatibleWith(Enum.class), new EnumValueFactory())

            .register(isArray(), new ArrayValueFactory())

            .register(typeCompatibleWith(Object.class), new ReflectionValueFactory<>())

            .build();
    }

    @Override
    public Object get(TypeMeta meta) {
        return delegate.get(meta);
    }
}
