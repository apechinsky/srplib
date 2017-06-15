package org.srplib.conversion;

import org.junit.Test;

/**
 * @author Anton Pechinsky
 */
public class ConvertersTest {

    @Test
    public void testNewDefaultRegistryHasNoDuplicates() throws Exception {
        Converters.newDefaultRegistry();
    }
}
