package com.projeto_java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

//Inicializa a aplicação com JavaFX

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/projeto_java/main.fxml")
        );

        Scene scene = new Scene(loader.load(), 1100, 720);

        stage.setTitle("🐈☕ Cafeteria dos Gatos");

        stage.setMinWidth(1000);
        stage.setMinHeight(700);

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}