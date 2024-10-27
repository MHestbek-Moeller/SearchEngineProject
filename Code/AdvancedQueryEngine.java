package searchengine;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Handles reading in the files, searches for the searchterm and communicates
 * with the webserver
 */
public class AdvancedQueryEngine {

  Map<String, Set<Page>> invertedIndex;
  QueryHandler queryHandler;
  Ranking ranking;

  /**
   * Reads in the file, creates inverted index and creates a query handler to
   * search for the clients query
   * and find a response
   * 
   * @param filename name of the file to work with
   * @throws IOException if the input or output has an error (e.g. filename
   *                     doesn't exist)
   */
  public AdvancedQueryEngine(String filename) throws IOException {
    List<Page> pages = readPages(filename);
    InvertedIndex i = new InvertedIndex();
    invertedIndex = i.indexPages(pages);
    queryHandler = new QueryHandler(invertedIndex);
    ranking = new RankingTF(pages);
 
  }

  /**
   * Adds pages to a list making sure the page has a title and at least one word
   * otherwise it throws an exception
   * 
   * @param filename name of the file to work with
   * @return list of pages
   * @throws IOException if the input or output has an error (e.g. filename
   *                     doesn't exist)
   */
  public List<Page> readPages(String filename) throws IOException {
    FileInputStream fileInputStream = new FileInputStream(filename);
    Scanner scanner = new Scanner(fileInputStream);
    String title = null;
    String pageURL = null;
    Page currentPage = null;
    List<Page> pages = new ArrayList<>();
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      if (line.startsWith("*PAGE")) {
        pageURL = line.substring(6);
        title = null;
        currentPage = null;
      } else if (title == null && pageURL != null) {
        title = line;
      } else if (title != null && pageURL != null && currentPage == null) {
        List<String> words = new ArrayList<>();
        words.add(line);
        currentPage = new Page(pageURL, title, words);
        pages.add(currentPage);
      } else if (title != null && pageURL != null && currentPage != null) {
        currentPage.getWords().add(line);
      }
    }
    scanner.close();
    Collections.reverse(pages);
    return pages;
  }

  /**
   * Searches for the query using the queryHandler
   * 
   * @param query input from the client to search for
   * @return matching webpages
   */
  public List<Page> search(String query) {
    Set<Page> matchingPages = queryHandler.getMatchingWebPages(query, invertedIndex);
    List<Page> rankedPages = ranking.rankPages(new ArrayList<>(matchingPages), query);
    return rankedPages;

  }
}
