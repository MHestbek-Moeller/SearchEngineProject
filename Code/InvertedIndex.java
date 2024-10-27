package searchengine;

import java.util.*;

/**
 * Creates and stores the inverted index to be looked up with the searchterm.
 * This is the default index of the search engine.
 */
public class InvertedIndex {
  public Map<String, Set<Page>> invertedIndex = new HashMap<>();

  /**
   * looks up the given term and returns the results
   * 
   * @param word the word we are searching for
   * @return returns the list of pages to which the word is mapped
   */
  public Set<Page> lookupInvertedIndex(String word) {
    Set<Page> result = new HashSet<>();
    if (invertedIndex.containsKey(word)) {
      result = invertedIndex.get(word);
    }
    return result;
  }

  /**
   * This creates the inverted index
   * 
   * @param pages list of all pages read in
   * @return a map that has the words as keys and the pages where the words are
   *         found as the corresponding values.
   */
  public Map<String, Set<Page>> indexPages(List<Page> pages) {
    for (Page page : pages) {
      for (String word : page.getWords()) {
        if (!invertedIndex.containsKey(word)) {
          invertedIndex.put(word, new HashSet<Page>());
        }
        invertedIndex.get(word).add(page);
      }}
      return invertedIndex;
  }

}
