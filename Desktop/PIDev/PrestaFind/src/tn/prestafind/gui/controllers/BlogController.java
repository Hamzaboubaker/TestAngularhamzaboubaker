package tn.prestafind.gui.controllers;

import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.prestafind.entities.Article;
import tn.prestafind.entities.User;
import tn.prestafind.entities.helpers.Account;
import tn.prestafind.entities.helpers.Post;
import tn.prestafind.entities.helpers.PostAudience;
import tn.prestafind.services.CRUDArticle;
import tn.prestafind.services.CRUDUser;
import tn.prestafind.utils.Animations;
import tn.prestafind.utils.RSSReader;
import tn.prestafind.utils.TextFieldsValidators;
import tn.prestafind.utils.TextFieldMask;

public class BlogController implements Initializable {

    List<Post> posts;
    Post post = new Post();
    Account account = new Account();
    User user = new User();
    CRUDUser crudUser = new CRUDUser();
    CRUDArticle crudArticle = new CRUDArticle();
    String userName, caption;

    @FXML
    private VBox postsContainer;

    private VBox postHeader;
    @FXML
    private VBox postVbox;
    @FXML
    private JFXTextField postTextField;
    @FXML
    private HBox topBar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setValidations();
        selectText();
//        animations();
        try {
            posts = new ArrayList<>(getPosts());
            displayPosts(posts);
        } catch (SQLException ex) {
            Logger.getLogger(BlogController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(BlogController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Post> getPosts() throws SQLException, Exception {
        int i = 0;
        posts = new ArrayList<>();
        List<Article> articles = crudArticle.getArticlesMax10();

        for (Article article : articles) {
            user = crudUser.getUserById(article.getAuteurId());
            userName = user.getPseudo();
            caption = article.getContenu();
            String datePub = article.getDatePublication().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            addPostToList(article.getId(), posts, userName, "../../resources/media/profiles/head.png",
                    true, datePub, PostAudience.FRIENDS,
                    "../../resources/media/img" + ++i + ".jpg", caption, 11, 2, 3);
        }

        // write the posts from the DB in PrestaFindRSSFeed.xml
        RSSReader.writeFeedToXMLFile(articles);

        return posts;
    }

    public void addPostToList(int articleId, List<Post> postsList, String username, String profileImg,
            boolean verified, String datePub,
            PostAudience visibility, String image, String caption,
            int nbrReactions, int nbrComments, int nbrShares) {
        Post post = new Post();
        post.setId(articleId);
        Account account = new Account();
        account.setName(username);
        account.setProfileImg(profileImg);
        account.setVerified(verified);
        post.setAccount(account);
        post.setDate(datePub);
        post.setAudience(visibility);
        post.setCaption(caption);
        post.setImage(image);
        post.setTotalReactions(nbrReactions);
        post.setNbComments(nbrComments);
        post.setNbShares(nbrShares);
        postsList.add(post);
    }

    @FXML
    private void postTextFieldEnterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                String caption = postTextField.getText();
//                System.out.println("captions: " + caption);
                if (!caption.trim().isEmpty()) {
                    Article article = new Article(
                            0, 3, "titre", caption, LocalDateTime.now(),
                            LocalDateTime.now(), 1, 2, 3, 1, 2, true, null);

                    crudArticle.ajouterArticle(article);

                    Post post = new Post();
                    Account account = new Account();
                    account.setName("aaa");
                    account.setProfileImg("../../resources/media/profiles/head.png");
                    account.setVerified(true);
                    post.setAccount(account);
                    post.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                    post.setAudience(PostAudience.FRIENDS);
                    post.setCaption(caption);
//                post.setImage(image);
                    post.setTotalReactions(0);
                    post.setNbComments(0);
                    post.setNbShares(0);
                    posts.add(post);

                    addPostToTopVbox(post);
                    postTextField.clear();
                } else {
                    postTextField.requestFocus();
                    Animations.shake(postTextField);
                    System.out.println("caption is empty");
                }

            } catch (SQLException ex) {
                Logger.getLogger(BlogController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void displayPosts(List<Post> posts) {
        posts.stream().forEach((post) -> {
            FXMLLoader postLoader = new FXMLLoader();
            postLoader.setLocation(getClass().getResource("../views/post.fxml"));
            try {
                VBox vbox = postLoader.load();
                PostController postController = postLoader.getController();
                postController.setData(post);
                postsContainer.getChildren().add(vbox);
            } catch (IOException ex) {
                Logger.getLogger(BlogController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void addPostToTopVbox(Post post) {
        FXMLLoader postLoader = new FXMLLoader();
        postLoader.setLocation(getClass().getResource("../views/post.fxml"));
        try {
            VBox vbox = postLoader.load();
            PostController postController = postLoader.getController();
            postController.setData(post);
            postsContainer.getChildren().add(1, vbox);
        } catch (IOException ex) {
            Logger.getLogger(BlogController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void animations() {
        postHeader.requestFocus();
        Animations.fadeInUp(postHeader);
    }

    private void setValidations() {
        TextFieldsValidators.toRequiredJFXTextField(postTextField);
    }

    private void selectText() {
        TextFieldMask.selectText(postTextField);
    }

    public VBox getPostsContainer() {
        return postsContainer;
    }

    public void setPostsContainer(VBox postsContainer) {
        this.postsContainer = postsContainer;
    }

}
