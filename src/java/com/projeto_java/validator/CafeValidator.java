package com.projeto_java.validator;

import java.util.regex.Pattern;

import static com.projeto_java.util.AlertUtil.showWarning;

public class CafeValidator {

    public static boolean validarCafe(String nomeProduto, String tamanhoProduto, String tipoTorra, double preco) {

        // Verifica se todos os campos foram preenchidos
        if (nomeProduto.isEmpty() || tamanhoProduto.isEmpty() || tipoTorra.isEmpty() || preco <= 0) {

            showWarning("Preencha todos os campos antes de prosseguir.");
            return false;
        }

        // Valida o nome do produto
        if (!validarNomeProduto(nomeProduto)) {

            showWarning("Digite um nome de produto válido.");
            return false;
        }

        return true;
    }

    private static boolean validarNomeProduto(String nomeProduto) {

        return Pattern.matches("^[a-zA-ZÀ-ÿ ]+$", nomeProduto);
    }
}