package freeman.ollie.analysis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RecursiveTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @since 09/09/2019
 */
public class WordAnalysisTask extends RecursiveTask<Integer> {

    private static final Logger logger = LoggerFactory.getLogger(WordAnalysisTask.class);

    private static final Pattern CLEANING_PATTERN = Pattern.compile("^\\p{Punct}*([^\\p{Punct}].+?%?)\\p{Punct}*$");

    private String word;

    public WordAnalysisTask(String word) {
        this.word = word;
    }

    @Override
    protected Integer compute() {
        String cleanedWord = cleanWord(word);
        logger.trace("Processing word [{}] >> Cleaned word [{}]", word, cleanedWord);
        return cleanedWord.length();
    }

    public static String cleanWord(String word) {
        if (word.equals("&")) return word;
        Matcher matcher = CLEANING_PATTERN.matcher(word);
        return matcher.matches() ? matcher.group(1) : "";
    }
}
