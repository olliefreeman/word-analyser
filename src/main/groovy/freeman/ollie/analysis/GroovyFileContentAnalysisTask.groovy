package freeman.ollie.analysis

import freeman.ollie.util.Utils
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.util.concurrent.RecursiveTask

/**
 * @since 10/09/2019
 */
class GroovyFileContentAnalysisTask extends RecursiveTask<Map<Integer, Long>> {

    private static final Logger logger = LoggerFactory.getLogger(GroovyFileContentAnalysisTask)

    private List<String> fileLines

    @Override
    protected Map<Integer, Long> compute() {
        logger.info('Starting line analysis on {} lines', fileLines.size())
        long start = System.currentTimeMillis()

        List<LineAnalysisTask> tasks = fileLines.collect {new GroovyLineAnalysisTask(line: it).fork()} as List<LineAnalysisTask>

        logger.info('All lines submitted for processing, waiting for completion')
        Map<Integer, Long> results = [:]

        tasks.each {task ->
            Map<Integer, Long> taskResults = task.join()
            taskResults.each {length, count ->
                results[length] = (results[length] ?: 0) + count
            }
        }

        logger.info('All lines processed in {}', Utils.getTimeTakenString(System.currentTimeMillis() - start))
        results
    }
}
