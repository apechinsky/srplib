package org.srplib.conversion;

import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

/**
 * Test class for ConvertBackConverter.
 *
 * @author Anton Pechinsky
 */
public class ConvertBackConverterTest {

    @Test
    public void testConvert() throws Exception {
        TwoWayConverter<String, Boolean> delegate = Mockito.mock(TwoWayConverter.class);
        ConvertBackConverter<Boolean, String> reverseConverter = new ConvertBackConverter<Boolean, String>(delegate);

        // invoke converter
        reverseConverter.convert(true);

        // verify that reverse converter have called convertBack method of underlying converter.
        verify(delegate).convertBack(true);
    }
}
