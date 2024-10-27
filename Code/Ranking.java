package searchengine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class Ranking {

    Map<Page, Map<String, Double>> pageRawCount;
    Map<Page, Map<String, Double>> pageFrequencyScore;

    public Ranking(List<Page> pages) {
        pageRawCount = calcPageRawCount(pages);
        pageFrequencyScore = calcPageFrequencyScore(pages);
    }

    public abstract List<Page> rankPages(List<Page> matchingPages, String query);

    /**
     * method that calculates the raw count of each page
     *
     * @param pages
     * @return Map<Page, Map<String, Double>>
     */
    public Map<Page, Map<String, Double>> calcPageRawCount(List<Page> pages) {
        Map<Page, Map<String, Double>> result = new HashMap<>();
        for (Page page : pages) {
            Map<String, Double> rawCount = new HashMap<>();
            List<String> words = page.getWords();
            for (String word : words) {
                if (!rawCount.containsKey(word)) {
                    rawCount.put(word, 0.0);
                }
                rawCount.put(word, rawCount.get(word) + 1.0);
            }
            result.put(page, rawCount);
        }
        return result;
    }

    public double getRawCount(Page page, String word) {
        return pageRawCount.get(page).get(word);
    }

    /**
     * Method that calculates the frequencyscore for each page
     * 
     * @param pages
     * @return Map<Page, Map<String, Double>>
     */
    public Map<Page, Map<String, Double>> calcPageFrequencyScore(List<Page> pages) {
        Map<Page, Map<String, Double>> result = new HashMap<>();
        for (Page page : pages) {
            Map<String, Double> frequencyScore = new HashMap<>();
            Set<String> uniqueWords = new HashSet<>(page.getWords());
            for (String word : uniqueWords) {
                double frequencyScoreword = (getRawCount(page, word) / page.getWords().size());
                frequencyScore.put(word, frequencyScoreword);
            }
            result.put(page, frequencyScore);
        }
        return result;
    }

    public double getFrequencyScore(Page page, String word) {
        return pageFrequencyScore.get(page).getOrDefault(word,0.0);
    }
}
