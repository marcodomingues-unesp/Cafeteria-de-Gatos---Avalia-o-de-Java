package com.projeto_java.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public final class AlertUtil {

    // Impede a criação de objetos da classe
    private AlertUtil() {
    }

    // Exibe uma mensagem de informação
    public static void showInformation(String mensagem) {

        Alert alert = new Alert(
                Alert.AlertType.INFORMATION
        );

        alert.setTitle("Informação");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);

        alert.showAndWait();
    }

    // Exibe uma mensagem de aviso
    public static void showWarning(String mensagem) {

        Alert alert = new Alert(
                Alert.AlertType.WARNING
        );

        alert.setTitle("Atenção");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);

        alert.showAndWait();
    }

    // Exibe uma mensagem de erro
    public static void showError(String mensagem) {

        Alert alert = new Alert(
                Alert.AlertType.ERROR
        );

        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);

        alert.showAndWait();
    }

    // Solicita confirmação antes da ação
    public static boolean showConfirmation(String mensagem) {

        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION
        );

        alert.setTitle("Confirmação");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);

        ButtonType botaoOk = new ButtonType("OK");
        ButtonType botaoCancelar = new ButtonType("Cancelar");

        alert.getButtonTypes().setAll(
                botaoOk,
                botaoCancelar
        );

        Optional<ButtonType> resultado =
                alert.showAndWait();

        return resultado.isEmpty()
                || resultado.get() != botaoOk;
    }
}