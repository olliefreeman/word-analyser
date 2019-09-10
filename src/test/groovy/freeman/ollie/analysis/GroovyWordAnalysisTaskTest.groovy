package freeman.ollie.analysis

import org.junit.Test

import static org.junit.Assert.assertEquals

/**
 * @since 09/09/2019
 */
class GroovyWordAnalysisTaskTest {

    @Test
    void testCleaning() {
        assertEquals('hello', GroovyWordAnalysisTask.cleanWord('hello'))
        assertEquals('hello', GroovyWordAnalysisTask.cleanWord('.hello'))
        assertEquals('hello', GroovyWordAnalysisTask.cleanWord('hello.'))
        assertEquals('hello', GroovyWordAnalysisTask.cleanWord('hello,'))
        assertEquals('hello', GroovyWordAnalysisTask.cleanWord('(hello'))
        assertEquals('hello', GroovyWordAnalysisTask.cleanWord('hello)'))
        assertEquals('hello', GroovyWordAnalysisTask.cleanWord('hello).'))

        assertEquals('11', GroovyWordAnalysisTask.cleanWord('11'))
        assertEquals('£11', GroovyWordAnalysisTask.cleanWord('£11'))
        assertEquals('11%', GroovyWordAnalysisTask.cleanWord('11%'))
        assertEquals('11.0', GroovyWordAnalysisTask.cleanWord('11.0'))
        assertEquals('11.0%', GroovyWordAnalysisTask.cleanWord('11.0%'))
        assertEquals('18/05/2016', GroovyWordAnalysisTask.cleanWord('18/05/2016'))
        assertEquals('&', GroovyWordAnalysisTask.cleanWord('&'))
    }

    @Test
    void testCompute() {
        assertEquals(5, new GroovyWordAnalysisTask(word: 'hello').compute())
        assertEquals(5, new GroovyWordAnalysisTask(word: '.hello').compute())
        assertEquals(5, new GroovyWordAnalysisTask(word: 'hello.').compute())
        assertEquals(5, new GroovyWordAnalysisTask(word: 'hello,').compute())
        assertEquals(5, new GroovyWordAnalysisTask(word: '(hello').compute())
        assertEquals(5, new GroovyWordAnalysisTask(word: 'hello)').compute())

        assertEquals(2, new GroovyWordAnalysisTask(word: '11').compute())
        assertEquals(3, new GroovyWordAnalysisTask(word: '£11').compute())
        assertEquals(3, new GroovyWordAnalysisTask(word: '11%').compute())
        assertEquals(4, new GroovyWordAnalysisTask(word: '11.0').compute())
        assertEquals(5, new GroovyWordAnalysisTask(word: '11.0%').compute())
        assertEquals(10, new GroovyWordAnalysisTask(word: '18/05/2016').compute())
        assertEquals(1, new GroovyWordAnalysisTask(word: '&').compute())
    }
}