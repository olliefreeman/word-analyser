package freeman.ollie

import freeman.ollie.util.Utils
import groovy.transform.CompileStatic

import java.nio.file.Files
import java.nio.file.Path

/**
 * @since 16/09/2019
 */
@CompileStatic
class GroovySingleThreadedAnalyserService extends GroovyAnalyserService {

    GroovySingleThreadedAnalyserService(Path filePathToAnalyse) {
        super(filePathToAnalyse)
    }

    @Override
    protected Map<Integer, Long> performAnalysis() throws IOException {
        Utils.splitWords(Files.readString(filePathToAnalyse))
            .collect {Utils.cleanWord(it).length()}
            .findAll()
            .groupBy {it}
            .collectEntries {k, v ->
                [k, v.size() as Long]
            } as Map<Integer, Long>
    }
}
