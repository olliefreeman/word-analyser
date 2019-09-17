package freeman.ollie.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @since 10/09/2019
 */
public class Utils {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    private static final Pattern CLEANING_PATTERN = Pattern.compile("^" +
                                                                    "[!\"#%\\\\'()*+,./:;<=>?@\\[\\]^_`{|}~]*" +
                                                                    "(.*?)" +
                                                                    "[!\"#$\\\\'()*+,-./:;<=>?@\\[\\]^_`{|}~]*" +
                                                                    "$");

    private static final String SPLIT_PATTERN = "([ \n(])";
    //private static final Pattern DOT_SPLIT_PATTERN = Pattern.compile("^(\\p{Punct}*\\w+)([:./]+[a-zA-Z].*)+$");
    private static final Pattern DOT_SPLIT_PATTERN = Pattern.compile("^(.+\\w+)([:./]+[a-zA-Z].*)+$");

    public static String getTimeTakenString(long timeTaken) {

        StringBuilder sb = new StringBuilder();

        long secs = timeTaken / 1000;
        long ms = timeTaken - (secs * 1000);
        long mins = secs / 60;
        secs = secs % 60;

        if (mins > 0) {
            sb.append(mins).append(" min");
            if (mins > 1) sb.append("s");
            sb.append(" ");
        }

        if (secs > 0) {
            sb.append(secs).append(" sec");
            if (secs > 1) sb.append("s");
            sb.append(" ");
        }

        return sb.append(ms).append(" ms").toString();
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
