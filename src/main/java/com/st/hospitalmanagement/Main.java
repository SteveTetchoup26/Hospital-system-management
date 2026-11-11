package com.st.hospitalmanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Police des icônes Gluon (<Icon>) : sans elle, les icônes restent invisibles.
        Font.loadFont(Main.class.getResourceAsStream("fonts/MaterialIcons-Regular.ttf"), 20);

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hospital Management");

        stage.setMinHeight(550);
        stage.setMinWidth(350);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        System.out.println("App started");
        launch();
    }
}