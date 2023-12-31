package tn.prestafind.utils;

import com.rometools.rome.feed.synd.*;
import com.rometools.rome.io.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import tn.prestafind.entities.Article;

public class RSSReader extends Application {

    WebView webView = new WebView();
    List<Post> posts = new ArrayList<>();
    GridPane menu = new GridPane();
    TextField feedInput = new TextField();
    FlowPane postHeader = new FlowPane();
    Label postHeaderLabel = new Label("Site - Title - Date");
    Post currentPost;
    List<String> urlHistory = new ArrayList<>();
    int historyIndex = -1;

    private static class Post {

        String siteTitle;
        String postTitle;
        Date date;
        String content;
        Hyperlink linkToContent;

        Date getDate() {
            return date;
        }
    }

    public static void writeFeedToXMLFile(List<Article> articles) throws Exception {
        SyndFeed feed = new SyndFeedImpl();
        feed.setFeedType("rss_2.0");
        feed.setTitle("PrestaFind RSS Feed");
        feed.setDescription("This is a sample RSS feed for testing");
        feed.setLink("http://www.PrestaFind.tn");

        List<SyndEntry> entries = constructEntries(articles);
        feed.setEntries(entries);

        // Write the feed to a file
        FileWriter writer = new FileWriter("PrestaFindRSSFeed.xml");
        SyndFeedOutput output = new SyndFeedOutput();
        output.output(feed, writer);
        writer.close();
    }

    private static List<SyndEntry> constructEntries(List<Article> articles) {
        List<SyndEntry> entries = new ArrayList<>();

        for (Article article : articles) {
            SyndEntry entry = new SyndEntryImpl();
            entry.setTitle(article.getTitre());
            entry.setLink("http://www.PrestaFind.tn/" + article.getTitre());
            entry.setPublishedDate(new Date());
            SyndContent description = new SyndContentImpl();
            description.setType("text/html");
            description.setValue(article.getContenu());
            entry.setDescription(description);
            entries.add(entry);
        }

        return entries;
    }

    // Load an existing RSS feed
    public void printFeedFromFile() throws Exception {
        // Load the feed from the file
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed loadedFeed = input.build(new File("PrestaFindRSSFeed.xml"));

        // Print out the feed title and entries
        System.out.println("Feed Title: " + loadedFeed.getTitle());
        for (SyndEntry loadedEntry : loadedFeed.getEntries()) {
            System.out.println("Entry Title: " + loadedEntry.getTitle());
            System.out.println("Entry Link: " + loadedEntry.getLink());
            System.out.println("Entry Description: " + loadedEntry.getDescription().getValue());
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("PrestaFind RSS Reader");
        addHistoryListener();

        try {
            URL url = new File("PrestaFindRSSFeed.xml").toURI().toURL();
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed loadedFeed = input.build(new XmlReader(url));

            for (SyndEntry loadedEntry : loadedFeed.getEntries()) {
                Post post = new Post();
                post.siteTitle = loadedFeed.getTitle();
                post.postTitle = loadedEntry.getTitle();
                post.date = loadedEntry.getPublishedDate();
                post.content = loadedEntry.getDescription().getValue();
                post.linkToContent = new Hyperlink(loadedEntry.getLink());

                post.linkToContent.setOnAction(e -> showPost(post));

                posts.add(post);
            }

            loadFeed();
        } catch (IOException | IllegalArgumentException | FeedException e) {
            e.getMessage();
        }

        try (Scanner sc = new Scanner(new File(".addedRSSFeeds"))) {
            while (sc.hasNextLine()) {
                posts.addAll(fetchFeed(sc.nextLine()));
            }
            loadFeed();
        } catch (Exception e) {
            e.getMessage();
        }

        ScrollPane scrollMenu = new ScrollPane();
        scrollMenu.setContent(menu);

        postHeaderLabel.setPadding(new Insets(10, 10, 10, 10));
        postHeaderLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 16));

        postHeader.minHeight(100);
        postHeader.getChildren().add(postHeaderLabel);
        VBox postView = new VBox(postHeader, webView);

        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(scrollMenu, postView);

        VBox vbox = new VBox(getAddFeedPane(), splitPane);

