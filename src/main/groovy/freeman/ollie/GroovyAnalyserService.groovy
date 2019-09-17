package freeman.ollie


import freeman.ollie.analysis.GroovyFileContentAnalysisTask
import freeman.ollie.exception.WordAnalyserException
import freeman.ollie.util.Service
import freeman.ollie.util.Utils
import groovy.transform.CompileStatic
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.nio.file.Files
import java.nio.file.Path
import java.text.DecimalFormat
import java.util.concurrent.ForkJoinPool

/**
 * @since 10/09/2019
 */
@CompileStatic
class GroovyAnalyserService implements Service {

    private static final Logger logger = LoggerFactory.getLogger(GroovyAnalyserService)
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat('#.###')

    protected Path filePathToAnalyse
    private Map<Integer, Long> results

    GroovyAnalyserService(Path filePathToAnalyse) {
        this.filePathToAnalyse = filePathToAnalyse
    }

    @Override
    Map<Integer, Long> getResults() {
        results
    }

    protected Map<Integer, Long> performAnalysis() throws IOException {
        List<String> fileLines = Files.readAllLines(filePathToAnalyse)
        ForkJoinPool pool = ForkJoinPool.commonPool()
        logger.info('Running with parallelism: {}', pool.getParallelism())
        return pool.invoke(new GroovyFileContentAnalysisTask(fileLines: fileLines))
    }

    @Override
    void analyse() throws WordAnalyserException {
        if (!Files.exists(filePathToAnalyse)) {
            throw new WordAnalyserException("No file found at provided path [${filePathToAnalyse.toString()}]")
        }

        try {
            logger.info('Reading in file [{}]', filePathToAnalyse.toString())
            long start = System.currentTimeMillis()
            results = performAnalysis()
            logger.info('Analysis complete in {}', Utils.getTimeTakenString(System.currentTimeMillis() - start))
        } catch (Exception ioe) {
            throw new WordAnalyserException("Could not analyse file", ioe);
        }
    }

    @Override
    String getAnalysisString() {
        StringBuilder sb = new StringBuilder()

        sb.append("Word count = ${totalNumberOfWords}\n")
        sb.append("Average word length = ${DECIMAL_FORMAT.format(averageWordLength)}\n")
        sb.append(results.sort().collect {k, v -> "Number of words of length ${k} is ${v}"}.join('\n')).append('\n')

        Map.Entry<Long, List<Integer>> mostUsed = getMostUsedWordLength()
        sb.append("The most frequently occurring word length is ${mostUsed.key}, for word lengths of ")

        mostUsed.value.eachWithIndex {int e, int i ->
            sb.append(e)
            if (i < mostUsed.value.size() - 2) {
                sb.append(", ")
            } else if (i == mostUsed.value.size() - 2) {
                sb.append(' & ')
            }
        }

        sb.toString()
    }

    @Override
    double getAverageWordLength() {
        (results.collect {k, v -> k * v}.sum() as float) / totalNumberOfWords
    }

    @Override
    Map.Entry<Long, List<Integer>> getMostUsedWordLength() {
        results.groupBy {it.value}.collectEntries {[it.key, it.value.keySet().sort()]}.max {it.key} as Map.Entry<Long, List<Integer>>
    }

    @Override
    long getTotalNumberOfWords() {
        results.values().sum() as long
    }
}
