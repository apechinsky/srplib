package org.srplib.contract;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for Utils.
 *
 * @author Anton Pechinsky
 */
public class UtilsTest {

    @Test
    public void testEquals() throws Exception {
        assertTrue(Utils.equals(null, null));
        assertTrue(Utils.equals("value", "value"));

        assertFalse(Utils.equals("non-null", null));
        assertFalse(Utils.equals(null, "non-null"));
    }

    @Test
    public void testIsBlank() throws Exception {
        assertTrue(Utils.isBlank(null));
        assertTrue(Utils.isBlank(""));
        assertTrue(Utils.isBlank(" "));
        assertTrue(Utils.isBlank(" \n\r\t    "));
    }

    @Test
    public void testFormat() throws Exception {
        assertEquals("-*-", Utils.format("-%s-", "*"));
    }

}
