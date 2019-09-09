package freeman.ollie.analysis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RecursiveTask;

/**
 * @since 09/09/2019
 */
public class WordAnalysisTask extends RecursiveTask<Integer> {

    private static final Logger logger = LoggerFactory.getLogger(WordAnalysisTask.class);

    private String word;

    public WordAnalysisTask(String word) {
        this.word = word;
    }

    @Override
    protected Integer compute() {
        logger.trace("Processing word [{}]", word);
        String cleanedWord = cleanWord(word);
        logger.trace("Cleaned word [{}]", cleanedWord);
        return cleanedWord.length();
    }

    public static String cleanWord(String word) {
        if (word.equals("&")) return word;

        String clean = word.replaceAll("^\\p{Punct}", "");

        if (word.endsWith("%")) return word;

        clean = clean.replaceAll("\\p{Punct}$", "");

        return clean;
    }
}
