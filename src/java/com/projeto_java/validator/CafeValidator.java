package com.projeto_java.validator;

import model.dto.CafeDTO;

import java.util.regex.Pattern;

public class CafeValidator implements Validador<CafeDTO> {

    private static final Pattern NOME_PATTERN =
            Pattern.compile("^[a-zA-ZÀ-ÿ ]+$");

    private final CafeDTO cafe;
    private String mensagemErro;

    public CafeValidator(CafeDTO cafe) {
        this.cafe = cafe;
    }

    @Override
    public boolean validar(CafeDTO valorAtual) {

        if (cafe == null) {
            mensagemErro = "Informe os dados do café.";
            return false;
        }

        if (cafe.getNomeProduto() == null ||
                cafe.getNomeProduto().trim().isEmpty()) {
            mensagemErro = "Informe o nome do produto.";
            return false;
        }

        if (!NOME_PATTERN.matcher(cafe.getNomeProduto().trim()).matches()) {
            mensagemErro = "Digite um nome de produto válido.";
            return false;
        }

        if (cafe.getTamanhoProduto() == null ||
                cafe.getTamanhoProduto().trim().isEmpty()) {
            mensagemErro = "Selecione o tamanho do café.";
            return false;
        }

        if (cafe.getTipoTorra() == null ||
                cafe.getTipoTorra().trim().isEmpty()) {
            mensagemErro = "Selecione o tipo de torra.";
            return false;
        }

        if (cafe.getPreco() <= 0) {
            mensagemErro = "Informe um preço válido.";
            return false;
        }

        mensagemErro = null;
        return true;
    }

    @Override
    public String getMensagemErro() {
        return mensagemErro;
    }

    @Override
    public CafeDTO getValor() {
        return cafe;
    }
}