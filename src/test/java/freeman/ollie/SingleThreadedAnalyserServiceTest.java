package freeman.ollie;

import freeman.ollie.util.Service;

import java.nio.file.Path;

/**
 * @since 16/09/2019
 */
public class SingleThreadedAnalyserServiceTest extends BaseAnalyserServiceTest {

    @Override
    Service getService(Path path) {
        return new SingleThreadedAnalyserService(path);
    }
}