package searchengine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simpler version of the searchengine, that searches for a word using a
 * regular index.
 */

public class SimpleQueryEngine {
  List<List<String>> pages;

  /**
   * Reads in the file as lines and adds it to an Arraylist.
   * 
   * @param filename is the name of the file
   * @throws IOException throws an IOException if the input or output has an error
   *                     (e.g. filename is not found)
   */

  public SimpleQueryEngine(String filename) throws IOException {
    pages = new ArrayList<>();// Kan laves til metode fra her
    try {
      List<String> lines = Files.readAllLines(Paths.get(filename));
      var lastIndex = lines.size();
      for (var i = lines.size() - 1; i >= 0; --i) {// Refactor dette loop til at starte fra 0
        if (lines.get(i).startsWith("*PAGE")) {
          pages.add(lines.subList(i, lastIndex));
          lastIndex = i;
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    Collections.reverse(pages);
  }

  /**
   * Searches through all pages for a matching searchterm, if found, will add to
   * result and return it.
   * 
   * @param searchTerm
   * @return
   */
  public List<List<String>> search(String searchTerm) {
    var result = new ArrayList<List<String>>();
    for (var page : pages) { // goes through the arraylist of pages
      if (page.contains(searchTerm)) { // if variable contains what we are searching for we add the page to the result
        result.add(page);
      }
    }
    return result;
  }
} // this: we return the result for a searchterm
