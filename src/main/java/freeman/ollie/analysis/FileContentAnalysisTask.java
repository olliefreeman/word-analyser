package freeman.ollie.analysis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

/**
 * @since 09/09/2019
 */
public class FileContentAnalysisTask extends RecursiveTask<Map<Integer, Long>> {

    private static final Logger logger = LoggerFactory.getLogger(FileContentAnalysisTask.class);

    private List<String> fileLines;

    public FileContentAnalysisTask(List<String> fileLines) {
        this.fileLines = fileLines;
    }

    @Override
    protected Map<Integer, Long> compute() {

        logger.info("Starting line analysis on {} lines", fileLines.size());
        long start = System.currentTimeMillis();
        List<LineAnalysisTask> tasks = fileLines.stream().map(LineAnalysisTask::new).collect(Collectors.toList());
        tasks.forEach(ForkJoinTask::fork);

        logger.info("All lines submitted for processing, waiting for completion");
        Map<Integer, Long> results = new HashMap<>();
        tasks.forEach(task -> {
            Map<Integer, Long> taskResults = task.join();
            taskResults.forEach((length, count) ->
                                    results.compute(length, (k, v) -> v == null ? count : v + count)
                               );
        });

        logger.info("All lines processed in {}", getTimeTakenString(System.currentTimeMillis() - start));
        return results;
    }


    private static String getTimeTakenString(long timeTaken) {

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
