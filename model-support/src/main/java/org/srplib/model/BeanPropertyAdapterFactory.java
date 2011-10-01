package org.srplib.model;

import org.srplib.contract.Argument;

/**
 * Implementation of {@link PropertyAdapterFactory} which creates instances of {@link org.srplib.model.ValueModel} for property of specified
 * java class or an object.
 *
 * @author Anton Pechinsky
 */
public class BeanPropertyAdapterFactory<B, T> implements PropertyAdapterFactory<T> {

    private Class<B> objectClass;

    private B object;

    /**
     * Creates factory for specified class.
     *
     * @param clazz Class a class to create value models for.
     */
    public BeanPropertyAdapterFactory(Class<B> clazz) {
        Argument.checkNotNull(clazz, "Can't create bean property adapter with null class!");
        this.objectClass = clazz;
    }

    /**
     * Creates factory for specified object.
     *
     * @param object Object an object to create value models for.
     */
    public BeanPropertyAdapterFactory(B object) {
        Argument.checkNotNull(object, "Can't create bean property adapter with null object!");
        this.objectClass = (Class<B>)object.getClass();
        this.object = object;
    }

    @Override
    public ValueModel<T> newAdapter(String property) {
        return object != null
            ? BeanPropertyValueAdapter.<B, T>forBean(object, property)
            : BeanPropertyValueAdapter.<B, T>forClass(objectClass, property);
    }
}
