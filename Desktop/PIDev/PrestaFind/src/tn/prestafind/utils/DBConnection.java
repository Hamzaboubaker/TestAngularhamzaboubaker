package tn.prestafind.utils;

//import java.sql.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.StageStyle;

public class DBConnection {

    private Connection conn;
    private static DBConnection instance;

    private static final String DATABASE = "prestafind";
    private static final String URL = "jdbc:mysql://db4free.net:3306/" + DATABASE;
    private static final String USER = "prestafind";
    private static final String PWD = "prestafind";
//    String URL = "jdbc:mysql://localhost:3306/" + DATABASE;
//    String USER = "root";
//    String PWD = "";

    private DBConnection() {
        try {
            conn = DriverManager.getConnection(URL, USER, PWD);
            System.out.println("Connexion etablie!!!");
        } catch (SQLException ex) {
            System.out.println(Constants.MESSAGE_ERROR_CONNECTION_MYSQL);
            showException(Constants.MESSAGE_ERROR_CONNECTION_MYSQL, ex);
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static DBConnection getInstance() {
        if (DBConnection.instance == null) {
            DBConnection.instance = new DBConnection();
        }
        return DBConnection.instance;
    }

    public static Connection getConnection() {
        return DBConnection.getInstance().conn;
    }

    private void showException(String message, Exception exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Exception");
        alert.setHeaderText(Alert.AlertType.ERROR.toString());
        alert.setContentText(message);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("Details:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();

    }
}
