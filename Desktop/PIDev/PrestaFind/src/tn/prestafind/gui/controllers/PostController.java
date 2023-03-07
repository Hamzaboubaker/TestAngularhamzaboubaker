package tn.prestafind.gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import tn.prestafind.entities.helpers.Post;
import tn.prestafind.entities.helpers.PostAudience;
import tn.prestafind.entities.helpers.Reactions;
import java.io.File;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import tn.prestafind.services.CRUDArticle;
import tn.prestafind.utils.JFXDialogTool;

public class PostController {

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
    private Label caption;

    @FXML
    private ImageView imgPost;

    @FXML
    private Label nbReactions;

    @FXML
    private Label nbComments;

    @FXML
    private Label nbShares;

    @FXML
    private HBox reactionsContainer;

    @FXML
    private ImageView imgLike;

    @FXML
    private ImageView imgLove;

    @FXML
    private ImageView imgCare;

    @FXML
    private ImageView imgHaha;

    @FXML
    private ImageView imgWow;

    @FXML
    private ImageView imgSad;

    @FXML
    private ImageView imgAngry;

    @FXML
    private HBox likeContainer;

    @FXML
    private ImageView imgReaction;

    @FXML
    private Label reactionName;

    @FXML
    private ImageView postOptions;

    @FXML
    private VBox postVBox;

    private long startTime = 0;
    private Reactions currentReaction;
    private JFXDialogTool dialogModifyPost;
    private Post post;
    @FXML
    private HBox commentHbox;

    @FXML
    public void onLikeContainerPressed(MouseEvent me) {
        startTime = System.currentTimeMillis();
    }

    @FXML
    public void onLikeContainerMouseReleased(MouseEvent me) {
        if (System.currentTimeMillis() - startTime > 500) {
            reactionsContainer.setVisible(true);
        } else {
            if (reactionsContainer.isVisible()) {
                reactionsContainer.setVisible(false);
            }
            if (currentReaction == Reactions.NON) {
                setReaction(Reactions.LIKE);
            } else {
                setReaction(Reactions.NON);
            }
        }
    }

    @FXML
    public void onReactionImgPressed(MouseEvent me) {
        switch (((ImageView) me.getSource()).getId()) {
            case "imgLove":
                setReaction(Reactions.LOVE);
                break;
            case "imgCare":
                setReaction(Reactions.CARE);
                break;
            case "imgHaha":
                setReaction(Reactions.HAHA);
                break;
            case "imgWow":
                setReaction(Reactions.WOW);
                break;
            case "imgSad":
                setReaction(Reactions.SAD);
                break;
            case "imgAngry":
                setReaction(Reactions.ANGRY);
                break;
            default:
                setReaction(Reactions.LIKE);
                break;
        }
        reactionsContainer.setVisible(false);
    }

    public void setReaction(Reactions reaction) {
        Image image = new Image(getClass().getResourceAsStream(reaction.getImgSrc()));
        imgReaction.setImage(image);
        reactionName.setText(reaction.getName());
        reactionName.setTextFill(Color.web(reaction.getColor()));

        if (currentReaction == Reactions.NON) {
            post.setTotalReactions(post.getTotalReactions() + 1);
        }

        currentReaction = reaction;

        if (currentReaction == Reactions.NON) {
            post.setTotalReactions(post.getTotalReactions() - 1);
        }

        nbReactions.setText(String.valueOf(post.getTotalReactions()));
    }

    public void setData(Post post) {
        this.post = post;
        Image img;
        img = new Image(getClass().getResourceAsStream(post.getAccount().getProfileImg()));
        imgProfile.setImage(img);
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

        if (post.getCaption() != null && !post.getCaption().isEmpty()) {
            caption.setText(post.getCaption());
        } else {
            caption.setManaged(false);
        }

        if (post.getImage() != null && !post.getImage().isEmpty()) {
            img = new Image(getClass().getResourceAsStream(post.getImage()));
            imgPost.setImage(img);
        } else {
            imgPost.setVisible(false);
            imgPost.setManaged(false);
        }

        nbReactions.setText(String.valueOf(post.getTotalReactions()));
        nbComments.setText(post.getNbComments() + " comments");
        nbShares.setText(post.getNbShares() + " shares");

        postOptions.setUserData(post.getId());

        currentReaction = Reactions.NON;
    }

    @FXML
    private void postOptionsClicked(MouseEvent ev) {

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
            CRUDArticle crudArticle = new CRUDArticle();
            crudArticle.supprimerArticle((int) postOptions.getUserData());
            popup.hide();

            Event customEvent = new Event(Event.ANY);
            postOptions.fireEvent(customEvent);

//            postVBox.setVisible(false);
            caption.setText("This post has been deleted.");
            Image image = new Image(new File("C:\\Users\\User31\\Documents\\00\\Esprit\\Sem6\\Projet développement Web Java\\PIDev\\PrestaFind\\src\\resources\\media\\image-not-found.png").toURI().toString());
            imgPost.setImage(image);
        });

        editButton.setOnAction(event -> {
        });

        reportButton.setOnAction(event -> {
            System.out.println("Hello from Report");
        });
    }

    @FXML
    private void onCommentHboxPressed(MouseEvent event) {
    }
}
