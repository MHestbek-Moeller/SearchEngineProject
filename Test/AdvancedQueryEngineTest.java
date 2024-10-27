package searchengine;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.util.*;

/**
 * Testing for the advanced query engine. 
 * Parameters such as filename, searchEngine and an emptyEngine are used
 */

@TestInstance(Lifecycle.PER_CLASS)
class AdvancedQueryEngineTest {
    String filename = "data/test-file.txt";
    AdvancedQueryEngine searchEngine;
    AdvancedQueryEngine emptyEngine;

    /**
     * search Engine is created
     * @throws IOException if input or output has errors
     */
    @BeforeAll
    private void setUp() throws IOException{
        searchEngine = new AdvancedQueryEngine(filename);
    }

    /**
     * Tears down the file so it does not keep running once tests are executed
     */
    @AfterAll
    void tearDown(){
        searchEngine = null;}

    /**
     * Test for valid parameters
     */
    @Test
    public void testForValidParam(){
        assertNotNull(searchEngine);
        assertNotNull(searchEngine.invertedIndex);
        assertNotNull(searchEngine.queryHandler);
    }

    /**
     * Test for invalid parameters
     */
    @Test
    public void testForInvalidParam(){
        assertNull(emptyEngine);
    }

    /**
     * Test for reading in the file
     * @throws IOException if input or output has errors, for example, if the file is not existing
     */
    @Test
    public void testReadTestFileCorrect() throws IOException{
        List<Page> pages = searchEngine.readPages("data/test-file.txt");
        assertNotNull(pages);
    }

    /**
     * Test for reading in a file
     * @throws IOException if input or output has errors, for example, if the file is not existing
     */
    @Test
    public void testReadTinyFileCorrect() throws IOException{
        List<Page> pages = searchEngine.readPages("data/enwiki-tiny.txt");
        assertNotNull(pages);
    }

    /**
     * Test for reading in a file
     * @throws IOException if input or output has errors, for example, if the file is not existing
     */
    @Test
    public void testReadSmallFileCorrect() throws IOException{
        List<Page> pages = searchEngine.readPages("data/enwiki-small.txt");
        assertNotNull(pages);
    }

    /**
     * Test for searching for one word
     */
    @Test
    public void returnQueryWord(){
        List<Page> results = searchEngine.search("word1");
        assertEquals(2, results.size());
    }

    /**
     * Test for searching for one word
     */
    @Test
    public void returnQueryWord2(){
        List<Page> results = searchEngine.search("word2");
        assertEquals(1, results.size());
    }

    /**
     * Test for empty search
     */
    @Test
    public void returnInvalidWord(){
        List<Page> results = searchEngine.search(" ");
        assertEquals(0, results.size());
    }

    /**
     * Test for searching for two words
     */
    @Test
    public void returnAndQueryWords(){
        List<Page> results = searchEngine.search("word1%20word2");
        assertEquals(1, results.size());
        }

     /**
     * Test for searching for one or another word
     */
    @Test
    public void returnOrQueryWords(){
        List<Page> results = searchEngine.search("word1%20or%20word2");
        assertEquals(2, results.size());
        }

     /**
     * Test for searching for two or one word
     */
    @Test
    public void returnOrPlusAndQueryWords(){
        List<Page> results = searchEngine.search("word1%20word2%20or%20word3%20");
        assertEquals(2, results.size());
        }

}
