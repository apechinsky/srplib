package org.srplib.conversion.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.srplib.conversion.Converter;
import org.srplib.conversion.ConverterConfigurer;
import org.srplib.conversion.ConverterException;
import org.srplib.conversion.ConverterRegistry;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Matchers.any;

/**
 * {@link ConversionServiceImpl} test
 */
public class ConversionServiceImplTest {

    private ConverterConfigurer configurer;

    private ConverterRegistry registry;

    private ConversionServiceImpl service;

    @Before
    public void setUp() throws Exception {
        configurer = Mockito.mock(ConverterConfigurer.class);
        registry = Mockito.mock(ConverterRegistry.class);
        service = new ConversionServiceImpl(configurer, registry);
    }

    @Test
    public void nullConvertedToNull() throws Exception {
        Object converted = service.convert(null);
        assertThat(converted, nullValue());
    }

    @Test
    public void sameTypeConversionReturnsValueAsIs() {
        Object value = new Object();
        Object converted = service.convert(Object.class, Object.class, value);
        assertThat(converted, sameInstance(value));
    }

    @Test
    public void converterRegistry() {
        String source = "value";
        Integer expected = 55;

        Converter<String, Integer> converter = Mockito.mock(Converter.class);
        Mockito.when(converter.convert(source)).thenReturn(expected);

        Mockito.when(registry.find(String.class, Integer.class)).thenReturn(converter);

        Object converted = service.convert(String.class, Integer.class, source);

        assertThat(converted, is(expected));
        Mockito.verify(converter).convert(source);
    }

    @Test(expected = ConverterException.class)
    public void exceptionIfNoConverter() {
        Mockito.when(registry.find(any(), any())).thenReturn(null);
        service.convert("value");
    }
}


