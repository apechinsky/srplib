package org.srplib.binding;

import org.srplib.conversion.Converter;

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


    /**
     * An optional value converter between models.
     *
     * @return Converter a converter
     */
    Converter getConverter();

}
