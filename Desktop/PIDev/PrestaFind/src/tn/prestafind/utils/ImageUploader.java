package tn.prestafind.utils;

import java.io.*;
import java.net.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ImageUploader extends Application {

    private static final String IMGUR_CLIENT_ID = "34e543bd40dde4a";
    private static final String IMGUR_CLIENT_SECRET = "32cacf9bbef7090062a3c6d536e32ebdaec4ef56";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        final FileChooser fileChooser = new FileChooser();
        final Button selectButton = new Button("Select Image");

        selectButton.setOnAction(event -> {
            File file = fileChooser.showOpenDialog(primaryStage);

            if (file != null) {
                try {
                    uploadImage(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(selectButton);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void uploadImage(File file) throws IOException {
        URL url = new URL("https://api.imgur.com/3/image");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + IMGUR_CLIENT_SECRET);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write("image=" + encodeImage(file));
        writer.flush();
        writer.close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
    }

    private String encodeImage(File file) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        FileInputStream fileInputStream = new FileInputStream(file);

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }
        fileInputStream.close();

        byte[] byteArray = byteArrayOutputStream.toByteArray();

        return java.util.Base64.getEncoder().encodeToString(byteArray);
    }
}
