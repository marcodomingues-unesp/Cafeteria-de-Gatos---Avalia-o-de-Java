package com.projeto_java.main;

import com.projeto_java.controller.MainController;
import com.projeto_java.service.CafeService;
import com.projeto_java.service.ICafeService;
import com.projeto_java.validator.CafeValidator;
import com.projeto_java.validator.ICafeValidator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Inicializa a aplicação JavaFX
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // Cria o serviço da aplicação
        ICafeService cafeService = new CafeService();

        // Cria o validador da aplicação
        ICafeValidator cafeValidator = new CafeValidator();

        // Localiza o arquivo FXML
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/com/projeto_java/main.fxml"
                )
        );

        // Injeta as dependências no Controller
        loader.setControllerFactory(controllerClass -> {

            if (controllerClass == MainController.class) {
                return new MainController(
                        cafeService,
                        cafeValidator
                );
            }

            throw new IllegalArgumentException(
                    "Controller não suportado: "
                            + controllerClass.getName()
            );
        });

        // Carrega a interface
        Scene scene = new Scene(
                loader.load(),
                1320,
                720
        );

        // Configura a janela
        stage.setTitle("🐈☕ Cafeteria dos Gatos");
        stage.setMinWidth(1000);
        stage.setMinHeight(700);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    // Encerra a aplicação corretamente
    @Override
    public void stop() {
        System.out.println("Aplicação encerrada.");
    }
}