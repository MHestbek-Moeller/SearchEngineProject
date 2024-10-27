package searchengine;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.*;

/**
 * Testing for the Inverted Index. 
 * Parameters invertedIndex, validIndex, emptyIndex, validFile, Search 
 * Engine and Pages are used
 */
@TestInstance(Lifecycle.PER_CLASS)
class InvertedIndexTest {
    InvertedIndex invertedIndex = new InvertedIndex();
    Map<String, Set<Page>> validIndex;
    Map<String, Set<Page>> emptyIndex;
    String validFile = "data/test-file.txt";
    AdvancedQueryEngine SearchEngine;
    List<Page> Pages;
    
       
/**
* search Engine is created
* @throws IOException if there is an error with reading in the validFile
*/
    @BeforeAll
    void setup() throws IOException{
        SearchEngine = new AdvancedQueryEngine(validFile);
        Pages = SearchEngine.readPages(validFile);
        validIndex = invertedIndex.indexPages(Pages);  
    }
    
/**
* Tears down the file so it does not keep running once tests are executed
*/
    @AfterAll
    void tearDown(){
        SearchEngine = null;
        validIndex = null;
    }

    /**
     * test for valid parameters
     */
    @Test
    public void testForValidParam(){
        assertNotNull(validIndex);
        assertEquals(3, validIndex.keySet().size());
    }

    /**
     * test for invalid parameters
     */
    @Test
    public void testForInvalidParam(){
        assertNull(emptyIndex);
    }

     /**
     * test for valid page indexing
     */
    @Test
    public void testForValidIndexPages(){
        assertNotNull(Pages);
        assertNotNull(validIndex.get("word1"));
    }

/**
 * test for invalid index 
 */
    @Test
    public void testForInvalidIndexPages(){
        assertNull(emptyIndex);
    }

/**
 * test if invertedIndex works when it recieves 'word1'
 */
    @Test
    public void testForWordLookup(){
       Set<Page> searchResults = invertedIndex.lookupInvertedIndex("word1");
       int length = searchResults.size();
       List<String> expectedResults = Arrays.asList("title1", "title2");
       assertTrue(length==2);
       for(Page pages:searchResults){
        boolean hasTitle = false;
        if(expectedResults.contains(pages.getTitle())) hasTitle = true;
        assertTrue(hasTitle);
       }}

/**
 * test if invertedIndex works when it recieves 'word2'
 */
       @Test
       public void testForWordLookup2(){
        Set<Page> searchResults = invertedIndex.lookupInvertedIndex("word2");
        int length = searchResults.size();
        List<String> expectedResults = Arrays.asList("title1");
        assertTrue(length==1);
        for(Page pages:searchResults){
         boolean hasTitle = false;
         if(expectedResults.contains(pages.getTitle())) hasTitle = true;
         assertTrue(hasTitle);
        }}

/**
 * test if invertedIndex works when it recieves 'word3'
 */
        @Test
       public void testForWordLookup3(){
        Set<Page> searchResults = invertedIndex.lookupInvertedIndex("word3");
        int length = searchResults.size();
        List<String> expectedResults = Arrays.asList("title2");
        assertTrue(length==1);
        for(Page pages:searchResults){
         boolean hasTitle = false;
         if(expectedResults.contains(pages.getTitle())) hasTitle = true;
         assertTrue(hasTitle);
        }}

/**
 * test if invertedIndex recieves no search word
 */
        @Test
        public void testForEmptyLookup(){
        Set<Page> searchResults = invertedIndex.lookupInvertedIndex("");
        int length = searchResults.size();
        List<String> expectedResults = Arrays.asList();
        assertTrue(length==0);
        for(Page pages:searchResults){
         boolean hasTitle = false;
         if(expectedResults.contains(pages.getTitle())) hasTitle = true;
         assertTrue(hasTitle);
        }}
    }