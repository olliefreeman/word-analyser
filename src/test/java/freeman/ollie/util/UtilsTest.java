package freeman.ollie.util;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * @since 17/09/2019
 */
public class UtilsTest {

    @Test
    public void testCleaning() {
        assertEquals("hello", Utils.cleanWord("hello"));
        assertEquals("hello", Utils.cleanWord(".hello"));
        assertEquals("hello", Utils.cleanWord("hello."));
        assertEquals("hello", Utils.cleanWord("hello,"));
        assertEquals("hello", Utils.cleanWord("(hello"));
        assertEquals("hello", Utils.cleanWord("hello)"));
        assertEquals("hello", Utils.cleanWord("hello)."));

        assertEquals("a", Utils.cleanWord("a"));
        assertEquals("I", Utils.cleanWord("I"));

        assertEquals("11", Utils.cleanWord("11"));
        assertEquals("-11", Utils.cleanWord("-11"));
        assertEquals("£11", Utils.cleanWord("£11"));
        assertEquals("$11", Utils.cleanWord("$11"));
        assertEquals("€11", Utils.cleanWord("€11"));
        assertEquals("11%", Utils.cleanWord("11%"));
        assertEquals("11.0", Utils.cleanWord("11.0"));
        assertEquals("11.0%", Utils.cleanWord("11.0%"));
        assertEquals("18/05/2016", Utils.cleanWord("18/05/2016"));
        assertEquals("&", Utils.cleanWord("&"));
        assertEquals("", Utils.cleanWord("{"));
        assertEquals("", Utils.cleanWord("}"));
        assertEquals("", Utils.cleanWord("+="));
        assertEquals("", Utils.cleanWord("="));
    }

    @Test
    public void testSplitting() {
        assertArrayEquals(new String[]{"hello"}, Utils.splitWords("hello"));
        assertArrayEquals(new String[]{"hel", "lo"}, Utils.splitWords("hel lo"));
        assertArrayEquals(new String[]{"hel", "lo"}, Utils.splitWords("hel(lo"));
        assertArrayEquals(new String[]{"/lo", "hel"}, Utils.splitWords("hel/lo"));
        assertArrayEquals(new String[]{"11.11"}, Utils.splitWords("11.11"));
        assertArrayEquals(new String[]{"£11.11"}, Utils.splitWords("£11.11"));
        assertArrayEquals(new String[]{"18/05/2016"}, Utils.splitWords("18/05/2016"));
        assertArrayEquals(new String[]{".com", "google"}, Utils.splitWords("google.com"));
        assertArrayEquals(new String[]{".groovy',", ".codehaus", "'org",}, Utils.splitWords("'org.codehaus.groovy',"));
        assertArrayEquals(new String[]{".txt", "LICENSE-2.0",}, Utils.splitWords("LICENSE-2.0.txt"));
        assertArrayEquals(new String[]{".txt'", "/LICENSE-2.0", "/licenses", ".org", ".apache", "://www", "'http",},
                          Utils.splitWords("'http://www.apache.org/licenses/LICENSE-2.0.txt'"));
    }

}