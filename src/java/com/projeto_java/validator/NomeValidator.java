package com.projeto_java.validator;

import java.util.regex.Pattern;

public class NomeValidator implements Validador<String> {

    private static final Pattern NOME_PATTERN =
            Pattern.compile("^[a-zA-ZÀ-ÿ ]+$");

    private final String valor;
    private String mensagemErro;

    public NomeValidator(String valor) {
        this.valor = valor;
    }

    @Override
    public boolean validar(String nome) {

        if (nome == null || nome.trim().isEmpty()) {
            mensagemErro = "Informe o nome do produto.";
            return true;
        }

        if (!NOME_PATTERN.matcher(nome.trim()).matches()) {
            mensagemErro = "Digite um nome de produto válido.";
            return true;
        }

        mensagemErro = null;
        return false;
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