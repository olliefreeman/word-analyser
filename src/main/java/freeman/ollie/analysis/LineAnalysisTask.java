package freeman.ollie.analysis;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

/**
 * @since 09/09/2019
 */
public class LineAnalysisTask extends RecursiveTask<Map<Integer, Long>> {

    private static final Logger logger = LoggerFactory.getLogger(LineAnalysisTask.class);

    private String line;

    public LineAnalysisTask(String line) {
        this.line = line;
    }

    @Override
    protected Map<Integer, Long> compute() {
        logger.debug("Processing line [{}]", line);
        String[] words = line.split(" ");

        List<WordAnalysisTask> subTasks =
            Arrays.stream(words)
                .filter(w -> !Strings.isNullOrEmpty(w))
                .map(WordAnalysisTask::new)
                .collect(Collectors.toList());
        subTasks.forEach(ForkJoinTask::fork);

        Map<Integer, Long> results = new HashMap<>();

        subTasks.forEach(task -> {
                             Integer length = task.join();
                             if (length != 0) {
                                 results.compute(length, (k, v) -> v == null ? 1 : ++v);
                             }
                         }
                        );

        return results;
    }
}
