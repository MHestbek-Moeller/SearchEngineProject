package searchengine;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.*;

/**
 * Testing for the Query Handler
 * Parameters filename, invertedIndex, searchEngine and queryHandler are used
 */
@TestInstance(Lifecycle.PER_CLASS)
class QueryHandlerTest {
        String filename = "data/test-file.txt";
        Map<String, Set<Page>> invertedIndex;
        AdvancedQueryEngine searchengine;
        QueryHandler queryHandler;

/**
 * setting up the parameter of a page
 */        
    @BeforeAll
    private void setUp() throws IOException{
        searchengine = new AdvancedQueryEngine(filename);
        invertedIndex = searchengine.invertedIndex;
        queryHandler = new QueryHandler(invertedIndex);
    }

/**
 * test the Query Handler with a valid parameter
 */
    @Test
    public void testQueryHandlerParameters(){
        assertNotNull(queryHandler);
    }

/**
 * tests Query Handler with one word 'word2'
 * @throws IOException if there is an error, i.e. if length doesn't equal 1
 */
    @Test
    public void testQueryHandlerOneWord() throws IOException{
        Set<Page> searchResults = queryHandler.getMatchingWebPages("word2", invertedIndex);
        int length = searchResults.size();
        List<String> expectedResults = Arrays.asList("title1");
        assertTrue(length==1);
        for (Page pages : searchResults){
            boolean hasTitle = false;
            if(expectedResults.contains(pages.getTitle())) hasTitle = true;
            assertTrue(hasTitle);
        }}

/**
 * tests Query Handler with no word (empty string)
 */
    @Test
    public void testQueryHandlerNoWord(){
        Set<Page> searchResults = queryHandler.getMatchingWebPages("", invertedIndex);
        int length = searchResults.size();
        List<String> expectedResults = Arrays.asList();
        assertTrue(length==0);
        for (Page pages : searchResults){
            boolean hasTitle = false;
            if(expectedResults.contains(pages.getTitle())) hasTitle = true;
            assertTrue(hasTitle);
    }}

/**
 * tests for successful intersection of 'word1' and 'word2'
 */
    @Test
    public void testQueryHandlerIntersection(){
        Set<Page> searchResults = queryHandler.getMatchingWebPages("word1%20word2", invertedIndex);
        int length = searchResults.size();
        List<String> expectedResults = Arrays.asList("title1");
        assertTrue(length==1);
        for (Page pages : searchResults){
            boolean hasTitle = false;
            if(expectedResults.contains(pages.getTitle())) hasTitle = true;
            assertTrue(hasTitle);
        }}

/**
 * tests for empty intersection of 'word5' and 'word6'
 */
    @Test
    public void testQueryHandlerNoIntersection(){
        Set<Page> searchResults = queryHandler.getMatchingWebPages("word5%20word6", invertedIndex);
        int length = searchResults.size();
        List<String> expectedResults = Arrays.asList();
        assertTrue(length==0);
        for (Page pages : searchResults){
            boolean hasTitle = false;
            if(expectedResults.contains(pages.getTitle())) hasTitle = true;
            assertTrue(hasTitle);
    }}

/**
* tests for successful union of 'word1' and 'word3'
*/
    @Test
    public void testQueryHandlerOrWords(){
        Set<Page> searchResults = queryHandler.getMatchingWebPages("word1%20or%20word3", invertedIndex);
        int length = searchResults.size();
        List<String> expectedResults = Arrays.asList("title1", "title2");
        assertTrue(length==2);
        for (Page pages : searchResults){
            boolean hasTitle = false;
            if(expectedResults.contains(pages.getTitle())) hasTitle = true;
            assertTrue(hasTitle);
    }}

/**
* tests union with empty query
*/
    @Test
    public void testQueryHandlerEmptyOr(){
        Set<Page> searchResults = queryHandler.getMatchingWebPages("%20or%20", invertedIndex);
        int length = searchResults.size();
        List<String> expectedResults = Arrays.asList();
        assertTrue(length==0);
        for (Page pages : searchResults){
            boolean hasTitle = false;
            if(expectedResults.contains(pages.getTitle())) hasTitle = true;
            assertTrue(hasTitle);
    }}

/**
 * tests for both intersection and union 
 */
    @Test
    public void testQueryHandlerIntersectionAndUnion(){
        Set<Page> searchResults = queryHandler.getMatchingWebPages("word1%20word2%20or%20word3%20", invertedIndex);
        int length = searchResults.size();
        List<String> expectedResults = Arrays.asList("title1", "title2");
        assertTrue(length==2);
        for (Page pages : searchResults){
            boolean hasTitle = false;
            if(expectedResults.contains(pages.getTitle())) hasTitle = true;
            assertTrue(hasTitle);
    }}

/**
 * tests for invalid query input 
 */
    @Test
    public void testQueryHandlerInvalidInput(){
        Set<Page> searchResults = queryHandler.getMatchingWebPages("invalid", invertedIndex);
        int length = searchResults.size();
        List<String> expectedResults = Arrays.asList();
        assertTrue(length==0);
        for (Page pages : searchResults){
            boolean hasTitle = false;
            if(expectedResults.contains(pages.getTitle())) hasTitle = true;
            assertTrue(hasTitle);
    }}

}