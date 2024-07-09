package org.srplib.conversion.service;

import java.io.IOException;

import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.srplib.conversion.Converter;
import org.srplib.conversion.registry.SupertypeConverterRegistry;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.srplib.conversion.service.NamedConverter.converter;

/**
 * {@link SupertypeConverterRegistryTest} test.
 */
public class SupertypeConverterRegistryTest {

    private final SupertypeConverterRegistry registry;

    public SupertypeConverterRegistryTest() {
        registry = new SupertypeConverterRegistry();
        registry.add(Object.class, Object.class, converter("ObjectObject"));
        registry.add(Exception.class, Exception.class, converter("ExceptionException"));
        registry.add(RuntimeException.class, Exception.class, converter("RuntimeExceptionException"));
        registry.add(RuntimeException.class, Throwable.class, converter("RuntimeExceptionThrowable"));
    }

    @Test
    public void findExact() throws Exception {
        Converter found = registry.find(RuntimeException.class, Exception.class);

        MatcherAssert.assertThat(found, is(converter("RuntimeExceptionException")));
    }

    @Test
    public void findInputSupertype() throws Exception {
        Converter found = registry.find(IOException.class, Exception.class);

        MatcherAssert.assertThat(found, is(converter("ExceptionException")));
    }

    @Test
    public void findMissing() throws Exception {
        Converter found = registry.find(String.class, Integer.class);

        MatcherAssert.assertThat(found, nullValue());
    }

}
