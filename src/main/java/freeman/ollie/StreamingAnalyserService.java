package freeman.ollie;

import freeman.ollie.exception.WordAnalyserException;
import freeman.ollie.util.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * @since 16/09/2019
 */
public class StreamingAnalyserService extends AnalyserService {

    private Method method;

    public StreamingAnalyserService(Path filePathToAnalyse) {
        super(filePathToAnalyse);
        method = Method.LINE_STREAMING;
    }

    public StreamingAnalyserService(Path filePathToAnalyse, Method method) {
        super(filePathToAnalyse);
        this.method = method;
    }

    @Override
    protected Map<Integer, Long> performAnalysis() throws IOException, WordAnalyserException {
        switch (method) {
            case STREAMING:
                return performStreamingAnalysis();
            case LINE_STREAMING:
                return performLineStreamingAnalysis();
        }
        throw new WordAnalyserException("No method set for single threaded analysis");
    }

    private Map<Integer, Long> performLineStreamingAnalysis() throws IOException {
        return Files.readAllLines(filePathToAnalyse)
            .parallelStream()
            .map(Utils::splitWords)
            .flatMap(Arrays::stream)
            .map(w -> Utils.cleanWord(w).length())
            .filter(l -> l != 0)
            .collect(groupingBy(Function.identity(), Collectors.counting()));
    }

    private Map<Integer, Long> performStreamingAnalysis() throws IOException {
        return Arrays.stream(Utils.splitWords(Files.readString(filePathToAnalyse)))
            .parallel()
            .map(w -> Utils.cleanWord(w).length())
            .filter(l -> l != 0)
            .collect(groupingBy(Function.identity(), Collectors.counting()));
    }


    public enum Method {
        STREAMING,
        LINE_STREAMING
    }
}
