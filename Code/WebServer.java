package searchengine;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

/**
 * WebServer class communicates with the client by reading in their input and
 * then
 * providing an output for their request with different files.
 */

public class WebServer {
  static final int PORT = 8080;
  static final int BACKLOG = 0;
  static final Charset CHARSET = StandardCharsets.UTF_8; // format of language

  HttpServer server;
  AdvancedQueryEngine queryEngine;

  /**
   * Sets up the webserver, creates the context and the look for the searchengine,
   * creates context for the input and output.
   * 
   * @param port        gets the port's value to run on, can choose between
   *                    integers
   * @param queryEngine sets our queryEngine which is and advanced one, but can be
   *                    changed to a simple one
   * @throws IOException catches if an input or output error occurs, for example,
   *                     the port or queryEngine we put in does not exist.
   */

  WebServer(int port, AdvancedQueryEngine queryEngine) throws IOException {
    server = HttpServer.create(new InetSocketAddress(port), BACKLOG);
    server.createContext("/", io -> respond(io, 200, "text/html", getFile("web/index.html")));
    server.createContext("/search", io -> search(io));
    server.createContext(
        "/favicon.ico", io -> respond(io, 200, "image/x-icon", getFile("web/favicon.ico")));
    server.createContext(
        "/code.js", io -> respond(io, 200, "application/javascript", getFile("web/code.js")));
    server.createContext(
        "/style.css", io -> respond(io, 200, "text/css", getFile("web/style.css")));
    server.start();
    this.queryEngine = queryEngine;
    printMessage(port);
  }

  /**
   * Prints out message in the terminal with the URL code of the webserver where
   * we can access the
   * searchengine, after the {@code WebServer ran}.
   * 
   * @param port is the port's number
   */

  private void printMessage(int port) {
    String msg = " WebServer running on http://localhost:" + port + " ";
    System.out.println("╭" + "─".repeat(msg.length()) + "╮");
    System.out.println("│" + msg + "│");
    System.out.println("╰" + "─".repeat(msg.length()) + "╯");
  }

  /**
   * Handles the communication between the client's input in the server and the
   * output, by responding to the input with a result.
   * It adds the response to the bytes in the form of the charset which is just
   * the unified language UTC 8.
   * 
   * @param io is the query request from the client
   */

  void search(HttpExchange io) {
    var query = getQuery(io);
    var response = new ArrayList<String>();
    for (var page : queryEngine.search(query)) {
      response.add(String.format("{\"url\": \"%s\", \"title\": \"%s\"}",
          page.getURL(), page.getTitle()));
    }
    var bytes = response.toString().getBytes(CHARSET);
    respond(io, 200, "application/json", bytes);
  }

  /**
   * Returns the result for the searchterm that the clients has put in as input
   * 
   * @param io is the query request from the client
   * @return returns the response for the client's query, which are a list of
   *         webpages containing the input
   */
  public String getQuery(HttpExchange io) {
    String query = io.getRequestURI().getRawQuery().split("=")[1].toLowerCase();
    return query;
  }

  /**
   * Responds to the client's request
   * 
   * @param io       is the query request from the client
   * @param code     is the response code sent to the server
   * @param mime     type of file the browser uses to read the file
   * @param response the searchengine's response as bytes
   */
  void respond(HttpExchange io, int code, String mime, byte[] response) {
    try {
      io.getResponseHeaders()
          .set("Content-Type", String.format("%s; charset=%s", mime, CHARSET.name()));
      io.sendResponseHeaders(200, response.length);
      io.getResponseBody().write(response);
    } catch (Exception e) {
    } finally {
      io.close();
    }
  }

  /**
   * Reads in the file we put as filename as bytes
   * 
   * @param filename the name of the file to read in
   * @return the bytes from the file
   * @throws IOException if an input or output error occurs (e.g. if there is no
   *                     such filename)
   */
  byte[] getFile(String filename) {
    try {
      return Files.readAllBytes(Paths.get(filename));
    } catch (IOException e) {
      e.printStackTrace();
      return new byte[0];
    }
  }

  /**
   * runs the searchengine
   * 
   * @param args if there is any input needed from our end in the terminal
   * @throws IOException if an input or output error occurs
   */

  public static void main(final String... args) throws IOException {
    var filename = Files.readString(Paths.get("config.txt")).strip();
    AdvancedQueryEngine queryEngine = new AdvancedQueryEngine(filename);
    new WebServer(PORT, queryEngine);
  }

}