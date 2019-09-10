package freeman.ollie.util;

/**
 * @since 10/09/2019
 */
public class Utils {

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
}
