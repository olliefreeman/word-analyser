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
        assertEquals("Number of different length words", 15, results.size());
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

    @Test
    public void simple2MultilineAnalyseWordCount() throws Exception {
        FileContentAnalysisTask task = new FileContentAnalysisTask(Files.readAllLines(Paths.get("src/test/resources/simple2_multiline.txt")));
        Map<Integer, Long> results = task.compute();

        assertEquals("Number of different length words", 6, results.size());

        assertNull("Number of words of length 0", results.get(0));
        assertEquals("Number of words of length 1", 1L, results.get(1).longValue());
        assertNull("Number of words of length 2", results.get(2));
        assertEquals("Number of words of length 3", 4L, results.get(3).longValue());
        assertEquals("Number of words of length 4", 1L, results.get(4).longValue());
        assertEquals("Number of words of length 5", 2L, results.get(5).longValue());
        assertEquals("Number of words of length 6", 1L, results.get(6).longValue());
        assertEquals("Number of words of length 7", 1L, results.get(7).longValue());
    }

    @Test
    public void smallBuildGradleAnalyseWordCount() throws Exception {
        FileContentAnalysisTask task = new FileContentAnalysisTask(Files.readAllLines(Paths.get("src/test/resources/small_build.gradle")));
        Map<Integer, Long> results = task.compute();

        assertEquals("Number of different length words", 8, results.size());

        assertNull("Number of words of length 0", results.get(0));
        assertNull("Number of words of length 1", results.get(1));
        assertEquals("Number of words of length 2", 7L, results.get(2).longValue());
        assertNull("Number of words of length 3", results.get(3));
        assertEquals("Number of words of length 4", 1L, results.get(4).longValue());
        assertEquals("Number of words of length 5", 1L, results.get(5).longValue());
        assertEquals("Number of words of length 6", 1L, results.get(6).longValue());
        assertEquals("Number of words of length 7", 2L, results.get(7).longValue());
        assertEquals("Number of words of length 11", 1L, results.get(11).longValue());
        assertEquals("Number of words of length 12", 1L, results.get(12).longValue());
        assertEquals("Number of words of length 14", 1L, results.get(14).longValue());

    }

}