package net.srplib.binding;

import net.srplib.contract.Argument;
import net.srplib.conversion.Converter;

/**
 * A simple implementation of {@link Binding} interface.
 *
 * @author Anton Pechinsky
 */
public class DefaultBinding implements Binding {

    private final ValueModel source;

    private final ValueModel target;

    private final Converter converter;

    public DefaultBinding(ValueModel source, ValueModel target, Converter converter) {
        Argument.notNull(source, "source");
        Argument.notNull(target, "target");
        this.source = source;
        this.target = target;
        this.converter = converter;
    }

    public DefaultBinding(ValueModel source, ValueModel target) {
        this(source, target, null);
    }

    public ValueModel getSource() {
        return source;
    }

    public ValueModel getTarget() {
        return target;
    }

    @Override
    public Converter getConverter() {
        return converter;
    }
}
