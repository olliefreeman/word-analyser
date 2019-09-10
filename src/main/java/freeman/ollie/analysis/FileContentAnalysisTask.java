package freeman.ollie.analysis;

import freeman.ollie.util.Utils;
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

        logger.info("All lines processed in {}", Utils.getTimeTakenString(System.currentTimeMillis() - start));
        return results;
    }
}
