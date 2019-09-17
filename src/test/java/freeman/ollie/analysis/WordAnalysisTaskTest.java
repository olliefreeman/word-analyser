package freeman.ollie.analysis;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @since 09/09/2019
 */
public class WordAnalysisTaskTest {

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