package freeman.ollie.analysis

import org.junit.Test

import static org.junit.Assert.assertEquals

/**
 * @since 09/09/2019
 */
class GroovyLineAnalysisTaskTest {

    @Test
    void compute() {
        GroovyLineAnalysisTask task = new GroovyLineAnalysisTask(line: 'Hello world & good morning. The date is 18/05/2016')
        Map<Integer, Long> results = task.compute()

        assertEquals('Number of words', 7, results.size())

        assertEquals('Number of words of length 1', 1L, results[1])
        assertEquals('Number of words of length 2', 1L, results[2])
        assertEquals('Number of words of length 3', 1L, results[3])
        assertEquals('Number of words of length 4', 2L, results[4])
        assertEquals('Number of words of length 5', 2L, results[5])
        assertEquals('Number of words of length 7', 1L, results[7])
        assertEquals('Number of words of length 10', 1L, results[10])

    }
}