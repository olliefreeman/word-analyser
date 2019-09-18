package freeman.ollie.analysis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

/**
 * @since 09/09/2019
 */
public class FileContentAnalysisTask extends RecursiveTask<Map<Integer, Long>> {

    private static final Logger logger = LoggerFactory.getLogger(FileContentAnalysisTask.class);

    private List<String> fileLines;
    private boolean forkWordCleaning;

    public FileContentAnalysisTask(List<String> fileLines) {
        this(fileLines, true);
    }

    public FileContentAnalysisTask(List<String> fileLines, boolean forkWordCleaning) {
        this.fileLines = fileLines;
        this.forkWordCleaning = forkWordCleaning;
    }

    @Override
    protected Map<Integer, Long> compute() {

        logger.info("Starting line analysis on {} lines", fileLines.size());
        List<LineAnalysisTask> tasks = fileLines
            .stream()
            .map(l -> ((LineAnalysisTask) new LineAnalysisTask(l, forkWordCleaning).fork()))
            .collect(Collectors.toList());

        logger.info("All lines submitted for processing, waiting for completion");
        Map<Integer, Long> results = new HashMap<>();
        tasks.forEach(task -> {
            Map<Integer, Long> taskResults = task.join();
            taskResults.forEach((length, count) ->
                                    results.compute(length, (k, v) -> v == null ? count : v + count)
                               );
        });

        logger.info("All lines processed");
        return results;
    }
}
