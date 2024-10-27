package searchengine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Reads the search term by the user, and finds the websites that match the
 * query using an inverted index.
 */

public class QueryHandler {
    Map<String, Set<Page>> invertedIndex;

    /**
     * Creates a new {@code QueryHandler}
     * 
     * @param invertedIndex the index to search from
     */
    public QueryHandler(Map<String, Set<Page>> invertedIndex) {
        this.invertedIndex = invertedIndex;
    }

    /**
     * Reads in the query and looks up the given index with the query term to get
     * the pages
     * that contain the query
     * 
     * @param query         the input the user searched for
     * @param invertedIndex the index to search from
     * @return returns all webpages matching the user's input
     */
    public Set<Page> getMatchingWebPages(String query, Map<String, Set<Page>> invertedIndex) {
        Set<Page> result = new HashSet<>();
        String[] queries = query.split("%20or%20");
        List<Set<Page>> matchesOneSubQuery = new ArrayList<>();
        for (String queryWords : queries) {
            List<Set<Page>> oneWordMatches = new ArrayList<>();
            for (String word : queryWords.split("%20")) {
                if (invertedIndex.containsKey(word)) {
                    oneWordMatches.add(invertedIndex.get(word));
                }
            }
            matchesOneSubQuery.add(getIntersection(oneWordMatches));
        }

        if (getUnion(matchesOneSubQuery) == null) {
            return result;
        }
        result = getUnion(matchesOneSubQuery);
        return result;

    }

    /**
     * Returns an empty set or all pages that contain all searched words without
     * duplicates by using the Set
     * 
     * @param oneWordMatches stores a list of all the pages that have a matching
     *                       word to the query
     * @return the intersect of all the matching pages to the query
     */
    public static Set<Page> getIntersection(List<Set<Page>> oneWordMatches) {
        Set<Page> result = new HashSet<>();
        if (oneWordMatches.size() == 0) {
            return result;
        }
        if (oneWordMatches.size() == 1) {
            return oneWordMatches.get(0);
        }
        Set<Page> intersectingSet = oneWordMatches.get(0);
        for (int i = 1; i < oneWordMatches.size(); i++) {
            Set<Page> oneWordMatchesIndexi = oneWordMatches.get(i);
            intersectingSet.retainAll(oneWordMatchesIndexi);
        }
        result = intersectingSet;
        return result;
    }

    /**
     * Returns an empty set or all pages that contain at least one or all searched
     * words without duplicates by using the Set
     * 
     * @param matchesOneSubQuery stores a list of all the pages that have a matching
     *                           word to the query
     * @return the union of all the matching pages to the query
     */
    public static Set<Page> getUnion(List<Set<Page>> matchesOneSubQuery) {
        Set<Page> result = new HashSet<>();
        if (matchesOneSubQuery.size() == 0) {
            return result;
        }
        if (matchesOneSubQuery.size() == 1) {
            return matchesOneSubQuery.get(0);
        }
        Set<Page> unionSet = matchesOneSubQuery.get(0);
        for (int i = 1; i < matchesOneSubQuery.size(); i++) {
            Set<Page> oneWordMatchesIndexi = matchesOneSubQuery.get(i);
            unionSet.addAll(oneWordMatchesIndexi);
        }
        result = unionSet;
        return result;
    }

}
