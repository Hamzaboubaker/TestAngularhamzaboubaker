/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.prestafind.gui.controllers;

import com.google.api.translate.Language;
import com.jfoenix.controls.JFXTextField;
import com.sun.java.accessibility.util.Translator;
import static java.awt.SystemColor.text;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.controlsfx.control.Notifications;
import tn.prestafind.utils.Animations;
import tn.prestafind.utils.TextFieldsValidators;
import tn.prestafind.utils.TextFieldMask;
import tn.prestafind.entities.Commentaire;
import tn.prestafind.entities.User;
import tn.prestafind.entities.helpers.Account;
import tn.prestafind.entities.helpers.Post;
import tn.prestafind.entities.helpers.PostAudience;
import tn.prestafind.services.BadWords;
import tn.prestafind.services.CRUDCommentaire;
import tn.prestafind.services.CRUDUser;

/**
 * FXML Controller class
 *
 * @author User31
 */
public class ListeCommentairesController implements Initializable {
 int c;
     int file;
    File pDir;
    File pfile;
    String lien;
    List<Commentaire> comments;
    CRUDCommentaire crudCommentaire = new CRUDCommentaire();
    List<Post> posts;
    Post post = new Post();
    Account account = new Account();
    User user = new User();
    CRUDUser crudUser = new CRUDUser();
    String userName, caption,img;

    @FXML
    private JFXTextField postTextField;
    @FXML
    private HBox commentBtnHbox;
    @FXML
    private VBox commentsContainer;
    @FXML
    private VBox commentVbox;
    @FXML
    private ImageView imv;
    @FXML
    private JFXTextField tfSearch;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
          c = (int) (Math.random() * (300000 - 2 + 1)) + 2;
        pDir = new File("src/image" + c + ".jpg");
        lien = "src/image" + c + ".jpg";
        setValidations();
        selectText();
//        animations();
        commentBtnHbox.setVisible(false);

