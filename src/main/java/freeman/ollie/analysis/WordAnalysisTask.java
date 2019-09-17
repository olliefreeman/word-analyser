package freeman.ollie.analysis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @since 09/09/2019
 */
public class WordAnalysisTask extends RecursiveTask<Integer> {

    private static final Logger logger = LoggerFactory.getLogger(WordAnalysisTask.class);

    public static final Pattern CLEANING_PATTERN = Pattern.compile("^" +
                                                                   "[!\"#%\\\\'()*+,./:;<=>?@\\[\\]^_`{|}~]*" +
                                                                   "(.*?)" +
                                                                   "[!\"#$\\\\'()*+,-./:;<=>?@\\[\\]^_`{|}~]*" +
                                                                   "$");

    private static final String SPLIT_PATTERN = "([ \n(])";
    //private static final Pattern DOT_SPLIT_PATTERN = Pattern.compile("^(\\p{Punct}*\\w+)([:./]+[a-zA-Z].*)+$");
    private static final Pattern DOT_SPLIT_PATTERN = Pattern.compile("^(.+\\w+)([:./]+[a-zA-Z].*)+$");

    private String word;

    public WordAnalysisTask(String word) {
        this.word = word;
    }

    @Override
    protected Integer compute() {
        return cleanWord(word).length();
    }

    public static String cleanWord(String word) {
        String cleanedWord = word.trim();
        Matcher matcher = CLEANING_PATTERN.matcher(cleanedWord);
        cleanedWord = matcher.matches() ? matcher.group(1) : "";
        logger.trace("Processing word [{}] >> Cleaned word [{}]", word, cleanedWord);
        return cleanedWord;
    }

    public static String[] splitWords(String text) {
        String[] split = text.split(SPLIT_PATTERN);
        String[] complete = new String[split.length];
        int l = 0;
        for (String s : split) {
            String[] addtlSplit = handleAdditionalSplit(s, new String[1]);
            if (addtlSplit.length != 1) complete = Arrays.copyOf(complete, complete.length + addtlSplit.length - 1);
            for (String value : addtlSplit) {
                complete[l] = value;
                l++;
            }
        }
        return complete;
    }

    private static String[] handleAdditionalSplit(String s, String[] addtl) {
        Matcher m = DOT_SPLIT_PATTERN.matcher(s);
        if (m.matches()) {
            addtl = Arrays.copyOf(addtl, addtl.length + 1);
            addtl[addtl.length - 2] = m.group(2);
            return handleAdditionalSplit(m.group(1), addtl);
        }
        addtl[addtl.length - 1] = s;
        return addtl;
    }
}
