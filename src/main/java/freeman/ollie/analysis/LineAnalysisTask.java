package freeman.ollie.analysis;

import com.google.common.base.Strings;
import freeman.ollie.util.Utils;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * @since 09/09/2019
 */
public class LineAnalysisTask extends RecursiveTask<Map<Integer, Long>> {

    private String line;

    public LineAnalysisTask(String line) {
        this.line = line;
    }

    @Override
    protected Map<Integer, Long> compute() {
        return Arrays.stream(Utils.splitWords(line))
            .filter(w -> !Strings.isNullOrEmpty(w))
            .map(w -> ((WordAnalysisTask) new WordAnalysisTask(w).fork()))
            .map(ForkJoinTask::join)
            .filter(l -> l != 0)
            .collect(groupingBy(Function.identity(), Collectors.counting()));
    }
}
