package net.srplib.binding;

import net.srplib.contract.Argument;

/**
 * A simple implementation of {@link Binding} interface.
 *
 * @author Anton Pechinsky
 */
public class DefaultBinding implements Binding {

    private final ValueModel source;

    private final ValueModel target;

    public DefaultBinding(ValueModel source, ValueModel target) {
        Argument.notNull(source, "source");
        Argument.notNull(target, "target");
        this.source = source;
        this.target = target;
    }

    public ValueModel getSource() {
        return source;
    }

    public ValueModel getTarget() {
        return target;
    }
}
