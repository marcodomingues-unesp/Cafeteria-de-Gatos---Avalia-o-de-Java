package com.projeto_java.validator;

import model.dto.CafeDTO;

import java.util.regex.Pattern;

public class CafeValidator implements Validador<CafeDTO> {

    // Permite apenas letras e espaços no nome do produto
    private static final Pattern NOME_PATTERN =
            Pattern.compile("^[a-zA-ZÀ-ÿ ]+$");

    private final CafeDTO cafe;
    private String mensagemErro;

    // Recebe o café que será validado
    public CafeValidator(CafeDTO cafe) {
        this.cafe = cafe;
    }

    @Override
    public boolean validar(CafeDTO valorAtual) {

        // Verifica se os dados do café foram informados
        if (cafe == null) {
            mensagemErro = "Informe os dados do café.";
            return false;
        }

        // Verifica se o nome foi preenchido
        if (cafe.getNomeProduto() == null ||
                cafe.getNomeProduto().trim().isEmpty()) {
            mensagemErro = "Informe o nome do produto.";
            return false;
        }

        // Verifica se o nome contém apenas letras e espaços
        if (!NOME_PATTERN.matcher(cafe.getNomeProduto().trim()).matches()) {
            mensagemErro = "Digite um nome de produto válido.";
            return false;
        }

        // Verifica se o tamanho foi selecionado
        if (cafe.getTamanhoProduto() == null ||
                cafe.getTamanhoProduto().trim().isEmpty()) {
            mensagemErro = "Selecione o tamanho do café.";
            return false;
        }

        // Verifica se o tipo de torra foi selecionado
        if (cafe.getTipoTorra() == null ||
                cafe.getTipoTorra().trim().isEmpty()) {
            mensagemErro = "Selecione o tipo de torra.";
            return false;
        }

        // Verifica se o preço é maior que zero
        if (cafe.getPreco() <= 0) {
            mensagemErro = "Informe um preço válido.";
            return false;
        }

        // Todas as validações foram aprovadas
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
