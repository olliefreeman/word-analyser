package freeman.ollie;

import freeman.ollie.exception.WordAnalyserException;
import freeman.ollie.util.Service;
import freeman.ollie.util.Utils;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @since 16/09/2019
 */
public class StreamingAnalyserServiceTest extends BaseAnalyserServiceTest {

    @Test
    public void benchmarkWithStreaming() throws WordAnalyserException {
        Service service = new StreamingAnalyserService(Paths.get("src/test/resources/bible.txt"), StreamingAnalyserService.Method.STREAMING);

        List<Long> times = new ArrayList<>();

        for (int i = 0; i < getBenchmarkTestCount(); i++) {
            long start = System.currentTimeMillis();
            service.analyse();
            times.add(System.currentTimeMillis() - start);
        }

        double total = times.stream().mapToDouble(Long::doubleValue).sum();
        double average = total / getBenchmarkTestCount();
        logger.warn("Benchmark for " + getClass().getSimpleName() + " is [" + DECIMAL_FORMAT.format(average) + "ms] " +
                    Utils.getTimeTakenString(((long) average)));

    }

    @Override
    Service getService(Path path) {
        return new StreamingAnalyserService(path);
    }
}