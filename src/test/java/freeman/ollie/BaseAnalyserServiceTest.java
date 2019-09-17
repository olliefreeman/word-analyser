package freeman.ollie;

import freeman.ollie.exception.WordAnalyserException;
import freeman.ollie.util.Service;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @since 09/09/2019
 */
public abstract class BaseAnalyserServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(BaseAnalyserServiceTest.class);
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void anotherAnalyseWordCount() throws Exception {
        Service service = getService(Paths.get("src/test/resources/another.txt"));
        service.analyse();

        logger.info("\n{}", service.getAnalysisString());

        assertEquals("Total number of words", 29, service.getTotalNumberOfWords());

        assertNull("Number of words of length 0", service.getResults().get(0));
        assertEquals("Number of words of length 1", 1L, service.getResults().get(1).longValue());
        assertEquals("Number of words of length 2", 2L, service.getResults().get(2).longValue());
        assertEquals("Number of words of length 3", 13L, service.getResults().get(3).longValue());
        assertEquals("Number of words of length 4", 5L, service.getResults().get(4).longValue());
        assertEquals("Number of words of length 5", 2L, service.getResults().get(5).longValue());
        assertEquals("Number of words of length 6", 1L, service.getResults().get(6).longValue());
        assertEquals("Number of words of length 7", 3L, service.getResults().get(7).longValue());
        assertEquals("Number of words of length 8", 1L, service.getResults().get(8).longValue());
        assertEquals("Number of words of length 9", 1L, service.getResults().get(9).longValue());

        assertEquals("Average word length", 4.069, service.getAverageWordLength(), 0.001);

        Map.Entry<Long, List<Integer>> mostUsed = service.getMostUsedWordLength();
        assertEquals("Most used word length", 13, mostUsed.getKey().intValue());
        assertEquals("Most used word lengths", 3, mostUsed.getValue().get(0).intValue());

    }

    @Test
    public void bibleAnalyseWordCount() throws Exception {
        Service service = getService(Paths.get("src/test/resources/bible.txt"));
        service.analyse();

        logger.info("\n{}", service.getAnalysisString());

        assertEquals("Total number of words", 793121, service.getTotalNumberOfWords());
        assertEquals("Average word length", 4.084, service.getAverageWordLength(), 0.001);

        assertNull("Number of words of length 0", service.getResults().get(0));
        assertEquals("Number of words of length 1", 18647L, service.getResults().get(1).longValue());

        Map.Entry<Long, List<Integer>> mostUsed = service.getMostUsedWordLength();
        assertEquals("Most used word length", 221411, mostUsed.getKey().intValue());
        assertEquals("Most used word lengths", 3, mostUsed.getValue().get(0).intValue());
    }

    @Test
    public void buildGradleAnalyseWordCount() throws Exception {
        Service service = getService(Paths.get("build.gradle"));
        service.analyse();

        logger.info("\n{}", service.getAnalysisString());

        assertEquals("Total number of words", 143, service.getTotalNumberOfWords());
        assertEquals("Average word length", 6.776, service.getAverageWordLength(), 0.001);

        assertNull("Number of words of length 0", service.getResults().get(0));

        Map.Entry<Long, List<Integer>> mostUsed = service.getMostUsedWordLength();
        assertEquals("Most used word length", 27, mostUsed.getKey().intValue());
        assertEquals("Most used word lengths", 4, mostUsed.getValue().get(0).intValue());
    }

    @Test
    public void noFileAnalyse() throws Exception {

        Service service = getService(Paths.get("resources/nothing.txt"));
        thrown.expect(WordAnalyserException.class);
        thrown.expectMessage(startsWith("No file found at provided path"));
        service.analyse();
    }

    @Test
    public void simple2AnalyseWordCount() throws Exception {
        Service service = getService(Paths.get("src/test/resources/simple2.txt"));
        service.analyse();

        logger.info("\n{}", service.getAnalysisString());

        assertEquals("Total number of words", 10, service.getTotalNumberOfWords());

        assertNull("Number of words of length 0", service.getResults().get(0));
        assertEquals("Number of words of length 1", 1L, service.getResults().get(1).longValue());
        assertNull("Number of words of length 2", service.getResults().get(2));
        assertEquals("Number of words of length 3", 4L, service.getResults().get(3).longValue());
        assertEquals("Number of words of length 4", 1L, service.getResults().get(4).longValue());
        assertEquals("Number of words of length 5", 2L, service.getResults().get(5).longValue());
        assertEquals("Number of words of length 6", 1L, service.getResults().get(6).longValue());
        assertEquals("Number of words of length 7", 1L, service.getResults().get(7).longValue());

        assertEquals("Average word length", 4.000, service.getAverageWordLength(), 0.001);

        Map.Entry<Long, List<Integer>> mostUsed = service.getMostUsedWordLength();
        assertEquals("Most used word length", 4, mostUsed.getKey().intValue());
        assertEquals("Most used word lengths", 3, mostUsed.getValue().get(0).intValue());

    }

    @Test
    public void simple2MultilineAnalyseWordCount() throws Exception {
        Service service = getService(Paths.get("src/test/resources/simple2_multiline.txt"));
        service.analyse();

        logger.info("\n{}", service.getAnalysisString());

        assertEquals("Total number of words", 10, service.getTotalNumberOfWords());

        assertNull("Number of words of length 0", service.getResults().get(0));
        assertEquals("Number of words of length 1", 1L, service.getResults().get(1).longValue());
        assertNull("Number of words of length 2", service.getResults().get(2));
        assertEquals("Number of words of length 3", 4L, service.getResults().get(3).longValue());
        assertEquals("Number of words of length 4", 1L, service.getResults().get(4).longValue());
        assertEquals("Number of words of length 5", 2L, service.getResults().get(5).longValue());
        assertEquals("Number of words of length 6", 1L, service.getResults().get(6).longValue());
        assertEquals("Number of words of length 7", 1L, service.getResults().get(7).longValue());

        assertEquals("Average word length", 4.000, service.getAverageWordLength(), 0.001);

        Map.Entry<Long, List<Integer>> mostUsed = service.getMostUsedWordLength();
        assertEquals("Most used word length", 4, mostUsed.getKey().intValue());
        assertEquals("Most used word lengths", 3, mostUsed.getValue().get(0).intValue());

    }

    @Test
    public void simpleAnalyseWordCount() throws Exception {
        Service service = getService(Paths.get("src/test/resources/simple.txt"));
        service.analyse();

        logger.info("\n{}", service.getAnalysisString());

        assertEquals("Total number of words", 9, service.getTotalNumberOfWords());

        assertNull("Number of words of length 0", service.getResults().get(0));
        assertEquals("Number of words of length 1", 1L, service.getResults().get(1).longValue());
        assertEquals("Number of words of length 2", 1L, service.getResults().get(2).longValue());
        assertEquals("Number of words of length 3", 1L, service.getResults().get(3).longValue());
        assertEquals("Number of words of length 4", 2L, service.getResults().get(4).longValue());
        assertEquals("Number of words of length 5", 2L, service.getResults().get(5).longValue());
        assertEquals("Number of words of length 7", 1L, service.getResults().get(7).longValue());
        assertEquals("Number of words of length 10", 1L, service.getResults().get(10).longValue());

        assertEquals("Average word length", 4.556, service.getAverageWordLength(), 0.001);

        Map.Entry<Long, List<Integer>> mostUsed = service.getMostUsedWordLength();
        assertEquals("Most used word length", 2, mostUsed.getKey().intValue());
        assertEquals("Most used word lengths", 4, mostUsed.getValue().get(0).intValue());
        assertEquals("Most used word lengths", 5, mostUsed.getValue().get(1).intValue());

    }

    abstract Service getService(Path path);
}