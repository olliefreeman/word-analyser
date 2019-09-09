package freeman.ollie.analysis;

import org.junit.Test;

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

        assertEquals("11", WordAnalysisTask.cleanWord("11"));
        assertEquals("£11", WordAnalysisTask.cleanWord("£11"));
        assertEquals("11%", WordAnalysisTask.cleanWord("11%"));
        assertEquals("11.0", WordAnalysisTask.cleanWord("11.0"));
        assertEquals("11.0%", WordAnalysisTask.cleanWord("11.0%"));
        assertEquals("18/05/2016", WordAnalysisTask.cleanWord("18/05/2016"));
        assertEquals("&", WordAnalysisTask.cleanWord("&"));
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