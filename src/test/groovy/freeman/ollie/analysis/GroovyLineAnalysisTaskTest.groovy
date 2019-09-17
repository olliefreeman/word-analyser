package freeman.ollie.analysis

import org.junit.Test

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNull

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

    @Test
    void compute2() {

        LineAnalysisTask task = new LineAnalysisTask('The fox crossed over the fence and jumped a river')
        Map<Integer, Long> results = task.compute()

        assertEquals('Number of word sizes', 6, results.size())

        assertNull('Number of words of length 0', results.get(0))
        assertEquals('Number of words of length 1', 1L, results[1])
        assertNull('Number of words of length 2', results.get(2))
        assertEquals('Number of words of length 3', 4L, results[3])
        assertEquals('Number of words of length 4', 1L, results[4])
        assertEquals('Number of words of length 5', 2L, results[5])
        assertEquals('Number of words of length 6', 1L, results[6])
        assertEquals('Number of words of length 7', 1L, results[7])

    }

    @Test
    void computeSomethingWithAnEmptyCharacter() {

        LineAnalysisTask task = new LineAnalysisTask('application {')
        Map<Integer, Long> results = task.compute()

        assertEquals('Number of word sizes', 1, results.size())

        assertNull('Number of words of length 0', results.get(0))
        assertEquals('Number of words of length 11', 1L, results[11])
    }
}