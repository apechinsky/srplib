package net.srplib.binding;

import net.srplib.contract.Assert;

/**
 * A convenient subclass for {@link ValueModel} implementers which holds value type.
 *
 * @author Anton Pechinsky
 */
public abstract class AbstractValueModel<T> implements ValueModel<T> {

    private Class<T> type;

    /**
     * Creates value model for specified value type.
     *
     * @param type Class value class
     */
    public AbstractValueModel(Class<T> type) {
        this.type = type;
    }

    /**
     *
     */
    public AbstractValueModel() {
        this.type = BeanUtils.getTypeParameter(getClass());
        Assert.notNull(type, "Can't construct ValueModel for class '" + getClass() + "'. Class has no type parameters.");
    }

    @Override
    public Class<T> getType() {
        return type;
    }
}