        Scene scene = new Scene(vbox, 1200, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addHistoryListener() {
        final WebHistory history = webView.getEngine().getHistory();
        history.getEntries().addListener(
                (ListChangeListener.Change<? extends WebHistory.Entry> c) -> {
                    c.next();
                    c.getRemoved().stream().forEach((e) -> {
                        System.out.println("----------------- Removed url: " + e.getUrl());
                    });
                    c.getAddedSubList().stream().forEach((e) -> {
                        System.out.println("+++++++++++++++++ Added url: " + e.getUrl());
                        if (historyIndex == -1 && !urlHistory.isEmpty()
                                && !urlHistory.get(0).equals(e.getUrl())) {
                            System.out.println("Clearing history ...");
                            urlHistory.clear();
                        }
                        if (!urlHistory.contains(e.getUrl())) {
                            urlHistory.add(e.getUrl());
                            ++historyIndex;
                        }
                    });
                });
    }

    private FlowPane getAddFeedPane() {
        Label addLabel = new Label("Add feed:");
        feedInput.setPrefColumnCount(40);
        feedInput.setOnKeyPressed(ke -> handleNewFeed(ke));
        Button addButton = new Button("ADD");
        addButton.setOnAction(action -> handleNewFeed(null));
        Button previousPage = new Button(" Previous Page < ");
        previousPage.setOnAction(a -> handlePreviousPage());
        Button nextPage = new Button(" > Next Page");
        nextPage.setOnAction(a -> handleNextPage());
        return new FlowPane(addLabel, feedInput, addButton, previousPage, nextPage);
    }

    private void handlePreviousPage() {
        System.out.println(urlHistory);
        if (urlHistory.isEmpty() || historyIndex == -1) {
            return;
        }
        if (historyIndex == 0) {
            showPost();
        } else {
            loadUrl(urlHistory.get(historyIndex - 1));
        }
        --historyIndex;
    }

    private void handleNextPage() {
        System.out.println(urlHistory);
        if (urlHistory.isEmpty() || historyIndex + 1 >= urlHistory.size()) {
            return; // TODO == should work ...
        }
        ++historyIndex;
        loadUrl(urlHistory.get(historyIndex));
    }

    private void loadUrl(String url) {
        webView.getEngine().load(url);
    }

    private void handleNewFeed(KeyEvent ke) {
        if (ke == null || ke.getCode().equals(KeyCode.ENTER)) {
            addFeedToFile(feedInput.getText());
            loadFeed(feedInput.getText());
            feedInput.setText("");
        }
    }

    private void addFeedToFile(String url) {
        try (FileWriter fw = new FileWriter(".addedRSSFeeds", true)) {
            fw.write(url + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadFeed() {
        menu.getChildren().clear();
        posts.sort(Comparator.comparing(Post::getDate).reversed());
        for (Post post : posts) {
            menu.addColumn(0, post.linkToContent);
        }
    }

    private void loadFeed(String url) {
        posts.addAll(fetchFeed(url));
        loadFeed();
    }

    public List<Post> fetchFeed(String url) {
        List<Post> listPost = new ArrayList<>();
        try {
            URL feedUrl = new URL(url);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedUrl));

            // Get the entry items...
            for (SyndEntry entry : (List<SyndEntry>) feed.getEntries()) {
                if (entry.getContents().size() == 0) {
                    break;
                }
                Post post = new Post();
                post.siteTitle = feed.getTitle();
                post.postTitle = entry.getTitle();
                post.content = entry.getContents().get(0).getValue();
                Date date = entry.getUpdatedDate();
                if (date == null) {
                    date = entry.getPublishedDate();
                }
                if (date == null) {
                    date = new Date();
                }
                post.date = date;
                Hyperlink link = new Hyperlink(entry.getTitle() + " - " + date);
                link.setOnAction(e -> showPost(post));
                post.linkToContent = link;
                listPost.add(post);
            }
        } catch (Exception ex) {
            System.err.println("Failed to fetch rss from: " + url);
            ex.printStackTrace();
        }

        return listPost;
    }

    private void showPost() {
        if (currentPost == null) {
            return;
        }
        updatePostHeader(currentPost);
        updateWebView(currentPost.content);
    }

    private void showPost(Post post) {
        currentPost = post;
        showPost();
    }

    private void updatePostHeader(Post post) {
        postHeaderLabel.setText(post.siteTitle + " - " + post.postTitle + " - " + post.date);
    }

    private void updateWebView(String content) {
        webView.getEngine().loadContent(content);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
