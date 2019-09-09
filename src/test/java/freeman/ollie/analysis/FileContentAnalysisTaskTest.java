package freeman.ollie.analysis;

import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @since 09/09/2019
 */
public class FileContentAnalysisTaskTest {

    @Test
    public void bibleAnalyse() throws Exception {
        FileContentAnalysisTask task = new FileContentAnalysisTask(Files.readAllLines(Paths.get("src/test/resources/bible.txt")));
        Map<Integer, Long> results = task.compute();

        assertEquals("Number of different length words", 18, results.size());
    }

    @Test
    public void buildGradleAnalyse() throws Exception {
        FileContentAnalysisTask task = new FileContentAnalysisTask(Files.readAllLines(Paths.get("build.gradle")));
        Map<Integer, Long> results = task.compute();

        assertNull("No words of length 0", results.get(0));
        assertEquals("Number of different length words", 20, results.size());
    }

    @Test
    public void simpleAnalyse() throws Exception {
        FileContentAnalysisTask task = new FileContentAnalysisTask(Files.readAllLines(Paths.get("src/test/resources/simple.txt")));
        Map<Integer, Long> results = task.compute();

        assertEquals("Number of different length words", 7, results.size());

        assertEquals("Number of words of length 1", 1L, results.get(1).longValue());
        assertEquals("Number of words of length 2", 1L, results.get(2).longValue());
        assertEquals("Number of words of length 3", 1L, results.get(3).longValue());
        assertEquals("Number of words of length 4", 2L, results.get(4).longValue());
        assertEquals("Number of words of length 5", 2L, results.get(5).longValue());
        assertEquals("Number of words of length 7", 1L, results.get(7).longValue());
        assertEquals("Number of words of length 10", 1L, results.get(10).longValue());
    }

}