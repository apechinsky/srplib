package net.srplib.binding;

/**
 * Binding is an association between two value models.
 *
 * @author Anton Pechinsky
 */
public interface Binding {

    /**
     * Returns source model of association.
     *
     * @return ValueModel value model.
     */
    ValueModel getSource();

    /**
     * Returns target model of association.
     *
     * @return ValueModel value model.
     */
    ValueModel getTarget();

}
