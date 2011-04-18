package org.srplib.binding.support;

import org.srplib.binding.AbstractValueModel;
import org.srplib.binding.BeanUtils;
import org.srplib.binding.ContextDependentValueModel;

import org.srplib.contract.Assert;

/**
 * A value model adapter for java bean property.
 *
 * <p>Note that this is model is context dependent (see. {@link org.srplib.binding.ContextDependentValueModel}</p>
 *
 * @author Anton Pechinsky
 */
public class PropertyValueAdapter<T> extends AbstractValueModel<T> implements ContextDependentValueModel<Object, T> {

    private String property;

    private Object context;

    public static <T> PropertyValueAdapter<T> newFromBean(Class<?> beanClass, String property) {
        Class<T> propertyType = BeanUtils.getFieldType(beanClass, property);
        return new PropertyValueAdapter<T>(property, propertyType);
    }

    public PropertyValueAdapter(String property, Class<T> type) {
        super(type);
        this.property = property;
    }

    @Override
    public void setValue(T value) {
        Assert.checkNotNull(context, "Context object isn't set!");
        BeanUtils.setField(context, property, value);
    }

    @Override
    public T getValue() {
        Assert.checkNotNull(context, "Context object isn't set!");
        return (T) BeanUtils.getField(context, property);
    }

    @Override
    public void setContext(Object context) {
        this.context = context;
    }

}
