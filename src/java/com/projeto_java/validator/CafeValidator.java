package com.projeto_java.validator;

import java.util.regex.Pattern;

public class CafeValidator {

    private static final Pattern nomePattern =
            Pattern.compile("^[a-zA-ZÀ-ÿ ]+$");

    // Valida todos os dados
    public static String validarCafe(
            String nomeProduto,
            String tamanhoProduto,
            String tipoTorra,
            double preco) {

        String erro = validarNome(nomeProduto);
        if (erro != null) return erro;

        erro = validarTamanho(tamanhoProduto);
        if (erro != null) return erro;

        erro = validarTorra(tipoTorra);
        if (erro != null) return erro;

        return validarPreco(preco);
    }

    // Valida o nome
    public static String validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return "Informe o nome do produto.";
        }

        if (!nomePattern.matcher(nome.trim()).matches()) {
            return "Digite um nome de produto válido.";
        }

        return null;
    }

    // Valida o tamanho
    public static String validarTamanho(String tamanho) {
        if (tamanho == null || tamanho.trim().isEmpty()) {
            return "Selecione o tamanho do café.";
        }

        return null;
    }

    // Valida a torra
    public static String validarTorra(String torra) {
        if (torra == null || torra.trim().isEmpty()) {
            return "Selecione o tipo de torra.";
        }

        return null;
    }

    // Valida o preço
    public static String validarPreco(double preco) {
        if (preco <= 0) {
            return "Informe um preço válido.";
        }

        return null;
    }
}