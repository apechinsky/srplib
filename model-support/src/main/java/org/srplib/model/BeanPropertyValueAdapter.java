package org.srplib.model;

import org.srplib.contract.Assert;
import org.srplib.support.BeanUtils;

/**
 * A value model adapter for java bean property.
 *
 * <p>Note that this is model is context dependent (see. {@link ContextDependentValueModel}</p>
 *
 * @author Anton Pechinsky
 */
public class BeanPropertyValueAdapter<B, T> extends AbstractValueModel<T> implements ContextDependentValueModel<B, T> {

    private String property;

    private Object context;

    /**
     * Creates value model for specified object.
     *
     * <p>Object is set as context.</p>
     *
     * <p>Call to this method equivalent to:
     * <pre>
     *  PropertyValueAdapter valueModel = new PropertyValueAdapter(person.getClass(), "name");
     *  valueModel.setContext(person);
     * </pre>
     * </p>
     *
     * @param bean Object an object of class containing specified property
     * @param property String property name to be accessed.
     * @return PropertyValueAdapter property value model
     */
    public static <B, T> BeanPropertyValueAdapter<B, T> forBean(B bean, String property) {
        BeanPropertyValueAdapter<B, T> valueAdapter = new BeanPropertyValueAdapter<B, T>((Class<B>) bean.getClass(), property);
        valueAdapter.setContext(bean);
        return valueAdapter;
    }

    /**
     * Creates value model for specified class. An alternative to constructor.
     *
     * @param beanClass Class a class containing specified property
     * @param property String property name to be accessed.
     * @return PropertyValueAdapter property value model
     */
    public static <B, T> BeanPropertyValueAdapter<B, T> forClass(Class<B> beanClass, String property) {
        return new BeanPropertyValueAdapter<B, T>(beanClass, property);
    }

    /**
     * Creates value model for specified property of specified class.
     *
     * <p>Before value access methods are invoked this value model need an object (context) whose property will be accessed.</p>
     *
     * <pre>
     *     // create model
     *     PropertyValueAdapter valueModel = new PropertyValueAdapter(Person.class, "name");
     *     ...
     *     // later this model may be used for specific object
     *     valueModel.setContext(person);
     * </pre>
     *
     * @param beanClass Class a class containing specified property
     * @param property String property name to be accessed.
     */
    public BeanPropertyValueAdapter(Class<B> beanClass, String property) {
        super((Class<T>) BeanUtils.getFieldType(beanClass, property));
        this.property = property;
    }

    /**
     * Sets value of specified bean property.
     *
     * @param value Object property value
     * @throws IllegalStateException if no context specified.
     */
    @Override
    public void setValue(T value) {
        Assert.checkNotNull(context, "Context object isn't set!");
        BeanUtils.setField(context, property, value);
    }

    /**
     * Returns value of specified bean property.
     *
     * @return Object property value
     * @throws IllegalStateException if no context specified.
     */
    @Override
    public T getValue() {
        Assert.checkNotNull(context, "Context object isn't set!");
        return (T) BeanUtils.getField(context, property);
    }

    @Override
    public void setContext(B context) {
        this.context = context;
    }

}
