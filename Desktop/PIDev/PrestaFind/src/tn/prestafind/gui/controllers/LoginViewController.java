package tn.prestafind.gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import tn.prestafind.entities.User;
import tn.prestafind.services.CRUDUser;
import tn.prestafind.utils.Animations;
import tn.prestafind.utils.Constants;
import tn.prestafind.utils.TextFieldMask;
import tn.prestafind.utils.TextFieldsValidators;

public class LoginViewController implements Initializable {

    private Stage stage;
    private double x = 0, y = 0;

    @FXML
    private AnchorPane sideBar;
    @FXML
    private JFXButton loginBtn;
    @FXML
    private FontAwesomeIconView icon;
    @FXML
    private JFXTextField txtPassword;
    @FXML
    private AnchorPane parentAnchorPane;
    @FXML
    private AnchorPane loginAnchorPane;
    @FXML
    private Label createAccountLabel;
    @FXML
    private JFXTextField emailTextField;
    @FXML
    private JFXPasswordField passwordField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showPassword();
        setValidations();
        selectText();
        setMask();
//        animations();
        loginAnchorPane.setUserData("SingInPage");

        parentAnchorPane.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });

        parentAnchorPane.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - x);
            stage.setY(mouseEvent.getScreenY() - y);
        });

        double initPos = loginAnchorPane.getLayoutX();
        loginAnchorPane.setLayoutX(272);
        TranslateTransition t = new TranslateTransition(Duration.seconds(1.5), loginAnchorPane);
        t.setToX(272);
        t.play();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void animations() {
        Animations.fadeInUp(emailTextField);
        Animations.fadeInUp(passwordField);
        Animations.fadeInUp(loginBtn);
    }

    private void setValidations() {
        TextFieldsValidators.toJFXPasswordField(passwordField);
        TextFieldsValidators.toJFXEmailField(emailTextField);
//        TextFieldsValidators.toRequiredJFXTextField(emailTextField);
    }

    private void setMask() {
//        TextFieldMask.onlyNumbersAndLettersNotSpaces(emailTextField, 40);
        TextFieldMask.onlyNumbersAndLettersNotSpaces(passwordField, 40);
    }

    private void selectText() {
        TextFieldMask.selectText(passwordField);
        TextFieldMask.selectText(emailTextField);
    }

    private void showPassword() {
        txtPassword.managedProperty().bind(icon.pressedProperty());
        txtPassword.visibleProperty().bind(icon.pressedProperty());
        txtPassword.textProperty().bindBidirectional(passwordField.textProperty());

        passwordField.managedProperty().bind(icon.pressedProperty().not());
        passwordField.visibleProperty().bind(icon.pressedProperty().not());

//        icon.pressedProperty().addListener((o, oldVal, newVal) -> {
//            if (newVal) {
//                icon.setIcon(FontAwesomeIcon.EYE);
//            } else {
//                icon.setIcon(FontAwesomeIcon.EYE_SLASH);
//            }
//        });
    }

    @FXML
    private void closeBtnClicked(MouseEvent event) {
        stage.close();
    }

    @FXML
    private void loginClicked(ActionEvent event) throws IOException, SQLException {
        User user = new User();
        CRUDUser crudUser = new CRUDUser();
        String emailString = emailTextField.getText();
        String passwordString = passwordField.getText();

        if (loginAnchorPane.getUserData().equals("SingInPage")) {
            try {
                user = crudUser.getUserByEmail(emailString);
                if (crudUser.login(emailString, passwordString)) {
                    String token = UUID.randomUUID().toString();
                    user.setToken(token);
                    user.setEtat(User.EtatUsr.ACTIF);
                    crudUser.modifierUser(user);

                    transitionToBlog();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur ajout user");
                    alert.setContentText("Email ou mot de passe incorrect");
                    alert.showAndWait();
                }
            } catch (SQLException ex) {
                Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            user = new User(emailString, "John",
                    "Doe", "johndoe", passwordString, 0,
                    User.Role.USER, Calendar.getInstance(), User.Sexe.HOMME,
                    123456789, User.EtatUsr.INACTIF, "https://example.com/profile.jpg");

            System.out.println("Ajout d'un utilisateur...");
            try {
                crudUser.ajouterUser(user);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Erreur ajout user");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void onCreateAccountClicked(MouseEvent event) {
        if (loginAnchorPane.getUserData().equals("SingInPage")) {
            TranslateTransition t = new TranslateTransition(Duration.seconds(1.2), sideBar);
            t.setToX(330);
            t.play();

            t = new TranslateTransition(Duration.seconds(1.2), loginAnchorPane);
            t.setToX(60);
            t.play();

            loginBtn.setText("Sign Up");
            createAccountLabel.setText("       Login");
            loginAnchorPane.setUserData("SingUpPage");
        } else {
            TranslateTransition t = new TranslateTransition(Duration.seconds(1.2), sideBar);
            t.setToX(290);
            t.play();

            t = new TranslateTransition(Duration.seconds(1.2), loginAnchorPane);
            t.setToX(543);
            t.play();

            loginBtn.setText("Login");
            createAccountLabel.setText("Create Account");
            loginAnchorPane.setUserData("SingInPage");
        }
    }

    private void transitionToBlog() throws IOException {
        Stage secondStage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.BLOG_VIEW));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        secondStage.setScene(scene);
        secondStage.getIcons().add(new Image(Constants.STAGE_ICON));
        secondStage.setTitle("PrestaFind");

        secondStage.show();
        stage.close();
    }
}
