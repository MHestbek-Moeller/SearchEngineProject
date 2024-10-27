package searchengine;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.*;

/**
 * Test the contents of each page
 * The parameters validURL, validTitle and validWords are used 
 */
@TestInstance(Lifecycle.PER_CLASS)
class PageTest {
        private String validURL;
        private String validTitle;
        private List<String> validWords;

    /**
     * sets up the parameters for a page
     */
    @BeforeAll
    private void setUp(){
        validURL = "https://en.wikipedia.org/wiki/Denmark";
        validTitle = "Denmark";
        validWords = new ArrayList<>();
        validWords.add("Denmark");
        validWords.add("kingdom");
        validWords.add("Queen");
    }
    
    /**
     * testing for valid parameters
     */
    @Test
    public void testForValidParameters(){
        Page page = new Page(validURL, validTitle, validWords);
        assertNotNull(page);
        assertEquals(validURL, page.getURL());
        assertEquals(validTitle, page.getTitle());
        assertEquals(validWords, page.getWords());
    }

    /**
     * Testing for the getWords method
     */
    @Test
    public void testForValidContent(){
        Page page = new Page(validURL, validTitle, validWords);
        assertNotNull(page);
        assertEquals(validWords, page.getWords());
    }

     /**
     * Testing for the getURL method
     */
    @Test
    public void testForValidURL(){
        Page page = new Page(validURL, validTitle, validWords);
        assertEquals(validURL, page.getURL());
    }

     /**
     * Testing for the getTitle method
     */
    @Test
    public void testForValidTitle(){
        Page page = new Page(validURL, validTitle, validWords);
        assertEquals(validTitle, page.getTitle());
    }}

