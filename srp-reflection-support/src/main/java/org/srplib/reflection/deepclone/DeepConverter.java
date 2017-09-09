package org.srplib.reflection.deepclone;

/**
 * @author Q-APE
 */
public interface DeepConverter<I, O> {

    O convert(I input, DeepConverterContext context);

}
