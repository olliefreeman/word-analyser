package freeman.ollie

import freeman.ollie.exception.WordAnalyserException
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.nio.file.Paths

import static org.hamcrest.Matchers.startsWith
import static org.junit.Assert.assertEquals

/**
 * @since 09/09/2019
 */
class GroovyAnalyserServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(GroovyAnalyserServiceTest)

    @Rule
    public ExpectedException thrown = ExpectedException.none()

    @Test
    void bibleAnalyseWordCount() throws Exception {
        GroovyAnalyserService service = new GroovyAnalyserService(Paths.get('src/test/resources/bible.txt'))
        service.analyse()

        assertEquals('Total number of words', 774586, service.getTotalNumberOfWords())
        assertEquals('Average word length', 4.158, service.getAverageWordLength(), 0.001)

        logger.info('\n{}', service.getAnalysisString())
    }

    @Test
    void buildGradleAnalyseWordCount() throws Exception {
        GroovyAnalyserService service = new GroovyAnalyserService(Paths.get('build.gradle'))
        service.analyse()

        assertEquals('Total number of words', 91, service.getTotalNumberOfWords())
        assertEquals('Average word length', 8.868, service.getAverageWordLength(), 0.001)

        Map.Entry<Long, List<Integer>> mostUsed = service.getMostUsedWordLength()
        assertEquals('Most used word length', 14, mostUsed.getKey().intValue())
        assertEquals('Most used word lengths', 11, mostUsed.getValue()[0])

        logger.info('\n{}', service.getAnalysisString())
    }

    @Test
    void noFileAnalyse() throws Exception {

        GroovyAnalyserService service = new GroovyAnalyserService(Paths.get('resources/nothing.txt'))
        thrown.expect(WordAnalyserException.class)
        thrown.expectMessage(startsWith('No file found at provided path'))
        service.analyse()
    }

    @Test
    void simpleAnalyseWordCount() throws Exception {
        GroovyAnalyserService service = new GroovyAnalyserService(Paths.get('src/test/resources/simple.txt'))
        service.analyse()

        assertEquals('Total number of words', 9, service.getTotalNumberOfWords())
        assertEquals('Average word length', 4.556, service.getAverageWordLength(), 0.001)

        Map.Entry<Long, List<Integer>> mostUsed = service.getMostUsedWordLength()
        assertEquals('Most used word length', 2, mostUsed.getKey().intValue())
        assertEquals('Most used word lengths', 4, mostUsed.getValue()[0])
        assertEquals('Most used word lengths', 5, mostUsed.getValue()[1])

        logger.info('\n{}', service.getAnalysisString())
    }
}