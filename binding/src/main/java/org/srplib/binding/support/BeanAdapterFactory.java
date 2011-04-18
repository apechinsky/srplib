package org.srplib.binding.support;

import org.srplib.contract.Argument;

/**
 * @author Anton Pechinsky
 */
public class BeanAdapterFactory {

    private Class beanClass;

    public BeanAdapterFactory(Class beanClass) {
        Argument.checkNotNull(beanClass, "beanClass");
        this.beanClass = beanClass;
    }

    public <T> PropertyValueAdapter<T> newAdapter(String property) {
        return PropertyValueAdapter.newFromBean(beanClass, property);
    }
}
