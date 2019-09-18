package freeman.ollie;

import freeman.ollie.analysis.FileContentAnalysisTask;
import freeman.ollie.exception.WordAnalyserException;
import freeman.ollie.util.Service;
import freeman.ollie.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

/**
 * @since 09/09/2019
 */
public class AnalyserService implements Service {

    protected static final Logger logger = LoggerFactory.getLogger(AnalyserService.class);
    protected static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.###");
    protected Path filePathToAnalyse;
    protected Map<Integer, Long> results;
    private boolean forkWordCleaning;

    public AnalyserService(Path filePathToAnalyse) {
        this(filePathToAnalyse, false);
    }

    public AnalyserService(Path filePathToAnalyse, boolean forkWordCleaning) {
        this.filePathToAnalyse = filePathToAnalyse.toAbsolutePath().normalize();
        this.forkWordCleaning = forkWordCleaning;
    }

    @Override
    public Map<Integer, Long> getResults() {
        return results;
    }

    @Override
    public void analyse() throws WordAnalyserException {
        if (!Files.exists(filePathToAnalyse)) {
            throw new WordAnalyserException("No file found at provided path [" + filePathToAnalyse.toString() + "]");
        }

        try {
            long start = System.currentTimeMillis();
            logger.info("Reading in file [{}]", filePathToAnalyse.toString());
            results = performAnalysis();
            logger.info("Analysis complete in {}", Utils.getTimeTakenString(System.currentTimeMillis() - start));
        } catch (Exception ioe) {
            throw new WordAnalyserException("Could not analyse file", ioe);
        }
    }

    @Override
    public String getAnalysisString() {

        StringBuilder sb = new StringBuilder();

        sb.append("Word count = ").append(getTotalNumberOfWords()).append("\n");
        sb.append("Average word length = ").append(DECIMAL_FORMAT.format(getAverageWordLength())).append("\n");

        results.forEach((k, v) -> sb.append("Number of words of length ").append(k).append(" is ").append(v).append("\n"));

        Map.Entry<Long, List<Integer>> mostUsed = getMostUsedWordLength();
        sb.append("The most frequently occurring word length is ")
            .append(mostUsed.getKey())
            .append(", for word lengths of ")
            .append(mostUsed.getValue().get(0));
        List<Integer> value = mostUsed.getValue();
        for (int i = 1; i < value.size(); i++) {
            if (i == value.size() - 1) {
                sb.append(" & ");
            } else {
                sb.append(", ");
            }
            sb.append(value.get(i));
        }

        return sb.toString();
    }

    @Override
    public double getAverageWordLength() {
        double total = results.entrySet().stream().mapToDouble(value -> value.getKey() * value.getValue()).sum();
        return total / getTotalNumberOfWords();
    }

    @Override
    public Map.Entry<Long, List<Integer>> getMostUsedWordLength() {
        Map<Long, List<Integer>> pivotedData = new HashMap<>();

        results.forEach((length, count) ->
                            pivotedData.compute(count, (k, lengths) -> {
                                if (lengths == null) lengths = new ArrayList<>();
                                lengths.add(length);
                                return lengths;
                            })
                       );
        return pivotedData.entrySet().stream().max(Comparator.comparingLong(Map.Entry::getKey)).get();
    }

    @Override
    public long getTotalNumberOfWords() {
        return results.values().stream().mapToLong(Long::longValue).sum();
    }

    protected Map<Integer, Long> performAnalysis() throws IOException, WordAnalyserException {
        List<String> fileLines = Files.readAllLines(filePathToAnalyse);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        logger.info("Running with parallelism: {}", pool.getParallelism());
        FileContentAnalysisTask task = new FileContentAnalysisTask(fileLines, forkWordCleaning);
        return pool.invoke(task);
    }
}
