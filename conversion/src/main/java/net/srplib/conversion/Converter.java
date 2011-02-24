package net.srplib.conversion;
 

/**
 * General purpose value converter.
 *
 * <ul>Use cases:
 *  <li>Data binding</li>
 *  <li>Data mapping</li>
 *  <li>Data migration</li>
 *  <li>etc.</li>
 * </ul>
 *
 * @author Anton Pechinsky
 */
public interface Converter<I, O> {

    /**
     * Converts specified value.
     *
     * @param input a value to convert.
     * @return output an output value.
     * @throws ConverterException if value can't be converted.
     */
    O convert(I input);

}
