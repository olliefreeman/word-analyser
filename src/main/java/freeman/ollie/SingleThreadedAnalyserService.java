package freeman.ollie;

import freeman.ollie.util.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * @since 16/09/2019
 */
public class SingleThreadedAnalyserService extends AnalyserService {

    public SingleThreadedAnalyserService(Path filePathToAnalyse) {
        super(filePathToAnalyse);
    }

    @Override
    protected Map<Integer, Long> performAnalysis() throws IOException {
        List<String> splitWords = Arrays.asList(Utils.splitWords(Files.readString(filePathToAnalyse)));

        return splitWords.parallelStream()
            .map(w -> Utils.cleanWord(w).length())
            .filter(l -> l != 0)
            .collect(groupingBy(Function.identity(), Collectors.counting()));
    }
}
