package freeman.ollie;

import freeman.ollie.exception.WordAnalyserException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertEquals;

/**
 * @since 09/09/2019
 */
public class AnalyserServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(AnalyserServiceTest.class);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void bibleAnalyseWordCount() throws Exception {
        AnalyserService service = new AnalyserService(Paths.get("src/test/resources/bible.txt"));
        service.analyse();

        assertEquals("Total number of words", 793824, service.getTotalNumberOfWords());
        assertEquals("Average word length", 4.085, service.getAverageWordLength(), 0.001);

        logger.info("\n{}", service.getAnalysisString());
    }

    @Test
    public void buildGradleAnalyseWordCount() throws Exception {
        AnalyserService service = new AnalyserService(Paths.get("build.gradle"));
        service.analyse();

        assertEquals("Total number of words", 89, service.getTotalNumberOfWords());
        assertEquals("Average word length", 9.213, service.getAverageWordLength(), 0.001);

        Map.Entry<Long, List<Integer>> mostUsed = service.getMostUsedWordLength();
        assertEquals("Most used word length", 14, mostUsed.getKey().intValue());
        assertEquals("Most used word lengths", 11, mostUsed.getValue().get(0).intValue());

        logger.info("\n{}", service.getAnalysisString());
    }

    @Test
    public void noFileAnalyse() throws Exception {

        AnalyserService service = new AnalyserService(Paths.get("resources/nothing.txt"));
        thrown.expect(WordAnalyserException.class);
        thrown.expectMessage(startsWith("No file found at provided path"));
        service.analyse();
    }

    @Test
    public void simpleAnalyseWordCount() throws Exception {
        AnalyserService service = new AnalyserService(Paths.get("src/test/resources/simple.txt"));
        service.analyse();

        assertEquals("Total number of words", 9, service.getTotalNumberOfWords());
        assertEquals("Average word length", 4.556, service.getAverageWordLength(), 0.001);

        Map.Entry<Long, List<Integer>> mostUsed = service.getMostUsedWordLength();
        assertEquals("Most used word length", 2, mostUsed.getKey().intValue());
        assertEquals("Most used word lengths", 4, mostUsed.getValue().get(0).intValue());
        assertEquals("Most used word lengths", 5, mostUsed.getValue().get(1).intValue());

        logger.info("\n{}", service.getAnalysisString());
    }
}