        try {
            posts = new ArrayList<>(getPosts());
            displayPosts(posts);
        } catch (SQLException ex) {
            Logger.getLogger(BlogController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(BlogController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public static boolean copier(File source, File dest) {
        try (InputStream sourceFile = new java.io.FileInputStream(source);
                OutputStream destinationFile = new FileOutputStream(dest)) {
            // Lecture par segment de 0.5Mo  
            byte buffer[] = new byte[512 * 1024];
            int nbLecture;
            while ((nbLecture = sourceFile.read(buffer)) != -1) {
                destinationFile.write(buffer, 0, nbLecture);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false; // Erreur 
        }
        return true; // Résultat OK   
    }

    private void animations() {
        Animations.fadeInUp(postTextField);
    }

    private void setValidations() {
        TextFieldsValidators.toRequiredJFXTextField(postTextField);
    }

    private void selectText() {
        TextFieldMask.selectText(postTextField);
    }

    public List<Post> getPosts() throws SQLException, Exception {
        int i = 0;
        posts = new ArrayList<>();
        List<Commentaire> comments = crudCommentaire.afficherCommentaire();

        for (Commentaire comment : comments) {
            user = crudUser.getUserById(comment.getAuteurId());
            userName = user.getPseudo();
            caption = comment.getContenu();
            img=comment.getImage();
            String datePub = comment.getDatePublication().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            addCommentsToList(comment.getId(), posts, userName, "../../resources/media/profiles/head.png",
                    true, datePub, img, caption, 15, 10, 10);
        }
        return posts;
    }

    public void addCommentsToList(int commentId, List<Post> commentsList, String username, String profileImg,
            boolean verified, String datePub, String image, String caption,
            int nbLikes, int nbDislikes, int nbReporst) {

        Post post = new Post();
        post.setId(commentId);
        Account account = new Account();
        account.setName(username);
        account.setProfileImg(profileImg);
        account.setVerified(verified);
        post.setAccount(account);
        post.setDate(datePub);

        //////////////////////////////////
        BadWords.loadConfigs();
        if (BadWords.filterText(caption)) {
            String filteredText = BadWords.replaceText(caption, "****");

            post.setCaption(filteredText);
        } else {
            post.setCaption(caption);
        }

        post.setImage(image);
        post.setNbLikes(nbLikes);
        post.setNbDisLikes(nbDislikes);
        post.setNbReports(nbReporst);
        commentsList.add(post);

    }

    private void displayPosts(List<Post> posts) {
        posts.stream().forEach((post) -> {
            FXMLLoader commentaireFXMLLoader = new FXMLLoader();
            commentaireFXMLLoader.setLocation(getClass().getResource("../views/commentaire.fxml"));
            try {
                VBox vbox = commentaireFXMLLoader.load();
                CommentaireController commentaireController = commentaireFXMLLoader.getController();
                commentaireController.setData(post);
                commentsContainer.getChildren().add(vbox);
            } catch (IOException ex) {
                Logger.getLogger(BlogController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @FXML
    private void postTextFieldKeyPressed(KeyEvent event) {

        BadWords.loadConfigs();
        Post post = new Post();
        if (event.getCode() == KeyCode.ENTER) {

            BadWords.loadConfigs();
            if (BadWords.filterText(postTextField.getText())) {
                System.out.println("Bad word detected.");
                post.setCaption("*******");
            }

            constructPost(post);
            posts.add(post);
            addPostToTopVbox(post);
            commentBtnHbox.setVisible(false);
        }
    }

    private Post constructPost(Post post) {
        try {
             copier(pfile,pDir);
            String caption = postTextField.getText();
//                System.out.println("captions: " + caption);
            if (!caption.trim().isEmpty()) {
                Commentaire comment = new Commentaire(2, caption, 3, 1, 0, LocalDateTime.now(), false);
    comment.setImage(lien);
                crudCommentaire.ajouterCommenaire(comment);

                Account account = new Account();
                account.setName(crudUser.getUserById(comment.getAuteurId()).getPseudo());
                account.setProfileImg("../../resources/media/profiles/head.png");
                account.setVerified(true);
                post.setAccount(account);
                post.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                post.setAudience(PostAudience.FRIENDS);

                //////////////////////////////////
                BadWords.loadConfigs();
                if (BadWords.filterText(caption)) {
                    String filteredText = BadWords.replaceText(caption, "****");

                    post.setCaption(filteredText);
                } else {
                    post.setCaption(caption);
                }

//                post.setImage(image);
                post.setNbLikes(comment.getNbLikes());
                post.setNbDisLikes(comment.getNbDisLikes());
                post.setNbReports(comment.getNbSignals());
            } else {
                postTextField.requestFocus();
                Animations.shake(postTextField);
                System.out.println("caption is empty");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return post;
    }

    private void addPostToTopVbox(Post post) {
        FXMLLoader commentaireFXMLLoader = new FXMLLoader();
        commentaireFXMLLoader.setLocation(getClass().getResource("../views/commentaire.fxml"));
        try {
            VBox vbox = commentaireFXMLLoader.load();
            CommentaireController commentaireController = commentaireFXMLLoader.getController();
            commentaireController.setData(post);
            commentsContainer.getChildren().add(1, vbox);
            postTextField.clear();

            ////////////////notif////////////////////////
            Notifications notificationBuilder = Notifications.create().title("Alert").text("commentaire ajouté avec succès")
                    .graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT)
                    .onAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {
                            System.out.println("clicked On ");
                        }
                    });

            notificationBuilder.showConfirm();
            //notificationBuilder.show();

            /////////////////////////
        } catch (IOException ex) {
            System.out.println("[ERROR: ListeCommentaireControlelr] failed to load commentaire.fxml");
            Logger.getLogger(BlogController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onCancelBtnClicked(ActionEvent event) {
        postTextField.clear();
        commentBtnHbox.setVisible(false);
    }

    @FXML
    private void onCommentBtnClicked(ActionEvent event) {
        Post post = new Post();
        constructPost(post);
        posts.add(post);
        addPostToTopVbox(post);
        commentBtnHbox.setVisible(false);
    }

    @FXML
    private void onCommentTFEntered(MouseEvent event) {
        commentBtnHbox.setVisible(true);
    }

    @FXML
    private void onCommentVBoxExited(MouseEvent event) {
        commentBtnHbox.setVisible(false);
    }

    @FXML
    private void upload(ActionEvent event) throws MalformedURLException  {
                        
                  FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image: ");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp")
        );
        Window stage = null;
        pfile = fileChooser.showOpenDialog(stage);

        /* - draw image */
        if (pfile != null) {
            file=1;
            Image image = new Image(pfile.toURI().toURL().toExternalForm());
            imv.setImage(image);
    }
    }

    @FXML
    private void tfSearchOnKeyRelese(KeyEvent event) throws Exception {
         String t = tfSearch.getText();

   
    }
    

}
