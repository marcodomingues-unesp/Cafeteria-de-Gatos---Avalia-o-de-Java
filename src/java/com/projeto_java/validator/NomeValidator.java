package com.projeto_java.validator;

import java.util.regex.Pattern;

public class NomeValidator implements Validador<String> {

    // Define o formato permitido para o nome
    private static final Pattern NOME_PATTERN =
            Pattern.compile("^[a-zA-ZÀ-ÿ ]+$");

    private final String valor;
    private String mensagemErro;

    public NomeValidator(String valor) {
        this.valor = valor;
    }

    @Override
    public boolean validar(String nome) {

        // Verifica se o nome foi preenchido
        if (nome == null || nome.trim().isEmpty()) {
            mensagemErro = "Informe o nome do produto.";
            return false;
        }

        // Verifica se o nome possui apenas caracteres válidos
        if (!NOME_PATTERN.matcher(nome.trim()).matches()) {
            mensagemErro = "Digite um nome de produto válido.";
            return false;
        }

        // Indica que a validação foi aprovada
        mensagemErro = null;
        return true;
    }

    @Override
    public String getMensagemErro() {
        return mensagemErro;
    }

    @Override
    public String getValor() {
        return valor;
    }
}