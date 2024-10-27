package searchengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RankingTFIDF extends Ranking {
    Map<String, Double> inverseDocumentScore;
    Map<Page, Map<String, Double>> frequencyInverseDocumentFrequency;

    public RankingTFIDF(List<Page> pages) {
        super(pages);
        inverseDocumentScore = calcInverseDocumentScore(pages);
        frequencyInverseDocumentFrequency = calcFrequencyInverseDocumentFrequency(pages);
    }

    /**
     * Method that calculates the IDF score
     * 
     * @param pages
     * @return Map<String, Double>
     */
    public Map<String, Double> calcInverseDocumentScore(List<Page> pages) {
        Map<String, Double> wordCounts = new HashMap<>();
        for (Page page : pages) {
            Set<String> uniqueWords = new HashSet<>(page.getWords());
            for (String word : uniqueWords) {
                if (!wordCounts.containsKey(word)) {
                    wordCounts.put(word, 0.0);
                }
                wordCounts.put(word, wordCounts.get(word) + 1.0);
            }
        }
        Map<String, Double> result = new HashMap<>();
        double wordImportance = 0.0;
        for (String word : wordCounts.keySet()) {
            wordImportance = (Math.log(pages.size() / (1 + ((wordCounts.get(word)))))) + 1;
            result.put(word, wordImportance);
        }
        return result;
    }

    public double getInverseDocumentScore(String word) {
        return inverseDocumentScore.getOrDefault(word, 0.0);
    }

    /**
     * Method that calculates the TF-IDF
     * 
     * @param pages
     * @return Map<Page, Map<String, Double>>
     */
    public Map<Page, Map<String, Double>> calcFrequencyInverseDocumentFrequency(List<Page> pages) {
        Map<Page, Map<String, Double>> result = new HashMap<>();
        for (Page page : pages) {
            Map<String, Double> frequencyInverseDocumentFrequency = new HashMap<>();
            Set<String> uniqueWords = new HashSet<>(page.getWords());
            for (String word : uniqueWords) {
                double inverseDocumentFrequencyWord = getFrequencyScore(page, word) * getInverseDocumentScore(word);
                frequencyInverseDocumentFrequency.put(word, inverseDocumentFrequencyWord);
            }
            result.put(page, frequencyInverseDocumentFrequency);
        }
        return result;
    }

    public double getFrequencyInverseDocumentFrequency(Page page, String word) {
        return frequencyInverseDocumentFrequency.get(page).getOrDefault(word, 0.0);
    }

    /**
     * methos that ranks the pages based of TF-IDF and returns a list of the
     * matching pages ranked
     * 
     * @param matchingPages
     * @param query
     * @return List<Page>
     */
    @Override
    public List<Page> rankPages(List<Page> matchingPages, String query) {
        Map<Page, Double> pageScores = new HashMap<>();
        for (Page page : matchingPages) {
            double score = 0.0;
            String[] subQueries = query.split("%20or%20");
            List<Double> subQueryScore = new ArrayList<>();
            for (String words : subQueries) {
                double oneWordScore = 0.0;
                for (String word : words.split("%20")) {
                    oneWordScore = oneWordScore + getFrequencyInverseDocumentFrequency(page, word);
                }
                subQueryScore.add(oneWordScore);
            }
            double maxValue = 0;
            for (int i = 0; i < subQueryScore.size(); i++) {
                maxValue = Math.max(maxValue, subQueryScore.get(i));
            }
            score = maxValue;
            pageScores.put(page, score);
        }
        PageComparator comparator = new PageComparator(pageScores);
        matchingPages.sort(comparator);
        return matchingPages;
    }

}
