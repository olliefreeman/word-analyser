package freeman.ollie.analysis;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * @since 09/09/2019
 */
public class WordAnalysisTaskTest {

    @Test
    public void testCleaning() {
        assertEquals("hello", WordAnalysisTask.cleanWord("hello"));
        assertEquals("hello", WordAnalysisTask.cleanWord(".hello"));
        assertEquals("hello", WordAnalysisTask.cleanWord("hello."));
        assertEquals("hello", WordAnalysisTask.cleanWord("hello,"));
        assertEquals("hello", WordAnalysisTask.cleanWord("(hello"));
        assertEquals("hello", WordAnalysisTask.cleanWord("hello)"));
        assertEquals("hello", WordAnalysisTask.cleanWord("hello)."));

        assertEquals("a", WordAnalysisTask.cleanWord("a"));
        assertEquals("I", WordAnalysisTask.cleanWord("I"));

        assertEquals("11", WordAnalysisTask.cleanWord("11"));
        assertEquals("-11", WordAnalysisTask.cleanWord("-11"));
        assertEquals("£11", WordAnalysisTask.cleanWord("£11"));
        assertEquals("$11", WordAnalysisTask.cleanWord("$11"));
        assertEquals("€11", WordAnalysisTask.cleanWord("€11"));
        assertEquals("11%", WordAnalysisTask.cleanWord("11%"));
        assertEquals("11.0", WordAnalysisTask.cleanWord("11.0"));
        assertEquals("11.0%", WordAnalysisTask.cleanWord("11.0%"));
        assertEquals("18/05/2016", WordAnalysisTask.cleanWord("18/05/2016"));
        assertEquals("&", WordAnalysisTask.cleanWord("&"));
        assertEquals("", WordAnalysisTask.cleanWord("{"));
        assertEquals("", WordAnalysisTask.cleanWord("}"));
        assertEquals("", WordAnalysisTask.cleanWord("+="));
        assertEquals("", WordAnalysisTask.cleanWord("="));
    }

    @Test
    public void testSplitting() {
        assertArrayEquals(new String[]{"hello"}, WordAnalysisTask.splitWords("hello"));
        assertArrayEquals(new String[]{"hel", "lo"}, WordAnalysisTask.splitWords("hel lo"));
        assertArrayEquals(new String[]{"hel", "lo"}, WordAnalysisTask.splitWords("hel(lo"));
        assertArrayEquals(new String[]{"/lo", "hel"}, WordAnalysisTask.splitWords("hel/lo"));
        assertArrayEquals(new String[]{"11.11"}, WordAnalysisTask.splitWords("11.11"));
        assertArrayEquals(new String[]{"£11.11"}, WordAnalysisTask.splitWords("£11.11"));
        assertArrayEquals(new String[]{"18/05/2016"}, WordAnalysisTask.splitWords("18/05/2016"));
        assertArrayEquals(new String[]{".com", "google"}, WordAnalysisTask.splitWords("google.com"));
        assertArrayEquals(new String[]{".groovy',", ".codehaus", "'org",}, WordAnalysisTask.splitWords("'org.codehaus.groovy',"));
        assertArrayEquals(new String[]{".txt", "LICENSE-2.0",}, WordAnalysisTask.splitWords("LICENSE-2.0.txt"));
        assertArrayEquals(new String[]{".txt'", "/LICENSE-2.0", "/licenses", ".org", ".apache", "://www", "'http",},
                          WordAnalysisTask.splitWords("'http://www.apache.org/licenses/LICENSE-2.0.txt'"));
    }

    @Test
    public void testCompute() {
        assertEquals(5, new WordAnalysisTask("hello").compute().intValue());
        assertEquals(5, new WordAnalysisTask(".hello").compute().intValue());
        assertEquals(5, new WordAnalysisTask("hello.").compute().intValue());
        assertEquals(5, new WordAnalysisTask("hello,").compute().intValue());
        assertEquals(5, new WordAnalysisTask("(hello").compute().intValue());
        assertEquals(5, new WordAnalysisTask("hello)").compute().intValue());

        assertEquals(2, new WordAnalysisTask("11").compute().intValue());
        assertEquals(3, new WordAnalysisTask("£11").compute().intValue());
        assertEquals(3, new WordAnalysisTask("11%").compute().intValue());
        assertEquals(4, new WordAnalysisTask("11.0").compute().intValue());
        assertEquals(5, new WordAnalysisTask("11.0%").compute().intValue());
        assertEquals(10, new WordAnalysisTask("18/05/2016").compute().intValue());
        assertEquals(1, new WordAnalysisTask("&").compute().intValue());
    }
}