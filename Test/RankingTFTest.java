package searchengine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;

public class RankingTFTest {
    /**
     * Initialising the pages that will be used for testing
     * @return
     */
    List<Page> getData() {
        return Arrays.asList(
                new Page("URL0", "Title0", List.of("word1", "word1", "word1", "word1", "word2")),
                new Page("URL1", "Title1", List.of("word1", "word2", "word2", "word2", "word3", "word4")),
                new Page("URL2", "Title2", List.of("word1", "word3", "word3", "word1", "word4", "word2")),
                new Page("URL3", "Title3", List.of("word1", "word3", "word3", "word1", "word4")),
                new Page("URL4", "Title4", List.of("word2", "word1", "word2")),
                new Page("URL5", "Title5", List.of("word1", "word2", "word4", "word2")),
                new Page("URL6", "Title6", List.of("word1", "word3", "word1","word4","word2", "word3","word4")));
    }
    /**
     * Testing the rankingTF on one word
     */
    @Test
    public void testRankPagesOneWord() {
        // Input preparation
        List<Page> input = getData();

        // System preparation
        Ranking ranking = new RankingTF(input);

        // Test
        List<Page> output = ranking.rankPages(input, "word1");
        

        // Verification//
        assertEquals("Title0", output.get(0).getTitle());
        assertEquals("Title3", output.get(1).getTitle());
        assertEquals("Title2", output.get(2).getTitle());
        assertEquals("Title4", output.get(3).getTitle());
        assertEquals("Title6", output.get(4).getTitle());
        assertEquals("Title5", output.get(5).getTitle());
        assertEquals("Title1", output.get(6).getTitle());
        
    }
    /**
     * Testing the rankingTF on 2 words split by whitespace
     */
    @Test
    public void testRankPagesTwoWords() {
        // Input preparation
        List<Page> input = getData();
        String expectedTitles[] = {"Title1","Title2","Title6"};
        List<String> expectedTitlesList = Arrays.asList(expectedTitles);

        // System preparation
        Ranking ranking = new RankingTF(input);

        // Test
        List<Page> output = ranking.rankPages(input, "word1%20word2");
        

        // Verification//
        

        assertEquals("Title0", output.get(0).getTitle());
        assertEquals("Title4", output.get(1).getTitle());
        assertEquals("Title5", output.get(2).getTitle());
        assertTrue(expectedTitlesList.contains((output.get(3).getTitle())));
        assertTrue(expectedTitlesList.contains((output.get(4).getTitle())));
        assertTrue(expectedTitlesList.contains((output.get(5).getTitle())));
        assertEquals("Title3", output.get(6).getTitle());

    }
    /**
     * Testing the ranking of 2 words separated by OR 
     */
    @Test
    public void testRankPagesOrQuery() {
         // Input preparation
         List<Page> input = getData();
         String expectedTitles[] = {"Title1","Title5"};
         List<String> expectedTitlesList = Arrays.asList(expectedTitles);
 
         // System preparation
         Ranking ranking = new RankingTF(input);
 
         // Test
         List<Page> output = ranking.rankPages(input, "word1%20or%20word2");
         
 
         // Verification//
         
 
         assertEquals("Title0", output.get(0).getTitle());
         assertEquals("Title4", output.get(1).getTitle());
         assertTrue(expectedTitlesList.contains((output.get(2).getTitle())));
         assertTrue(expectedTitlesList.contains((output.get(3).getTitle())));
         assertEquals("Title3", output.get(4).getTitle());
         assertEquals("Title2", output.get(5).getTitle());
         assertEquals("Title6", output.get(6).getTitle());

    }
        /**
         * Testing the ranking of multple words seperated by white spces and OR 
         */
    @Test
    public void testRankPagesMultipleWordOrQuery() {
         // Input preparation
         List<Page> input = getData();
 
         // System preparation
         Ranking ranking = new RankingTF(input);
 
         // Test
         List<Page> output = ranking.rankPages(input, "word1%20word2%20or%20word3%20word4");
         
 
         // Verification//
         assertEquals("Title0", output.get(0).getTitle());
         assertEquals("Title4", output.get(1).getTitle());
         assertEquals("Title5", output.get(2).getTitle());
         assertEquals("Title1", output.get(3).getTitle());
         assertEquals("Title3", output.get(4).getTitle());
         assertEquals("Title6", output.get(5).getTitle());
         assertEquals("Title2", output.get(6).getTitle());

    }

}
