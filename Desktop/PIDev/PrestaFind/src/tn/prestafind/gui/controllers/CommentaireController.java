/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.prestafind.gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.converter.LocalDateTimeStringConverter;
import static jdk.nashorn.internal.runtime.Debug.id;
import tn.prestafind.entities.helpers.Post;
import tn.prestafind.entities.helpers.PostAudience;
import tn.prestafind.services.CRUDArticle;
import tn.prestafind.services.CRUDCommentaire;
import tn.prestafind.utils.JFXDialogTool;
import tn.prestafind.services.BadWords;
import org.controlsfx.control.Notifications;
import sun.awt.image.ImageCache;
import tn.prestafind.entities.Commentaire;

/**
 * FXML Controller class
 *
 * @author User31
 */
public class CommentaireController implements Initializable {

    private long startTime = 0;
    private JFXDialogTool dialogModifyPost;
    private Post post;

    @FXML
    private VBox postVBox;
    @FXML
    private ImageView imgProfile;
    @FXML
    private Label username;
    @FXML
    private ImageView imgVerified;
    @FXML
    private Label date;
    @FXML
    private ImageView audience;
    @FXML
    private ImageView postOptions;
    @FXML
    private Label caption;
    @FXML
    private HBox likeContainer;
    @FXML
    private Label nbLikes;
    @FXML
    private Label nbDislikes;
    @FXML
    private Label nbReports;
    @FXML
    private ImageView imgView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    //String contenu = post.getCaption();
    //LocalDateTime datePublication=new LocalDateTime();
    // Commentaire co =new Commentaire();
    public void setData(Post post) {
        //BadWords bw =new BadWords();
        this.post = post;
        Image img;
        img = new Image(getClass().getResourceAsStream(post.getAccount().getProfileImg()));
        imgProfile.setImage(img);
// Effacer la cache des images



File file = new File(post.getImage());
if (file.exists()) {
    Image imgComment = new Image(file.toURI().toString());
 
    imgView.setImage(imgComment);
    
   
} else {
    // Le fichier n'existe pas
    // Afficher un message d'erreur ou effectuer une autre action appropriée
}



         imgView.setVisible(true);
        username.setText(post.getAccount().getName());
        if (post.getAccount().isVerified()) {
            imgVerified.setVisible(true);
        } else {
            imgVerified.setVisible(false);
        }

        date.setText(post.getDate());
        if (post.getAudience() == PostAudience.PUBLIC) {
            img = new Image(getClass().getResourceAsStream(PostAudience.PUBLIC.getImgSrc()));
        } else {
            img = new Image(getClass().getResourceAsStream(PostAudience.FRIENDS.getImgSrc()));
        }
        audience.setImage(img);
        //String co=caption.getText();

//        if(!BadWords.filterText(co))
//        {
        if (post.getCaption() != null && !post.getCaption().isEmpty()) {

            //////////////////////////////////
            BadWords.loadConfigs();
            if (BadWords.filterText(post.getCaption())) {
                post.setCaption("*****");
            } else {
                caption.setText(post.getCaption());
                
            }
            //caption.setText("bad word");

        } else {
            caption.setManaged(false);
        }
//        }}
//             else
//             {
//                 
//                 
//                 System.out.println("Bad Word trouvé");
//                     }
//            

//        if (post.getImage() != null && !post.getImage().isEmpty()) {
//            img = new Image(getClass().getResourceAsStream(post.getImage()));
//            imgPost.setImage(img);
//        } else {
//            imgPost.setVisible(false);
//            imgPost.setManaged(false);
//        }
        nbReports.setText(String.valueOf(post.getNbReports()));
        nbLikes.setText(post.getNbLikes() + " likes");
        nbDislikes.setText(post.getNbDisLikes() + " dislikes");
        postOptions.setUserData(post.getId());
   
    }

    @FXML
    private void onLikeContainerMouseReleased(MouseEvent event) {
    }

    @FXML
    private void onLikeContainerPressed(MouseEvent event) {
    }

    @FXML
    private void commentOptionsClicked(MouseEvent ev) {

        JFXPopup popup = new JFXPopup();

        JFXButton deleteButton = new JFXButton("Delete");
        deleteButton.setPrefSize(100, 40);
        JFXButton editButton = new JFXButton("Edit");
        editButton.setPrefSize(100, 40);
        JFXButton reportButton = new JFXButton("Report");
        reportButton.setPrefSize(100, 40);

        VBox buttonBox = new VBox(10);
//        popup.setPopupContent(new StackPane(deleteButton, editButton, reportButton));
        buttonBox.getChildren().addAll(deleteButton, editButton, reportButton);
        popup.setPopupContent(buttonBox);

        postOptions.setOnMouseClicked(event -> {
            popup.show(postOptions, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, event.getX(), event.getY());
        });

        deleteButton.setOnAction(event -> {
            try {
                CRUDCommentaire crudCommentaire = new CRUDCommentaire();
                crudCommentaire.supprimerCommentaire((int) postOptions.getUserData());
                popup.hide();

                Event customEvent = new Event(Event.ANY);
                postOptions.fireEvent(customEvent);

//            postVBox.setVisible(false);
                caption.setText("This post has been deleted.");
            } catch (SQLException ex) {
                Logger.getLogger(CommentaireController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        editButton.setOnAction(event -> {
        });

        reportButton.setOnAction(event -> {
            System.out.println("Hello from Report");
        });

    }

    @FXML
    private void like(MouseEvent event) {

    }

}
