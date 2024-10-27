package searchengine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

public class RankingTest {
    /**
     * Initializing the pages that the following tests are based on
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
     * Testing the calcPageRawcount of specific words in different pages
     */

    @Test
    public void testCalcPageRawCount() {
        // Input preparation
        List<Page> pages = getData();

        // System preparation
        Ranking ranking = new RankingTF(pages);

        // Test
        Map<Page, Map<String, Double>> pageRawCount = ranking.calcPageRawCount(pages);

        // Verification
        assertEquals(4, pageRawCount.get(pages.get(0)).get("word1"));
        assertEquals(1,pageRawCount.get(pages.get(1)).get("word1"));
        assertEquals(3, pageRawCount.get(pages.get(1)).get("word2"));
        assertEquals(2, pageRawCount.get(pages.get(2)).get("word1"));
        assertEquals(2,pageRawCount.get(pages.get(3)).get("word1"));
        assertEquals(1, pageRawCount.get(pages.get(4)).get("word1"));
        assertEquals(1, pageRawCount.get(pages.get(5)).get("word1"));
        assertEquals(2, pageRawCount.get(pages.get(6)).get("word1"));
    }
    /**
     * Testing the TF for different words and pages compared with expected values
     */
    @Test
    public void testCalcPageFrequencyScore() {
        // Input preparation
        List<Page> pages = getData();

        // System preparation
        Ranking ranking = new RankingTF(pages);

        // Test
        Map<Page, Map<String, Double>> pageFrequencyScore = ranking.calcPageFrequencyScore(pages);

        // Verification
        assertEquals(0.8, pageFrequencyScore.get(pages.get(0)).get("word1"), 0.01);
        assertEquals(0.16, pageFrequencyScore.get(pages.get(1)).get("word1"), 0.01);
        assertEquals(0.33, pageFrequencyScore.get(pages.get(2)).get("word1"), 0.01);
        assertEquals(0.5, pageFrequencyScore.get(pages.get(1)).get("word2"), 0.01);
        assertEquals(0.4, pageFrequencyScore.get(pages.get(3)).get("word1"), 0.01);
        assertEquals(0.33, pageFrequencyScore.get(pages.get(4)).get("word1"), 0.01);
        assertEquals(0.25, pageFrequencyScore.get(pages.get(5)).get("word1"), 0.01);
        assertEquals(0.28, pageFrequencyScore.get(pages.get(6)).get("word1"), 0.01);
    }
}