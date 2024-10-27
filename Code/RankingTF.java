package searchengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankingTF extends Ranking {

    public RankingTF(List<Page> pages) {
        super(pages);
    }

    /** 
     * Ranks the pages by term frequency and returns it in a list of ranked matchingpages
     * 
     * @param matchingPages
     * @param query
     * @return List<Page>
     */
    @Override
    public List<Page> rankPages(List<Page> matchingPages, String query) {
        Map<Page, Double> pageScores = new HashMap<>();
        for (Page page : matchingPages) {
            double score = 0;
            String[] subQueries = query.split("%20or%20");
            List<Double> subQueryScore = new ArrayList<>();
            for (String words : subQueries) {
                double oneWordScore = 0;
                for (String word : words.split("%20")) {
                    oneWordScore = oneWordScore + getFrequencyScore(page, word);
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
