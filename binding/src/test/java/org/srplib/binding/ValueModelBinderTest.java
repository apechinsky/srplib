package org.srplib.binding;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.srplib.binding.support.PropertyValueAdapter;
import org.srplib.conversion.Converter;
import org.srplib.conversion.ConverterRegistry;
import org.srplib.conversion.IntegerToStringConverter;
import org.srplib.conversion.StringToIntegerConverter;
import org.srplib.conversion.TwoWayConverter;

/**
 * @author Anton Pechinsky
 */
public class ValueModelBinderTest {

    private SourceBean source;

    private TargetBean target;

    private ValueModelBinder binder;

    @Before
    public void setUp() throws Exception {
        source = new SourceBean("hello", 2011, 23);
        target = new TargetBean();
        binder = new ValueModelBinder();
    }

    @Test
    public void testBind() throws Exception {
        binder.addBinding(source.newPropertyModel("s1"), target.newPropertyModel("t1"));
        binder.addBinding(source.newPropertyModel("s2"), target.newPropertyModel("t2"));

        // Bind
        binder.bind(source);

        Assert.assertEquals(source.s1, target.t1);
        Assert.assertEquals(source.s2, target.t2);

        // Unbind
        SourceBean unbound = new SourceBean();
        binder.unbind(unbound);

        Assert.assertEquals(source.s1, unbound.s1);
        Assert.assertEquals(source.s2, unbound.s2);
    }

    @Test
    public void testBindWithConversion() throws Exception {
        ConverterRegistry converterRegistry = new ConverterRegistry();
        converterRegistry.registerConverter(Integer.class, String.class, new IntegerToStringConverter());
        converterRegistry.registerConverter(String.class, Integer.class, new StringToIntegerConverter());

        binder.setConverterRegistry(converterRegistry);

        binder.addBinding(source.newPropertyModel("integer"), target.newPropertyModel("string"));

        // Bind
        binder.bind(source);
        Assert.assertEquals("23", target.string);

        // Unbind
        SourceBean unbound = new SourceBean();
        binder.unbind(unbound);
        Assert.assertEquals(source.integer, unbound.integer);
    }

    @Test
    public void testBindingSpecificConversionOneWay() throws Exception {
        Binding binding = new DefaultBinding(
            source.newPropertyModel("integer"), target.newPropertyModel("string"), new IntegerToStringConverter());

        binder.addBinding(binding);

        binder.bind(source);
        Assert.assertEquals("23", target.string);

        try {
            binder.unbind(new SourceBean());
            Assert.fail("Should throw IllegalStateException because to String to Integer converter");
        }
        catch (IllegalStateException e) {
            // expected exception
        }

    }

    @Test
    public void testBindingSpecificConversionTwoWay() throws Exception {
        Binding binding = new DefaultBinding(
            source.newPropertyModel("integer"), target.newPropertyModel("string"), new IntToStringTwoWayConverter());

        binder.addBinding(binding);

        binder.bind(source);
        Assert.assertEquals("23", target.string);

        // Unbind
        SourceBean unbound = new SourceBean();
        binder.unbind(unbound);
        Assert.assertEquals(source.integer, unbound.integer);

    }

    private static class IntToStringTwoWayConverter implements TwoWayConverter<Integer, String> {

        private Converter<Integer, String> intToStringConverter = new IntegerToStringConverter();
        private Converter<String, Integer> stringToIntConverter = new StringToIntegerConverter();

        @Override
        public String convert(Integer input) {
            return intToStringConverter.convert(input);
        }

        @Override
        public Integer convertBack(String output) {
            return stringToIntConverter.convert(output);
        }
    }

    private class SourceBean extends TestBean {

        private String s1;

        private Integer s2;

        private Integer integer;

        private SourceBean(String s1, Integer s2, Integer integer) {
            this.s1 = s1;
            this.s2 = s2;
            this.integer = integer;
        }

        private SourceBean() {
        }
    }

    private class TargetBean extends TestBean {

        private String t1;

        private Integer t2;

        private String string;

    }

    private class TestBean {

        public ValueModel newPropertyModel(String property) {
            Class<?> type = BeanUtils.getFieldType(getClass(), property);
            PropertyValueAdapter valueModel = new PropertyValueAdapter(property, type);
            valueModel.setContext(this);
            return valueModel;
        }

    }
}
