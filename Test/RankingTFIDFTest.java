package searchengine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;

public class RankingTFIDFTest {

    /**
     *  Initializing the pages the testing is based on
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
                new Page("URL6", "Title6", List.of("word1", "word3", "word1", "word4", "word2", "word3", "word4")));
    }
    /**
     * Testing if the amount of pages(Lenght of array) are calculated correct
     */
    @Test
    public void testPageLength() {
        // Input preparation
        List<Page> pages = getData();

        assertEquals(pages.size(), 7);
    }
    /**
     * Testing if the inverse document score is calculated correct, based on the calculations made by us
     */

    @Test
    public void testCalcInverseDocumentScore() {
        // Input preparation
        List<Page> pages = getData();

        // System preparation
        RankingTFIDF ranking = new RankingTFIDF(pages);

        // Test
        Map<String, Double> inverseDocumentScore = ranking.calcInverseDocumentScore(pages);
        assertEquals(0.866, inverseDocumentScore.get("word1"), 0.01);
        assertEquals(1.0, inverseDocumentScore.get("word2"), 0.01);
        assertEquals(1.33, inverseDocumentScore.get("word3"), 0.01);
        assertEquals(1.154, inverseDocumentScore.get("word4"), 0.01);

    }
    /**
     * Testing TFIDF for each word in pages
     */
    @Test
    public void testFrequencyInverseDocumentFrequency() {
        // Input preparation
        List<Page> pages = getData();

        // System preparation
        RankingTFIDF ranking = new RankingTFIDF(pages);

        // Test
        Map<Page, Map<String, Double>> frequencyInverseDocumentScore = ranking
                .calcFrequencyInverseDocumentFrequency(pages);

        // Word1
        assertEquals(0.693, frequencyInverseDocumentScore.get(pages.get(0)).get("word1"), 0.01);
        assertEquals(0.144, frequencyInverseDocumentScore.get(pages.get(1)).get("word1"), 0.01);
        assertEquals(0.288, frequencyInverseDocumentScore.get(pages.get(2)).get("word1"), 0.01);
        assertEquals(0.346, frequencyInverseDocumentScore.get(pages.get(3)).get("word1"), 0.01);
        assertEquals(0.288, frequencyInverseDocumentScore.get(pages.get(4)).get("word1"), 0.01);
        assertEquals(0.216, frequencyInverseDocumentScore.get(pages.get(5)).get("word1"), 0.01);
        assertEquals(0.247, frequencyInverseDocumentScore.get(pages.get(6)).get("word1"), 0.01);

        // Word2
        assertEquals(0.2, frequencyInverseDocumentScore.get(pages.get(0)).get("word2"), 0.01);
        assertEquals(0.5, frequencyInverseDocumentScore.get(pages.get(1)).get("word2"), 0.01);
        assertEquals(0.166, frequencyInverseDocumentScore.get(pages.get(2)).get("word2"), 0.01);
        assertEquals(0.66, frequencyInverseDocumentScore.get(pages.get(4)).get("word2"), 0.01);
        assertEquals(0.5, frequencyInverseDocumentScore.get(pages.get(5)).get("word2"), 0.01);

        // Word3
        assertEquals(0.22, frequencyInverseDocumentScore.get(pages.get(1)).get("word3"), 0.01);
        assertEquals(0.44, frequencyInverseDocumentScore.get(pages.get(2)).get("word3"), 0.01);
        assertEquals(0.53, frequencyInverseDocumentScore.get(pages.get(3)).get("word3"), 0.01);
        assertEquals(0.38, frequencyInverseDocumentScore.get(pages.get(6)).get("word3"), 0.01);

        // Word4
        assertEquals(0.19, frequencyInverseDocumentScore.get(pages.get(1)).get("word4"), 0.01);
        assertEquals(0.192, frequencyInverseDocumentScore.get(pages.get(2)).get("word4"), 0.01);
        assertEquals(0.23, frequencyInverseDocumentScore.get(pages.get(3)).get("word4"), 0.01);
        assertEquals(0.288, frequencyInverseDocumentScore.get(pages.get(5)).get("word4"), 0.01);
    }
    /**
     * testing the ranking of the pages based on one word ("word1") 
     */
    @Test
    public void testRankPagesOneWord() {
        // Input preparation
        List<Page> input = getData();

        // System preparation
        RankingTFIDF ranking = new RankingTFIDF(input);

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
     * Testing the ranking of the pages based on two words splitted by white space (%20)
     */
    @Test
    public void testRankPagesTwoWords() {
        // Input preparation
        List<Page> input = getData();
        String expectedTitles[] = { "Title1", "Title2", "Title6" };
        List<String> expectedTitlesList = Arrays.asList(expectedTitles);

        // System preparation
        RankingTFIDF ranking = new RankingTFIDF(input);

        // Test
        List<Page> output = ranking.rankPages(input, "word1%20word2");

        // Verification//

        assertEquals("Title4", output.get(0).getTitle());
        assertEquals("Title0", output.get(1).getTitle());
        assertEquals("Title5", output.get(2).getTitle());
        assertTrue(expectedTitlesList.contains((output.get(3).getTitle())));
        assertTrue(expectedTitlesList.contains((output.get(4).getTitle())));
        assertTrue(expectedTitlesList.contains((output.get(5).getTitle())));
        assertEquals("Title3", output.get(6).getTitle());

    }
    /**
     * Testing ranking on 2 words splitted by OR (%20OR%20)
     */

    @Test
    public void testRankPagesOrQuery() {
        // Input preparation
        List<Page> input = getData();
        String expectedTitles[] = { "Title1", "Title5" };
        List<String> expectedTitlesList = Arrays.asList(expectedTitles);

        // System preparation
        RankingTFIDF ranking = new RankingTFIDF(input);

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
     * Testing ranking on multiple words splitted by both white spaces and OR 
     */
    @Test
    public void testRankPagesMultipleWordOrQuery() {
        // Input preparation
        List<Page> input = getData();

        // System preparation
        RankingTFIDF ranking = new RankingTFIDF(input);

        // Test
        List<Page> output = ranking.rankPages(input, "word1%20word2%20or%20word3%20word4");

        // Verification//
        assertEquals("Title4", output.get(0).getTitle());
        assertEquals("Title0", output.get(1).getTitle());
        assertEquals("Title3", output.get(2).getTitle());
        assertEquals("Title5", output.get(3).getTitle());
        assertEquals("Title6", output.get(4).getTitle());
        assertEquals("Title1", output.get(5).getTitle());
        assertEquals("Title2", output.get(6).getTitle());

    }

}
