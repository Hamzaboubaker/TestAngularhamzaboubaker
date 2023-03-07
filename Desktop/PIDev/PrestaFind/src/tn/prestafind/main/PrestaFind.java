package tn.prestafind.main;

import tn.prestafind.entities.User;
import tn.prestafind.gui.controllers.LoginViewController;
import java.io.IOException;
import java.sql.SQLException;
import java.util.prefs.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tn.prestafind.services.CRUDUser;
import tn.prestafind.utils.Constants;

public class PrestaFind extends Application {

    // A unique identifier for this app (for use with java.util.prefs)
    private static final String APP_NODE = "prestafind.PrestaFind";
    // Get the user preferences node for this app
    Preferences prefs = Preferences.userRoot().node(APP_NODE);
    User user = new User();
    CRUDUser crudUser = new CRUDUser();

    public static void main(String[] args) throws SQLException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.LOGIN_VIEW));
            Parent root = loader.load();
            LoginViewController loginController = loader.getController();
            Scene scene = new Scene(root);

            scene.setFill(Color.TRANSPARENT);
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.getIcons().add(new Image(Constants.STAGE_ICON));
            primaryStage.setScene(scene);

            loginController.setStage(primaryStage);
            primaryStage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
