package freeman.ollie.util;

import freeman.ollie.exception.WordAnalyserException;

import java.util.List;
import java.util.Map;

/**
 * @since 10/09/2019
 */
public interface Service {

    Map<Integer, Long> getResults();

    void analyse() throws WordAnalyserException;

    String getAnalysisString();

    double getAverageWordLength();

    Map.Entry<Long, List<Integer>> getMostUsedWordLength();

    long getTotalNumberOfWords();

}
