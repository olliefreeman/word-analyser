package freeman.ollie.analysis

import org.junit.Test

import java.nio.file.Files
import java.nio.file.Paths

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNull

/**
 * @since 09/09/2019
 */
class GroovyFileContentAnalysisTaskTest {

    @Test
    void bibleAnalyse() throws Exception {
        GroovyFileContentAnalysisTask task = new GroovyFileContentAnalysisTask(fileLines: Files.readAllLines(Paths.get(
            'src/test/resources/bible.txt')))
        Map<Integer, Long> results = task.compute()

        assertEquals('Number of different length words', 18, results.size())
    }

    @Test
    void buildGradleAnalyse() throws Exception {
        GroovyFileContentAnalysisTask task = new GroovyFileContentAnalysisTask(fileLines: Files.readAllLines(Paths.get('build.gradle')))
        Map<Integer, Long> results = task.compute()

        assertNull('Words of length 0', results[0])
        assertEquals('Number of different length words', 15, results.size())
    }

    @Test
    void simpleAnalyse() throws Exception {
        GroovyFileContentAnalysisTask task = new GroovyFileContentAnalysisTask(fileLines: Files.readAllLines(Paths.get(
            'src/test/resources/simple.txt')))
        Map<Integer, Long> results = task.compute()

        assertEquals('Number of different length words', 7, results.size())

        assertEquals('Number of words of length 1', 1L, results[1])
        assertEquals('Number of words of length 2', 1L, results[2])
        assertEquals('Number of words of length 3', 1L, results[3])
        assertEquals('Number of words of length 4', 2L, results[4])
        assertEquals('Number of words of length 5', 2L, results[5])
        assertEquals('Number of words of length 7', 1L, results[7])
        assertEquals('Number of words of length 10', 1L, results[10])
    }


    @Test
    void simple2MultilineAnalyseWordCount() throws Exception {
        GroovyFileContentAnalysisTask task = new GroovyFileContentAnalysisTask(fileLines: Files.readAllLines(Paths.get(
            'src/test/resources/simple2_multiline.txt')))
        Map<Integer, Long> results = task.compute()

        assertEquals('Number of different length words', 6, results.size())

        assertNull('Number of words of length 0', results[0])
        assertEquals('Number of words of length 1', 1L, results[1])
        assertNull('Number of words of length 2', results[2])
        assertEquals('Number of words of length 3', 4L, results[3])
        assertEquals('Number of words of length 4', 1L, results[4])
        assertEquals('Number of words of length 5', 2L, results[5])
        assertEquals('Number of words of length 6', 1L, results[6])
        assertEquals('Number of words of length 7', 1L, results[7])
    }

    @Test
    void smallBuildGradleAnalyseWordCount() throws Exception {
        GroovyFileContentAnalysisTask task = new GroovyFileContentAnalysisTask(fileLines: Files.readAllLines(Paths.get(
            'src/test/resources/small_build.gradle')))
        Map<Integer, Long> results = task.compute()

        assertEquals('Number of different length words', 8, results.size())

        assertNull('Number of words of length 0', results[0])
        assertNull('Number of words of length 1', results[1])
        assertEquals('Number of words of length 2', 7L, results[2])
        assertNull('Number of words of length 3', results[3])
        assertEquals('Number of words of length 4', 1L, results[4])
        assertEquals('Number of words of length 5', 1L, results[5])
        assertEquals('Number of words of length 6', 1L, results[6])
        assertEquals('Number of words of length 7', 2L, results[7])
        assertEquals('Number of words of length 11', 1L, results[11])
        assertEquals('Number of words of length 12', 1L, results[12])
        assertEquals('Number of words of length 14', 1L, results[14])

    }
}