package freeman.ollie;

import freeman.ollie.util.Service;

import java.nio.file.Path;

/**
 * @since 09/09/2019
 */
public class AnalyserServiceTest extends BaseAnalyserServiceTest {

    @Override
    Service getService(Path path) {
        return new AnalyserService(path);
    }
}