package com.projeto_java.main;

import com.projeto_java.controller.MainController;
import com.projeto_java.service.CafeService;
import com.projeto_java.service.ICafeService;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // Cria o serviço usado pelo Controller
        ICafeService cafeService = new CafeService();

        // Carrega a tela definida no FXML
        FXMLLoader loader = new FXMLLoader(
                Main.class.getResource(
                        "/com/projeto_java/main.fxml"
                )
        );

        // Injeta o serviço no Controller
        loader.setControllerFactory(
                _ -> new MainController(cafeService)
        );

        // Cria a cena da aplicação
        Scene scene = new Scene(
                loader.load(),
                1320,
                720
        );

        // Configura a janela principal
        stage.setTitle("☕ Cafeteria Maragato");
        stage.setScene(scene);

        stage.setMinWidth(1000);
        stage.setMinHeight(700);

        // Exibe a aplicação
        stage.show();
    }
}