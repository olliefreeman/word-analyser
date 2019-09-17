package freeman.ollie.analysis;

import freeman.ollie.util.Utils;

import java.util.concurrent.RecursiveTask;

/**
 * @since 09/09/2019
 */
public class WordAnalysisTask extends RecursiveTask<Integer> {

    private String word;

    public WordAnalysisTask(String word) {
        this.word = word;
    }

    @Override
    protected Integer compute() {
        return Utils.cleanWord(word).length();
    }
}
