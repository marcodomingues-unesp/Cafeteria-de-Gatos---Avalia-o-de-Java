package com.projeto_java.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertUtil {

    // Impede a criação de objetos dessa classe
    private AlertUtil() {
    }

    // Exibe uma mensagem de informação
    public static void showInformation(String mensagem) {
        mostrar(
                Alert.AlertType.INFORMATION,
                "Informação",
                mensagem
        );
    }

    // Exibe uma mensagem de aviso
    public static void showWarning(String mensagem) {
        mostrar(
                Alert.AlertType.WARNING,
                "Aviso",
                mensagem
        );
    }

    // Exibe uma mensagem de erro
    public static void showError(String mensagem) {
        mostrar(
                Alert.AlertType.ERROR,
                "Erro",
                mensagem
        );
    }

    // Exibe uma confirmação e retorna a escolha do usuário
    public static boolean showConfirmation(String mensagem) {

        Alert alert = criar(
                Alert.AlertType.CONFIRMATION,
                "Confirmação",
                mensagem
        );

        Optional<ButtonType> resultado =
                alert.showAndWait();

        return resultado.isPresent()
                && resultado.get() == ButtonType.OK;
    }

    // Exibe o alerta na tela
    private static void mostrar(
            Alert.AlertType tipo,
            String titulo,
            String mensagem) {

        criar(tipo, titulo, mensagem).showAndWait();
    }

    // Cria e configura o alerta
    private static Alert criar(
            Alert.AlertType tipo,
            String titulo,
            String mensagem) {

        Alert alert = new Alert(tipo);

        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);

        return alert;
    }
}