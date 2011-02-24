package net.srplib.binding.support;

import net.srplib.contract.Argument;

/**
 * @author Anton Pechinsky
 */
public class BeanAdapterFactory {

    private Class beanClass;

    public BeanAdapterFactory(Class beanClass) {
        Argument.notNull(beanClass, "beanClass");
        this.beanClass = beanClass;
    }

    public <T> PropertyValueAdapter<T> newAdapter(String property) {
        return PropertyValueAdapter.newFromBean(beanClass, property);
    }
}
