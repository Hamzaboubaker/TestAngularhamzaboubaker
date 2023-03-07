package tn.prestafind.main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import tn.prestafind.utils.Constants;

public class TestViews extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {
       
       // FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.BLOG_VIEW));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/prestafind/gui/views/listeCommentaires.fxml"));
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/prestafind/gui/views/CaptchaFXMLSkin.fxml"));
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/prestafind/gui/views/captcha.fxml"));
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/prestafind/gui/views/blog.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

//        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("PrestaFind");
        primaryStage.getIcons().add(new Image(Constants.STAGE_ICON));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws SQLException {
        launch(args);
//        HelperFunctions.afficherMenu();
    }

}
