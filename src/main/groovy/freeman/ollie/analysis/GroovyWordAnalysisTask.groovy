package freeman.ollie.analysis

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.util.concurrent.RecursiveTask
import java.util.regex.Pattern

/**
 * @since 10/09/2019
 */
class GroovyWordAnalysisTask extends RecursiveTask<Integer> {

    private static final Logger logger = LoggerFactory.getLogger(GroovyWordAnalysisTask)

    private static final Pattern CLEANING_PATTERN = ~/^\p{Punct}*(.+?%?)\p{Punct}*$/

    private String word

    @Override
    protected Integer compute() {
        String cleanedWord = cleanWord(word)
        logger.trace('Processing word [{}] >> Cleaned word [{}]', word, cleanedWord)
        cleanedWord.length()
    }

    static String cleanWord(String word) {
        word == '&' ? word : word.find(CLEANING_PATTERN) {it[1]} ?: word
    }
}
