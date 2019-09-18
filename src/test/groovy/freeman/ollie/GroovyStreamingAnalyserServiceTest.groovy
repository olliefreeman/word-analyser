package freeman.ollie


import freeman.ollie.util.Service

import java.nio.file.Path

/**
 * @since 09/09/2019
 */
class GroovyStreamingAnalyserServiceTest extends BaseAnalyserServiceTest {

    @Override
    Service getService(Path path) {
        new GroovyStreamingAnalyserService(path)
    }

    @Override
    int getBenchmarkTestCount(){
        10
    }
}