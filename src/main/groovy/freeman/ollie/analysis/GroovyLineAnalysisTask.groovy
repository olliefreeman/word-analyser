package freeman.ollie.analysis

import freeman.ollie.util.Utils

import java.util.concurrent.RecursiveTask

/**
 * @since 10/09/2019
 */
class GroovyLineAnalysisTask extends RecursiveTask<Map<Integer, Long>> {

    private String line

    @Override
    protected Map<Integer, Long> compute() {
        Utils.splitWords(line)
            .findAll()
            .collect {new WordAnalysisTask(it).fork()}
            .groupBy {it.join()}
            .collectEntries {length, values -> [length, values.size()]}
            .findAll {it.key} as Map<Integer, Long>
    }
}
