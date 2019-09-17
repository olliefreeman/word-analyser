package freeman.ollie.analysis;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @since 09/09/2019
 */
public class LineAnalysisTaskTest {

    @Test
    public void compute() {

        LineAnalysisTask task = new LineAnalysisTask("Hello world & good morning. The date is 18/05/2016");
        Map<Integer, Long> results = task.compute();

        assertEquals("Number of word sizes", 7, results.size());

        assertEquals("Number of words of length 1", 1L, results.get(1).longValue());
        assertEquals("Number of words of length 2", 1L, results.get(2).longValue());
        assertEquals("Number of words of length 3", 1L, results.get(3).longValue());
        assertEquals("Number of words of length 4", 2L, results.get(4).longValue());
        assertEquals("Number of words of length 5", 2L, results.get(5).longValue());
        assertEquals("Number of words of length 7", 1L, results.get(7).longValue());
        assertEquals("Number of words of length 10", 1L, results.get(10).longValue());

    }

    @Test
    public void compute2() {

        LineAnalysisTask task = new LineAnalysisTask("The fox crossed over the fence and jumped a river");
        Map<Integer, Long> results = task.compute();

        assertEquals("Number of word sizes", 6, results.size());

        assertNull("Number of words of length 0", results.get(0));
        assertEquals("Number of words of length 1", 1L, results.get(1).longValue());
        assertNull("Number of words of length 2", results.get(2));
        assertEquals("Number of words of length 3", 4L, results.get(3).longValue());
        assertEquals("Number of words of length 4", 1L, results.get(4).longValue());
        assertEquals("Number of words of length 5", 2L, results.get(5).longValue());
        assertEquals("Number of words of length 6", 1L, results.get(6).longValue());
        assertEquals("Number of words of length 7", 1L, results.get(7).longValue());

    }
}