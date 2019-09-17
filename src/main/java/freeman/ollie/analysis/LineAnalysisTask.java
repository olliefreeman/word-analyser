package freeman.ollie.analysis;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

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
        return subTasks.stream().collect(groupingBy(ForkJoinTask::join, Collectors.counting()));
    }
}
