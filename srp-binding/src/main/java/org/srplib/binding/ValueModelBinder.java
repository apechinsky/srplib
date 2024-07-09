package org.srplib.binding;

import java.util.ArrayList;
import java.util.List;

import org.srplib.contract.Argument;
import org.srplib.contract.Assert;
import org.srplib.conversion.ConvertBackConverter;
import org.srplib.conversion.Converter;
import org.srplib.conversion.ConverterRegistry;
import org.srplib.conversion.EmptyConverter;
import org.srplib.conversion.TwoWayConverter;
import org.srplib.conversion.registry.ExactConverterRegistry;
import org.srplib.model.ContextDependentValueModel;
import org.srplib.model.ValueModel;
import org.srplib.validation.ValidationErrors;
import org.srplib.validation.ValidationException;

/**
 * An implementation of {@link Binder} which bind collection of {@link Binding}.
 *
 * @author Anton Pechinsky
 */
public class ValueModelBinder<T> implements Binder<T> {

    private List<Binding> bindings = new ArrayList<Binding>();

    private ConverterRegistry converterRegistry;

    public ValueModelBinder(List<Binding> bindings) {
        Argument.checkNotNull(bindings, "binding");
        this.bindings = bindings;
        this.converterRegistry = createDefaultConverterRegistry();
    }

    private ConverterRegistry createDefaultConverterRegistry() {
        ConverterRegistry converterRegistry = new ExactConverterRegistry();
        return converterRegistry;
    }

    public ValueModelBinder() {
        this(new ArrayList<Binding>());
    }

    public ConverterRegistry getConverterRegistry() {
        return converterRegistry;
    }

    public void setConverterRegistry(ConverterRegistry converterRegistry) {
        this.converterRegistry = converterRegistry;
    }

    /**
     * Adds binding to this binder.
     *
     * @param binding Binding a binding definition.
     */
    public void addBinding(Binding binding) {
        this.bindings.add(binding);
    }

    /**
     * Adds binding to this binder.
     *
     * <p>Call to this method is equivalent to
     * <pre>
     * addBinding(new DefaultBinding(source, target))
     * </pre>
     * </p>
     *
     * @param source ValueModel source model
     * @param target ValueModel target model
     */
    public void addBinding(ValueModel source, ValueModel target) {
        addBinding(new DefaultBinding(source, target));
    }

    @Override
    public void bind(T object) {
        for (Binding binding : bindings) {
            bind(object, binding);
        }
    }

    private void bind(T object, Binding binding) {
        ValueModel<?> source = binding.getSource();
        setContextIfApplicable(object, source);

        ValueModel target = binding.getTarget();

        Object value = source.getValue();

        // Use binding specific converter if exists otherwise use global converter
        Converter converter = binding.getConverter() != null
            ? binding.getConverter()
            : getConverterNullSafe(source.getType(), target.getType());

        Object convertedValue = converter.convert(value);

        // Set converted value to target model
        target.setValue(convertedValue);
    }

    @Override
    public T unbind(T object) {
        ValidationErrors validationErrors = unbind(object, new ValidationErrors());
        if (validationErrors.hasErrors()) {
            throw new ValidationException(validationErrors);
        }
        return object;
    }

    @Override
    public ValidationErrors unbind(T object, ValidationErrors errors) {
        for (Binding binding : bindings) {
            unbind(object, binding, errors);
        }
        return errors;
    }

    private void unbind(T object, Binding binding, ValidationErrors errors) {
        ValueModel<Object> source = binding.getSource();
        setContextIfApplicable(object, source);

        ValueModel target = binding.getTarget();

        Object value = target.getValue();

        // Use binding specific converter if exists and its type is TwoWayConverter otherwise use global converter
        Converter converter = binding.getConverter() instanceof TwoWayConverter
            ? new ConvertBackConverter((TwoWayConverter)binding.getConverter())
            : getConverterNullSafe(target.getType(), source.getType());

        Object convertedValue = converter.convert(value);

        // Set converted value to target model
        source.setValue(convertedValue);
    }

    private void setContextIfApplicable(T object, ValueModel<?> valueModel) {
        if (valueModel instanceof ContextDependentValueModel) {
            ((ContextDependentValueModel<T, ?>)valueModel).setContext(object);
        }
    }

    private Converter getConverterNullSafe(Class<?> source, Class<?> target) {
        Converter converter = null;

        if (target.isAssignableFrom(source)) {
            converter = EmptyConverter.instance();
        }
        else {
            converter = converterRegistry.find(source, target);
        }
        Assert.checkNotNull(converter, "No converter from type '" + source + "' to type '" + target + "'.");

        return converter;
    }


}
