package searchengine;

import java.util.Comparator;
import java.util.Map;

class PageComparator implements Comparator<Page> {
    private Map<Page, Double> pageScores;

    public PageComparator(Map<Page, Double> pageScores) {
        this.pageScores = pageScores;
    }

    
    /** 
     * Method that compares the pages so that they can be ranked
     * 
     * @param page1
     * @param page2
     * @return int
     */
    @Override
    public int compare(Page page1, Page page2) {
        if (pageScores.get(page1) < pageScores.get(page2)) {
            return 1;
        } else if (pageScores.get(page1) > pageScores.get(page2)) {
            return -1;
        } else {
            return 0;
        }

    }
}