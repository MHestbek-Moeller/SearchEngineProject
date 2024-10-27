package searchengine;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code Page} is a class we use to store and search for information related to
 * a webpage
 */
public class Page {
  public String URL;
  public String title;
  public List<String> words = new ArrayList<>();

  /**
   * Creates a new {@code Page} with the URL, title and words in it
   * 
   * @param URL   the URL of the webpage
   * @param title the title of the webpage
   * @param words all words in the webpage without indexes
   */
  public Page(String URL, String title, List<String> words) {
    this.URL = URL;
    this.title = title;
    this.words = words;
  }

  /**
   * Retrieves all words from the {@code Page} as a List of Strings
   * 
   * @return the words stored in a {@code Page}
   */
  public List<String> getWords() {
    return words;
  }

  /**
   * Retrieves the URL of a {@code Page} as a String
   * 
   * @return the URL of a {@code Page}
   */
  public String getURL() {
    return URL;

  }

  /**
   * Retrieves the title of a {@code Page} as a String
   * 
   * @return the title of a {@code Page}
   */
  public String getTitle() {
    return title;

  }
}
