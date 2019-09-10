package freeman.ollie.analysis


import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.util.concurrent.RecursiveTask

/**
 * @since 10/09/2019
 */
class GroovyLineAnalysisTask extends RecursiveTask<Map<Integer, Long>> {

    private static final Logger logger = LoggerFactory.getLogger(LineAnalysisTask)

    private String line

    @Override
    protected Map<Integer, Long> compute() {
        logger.debug('Processing line [{}]', line)
        line.split()
            .findAll()
            .collect {new GroovyWordAnalysisTask(word: it).fork()}
            .groupBy {it.join()}
            .collectEntries {length, values -> [length, values.size()]}
            .findAll {it.key} as Map<Integer, Long>
    }
}
