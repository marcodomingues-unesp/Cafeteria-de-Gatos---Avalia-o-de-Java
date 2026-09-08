package com.projeto_java.validator;

import model.dto.CafeDTO;

public class CafeValidator implements ICafeValidator {

    private String mensagemErro;

    @Override
    public boolean validar(CafeDTO dadosCafe) {

        if (dadosCafe == null) {
            mensagemErro = "Informe os dados do café.";
            return false;
        }

        CampoObrigatorioValidator nomeObrigatorio =
                new CampoObrigatorioValidator(
                        "Nome do produto",
                        dadosCafe.getNomeProduto()
                );

        if (!nomeObrigatorio.validar(nomeObrigatorio.getValor())) {
            mensagemErro = nomeObrigatorio.getMensagemErro();
            return false;
        }

        NomeValidator nomeValidator =
                new NomeValidator(dadosCafe.getNomeProduto());

        if (!nomeValidator.validar(dadosCafe.getNomeProduto())) {
            mensagemErro = nomeValidator.getMensagemErro();
            return false;
        }

        CampoObrigatorioValidator tamanhoObrigatorio =
                new CampoObrigatorioValidator(
                        "Tamanho do café",
                        dadosCafe.getTamanhoProduto()
                );

        if (!tamanhoObrigatorio.validar(tamanhoObrigatorio.getValor())) {
            mensagemErro = tamanhoObrigatorio.getMensagemErro();
            return false;
        }

        CampoObrigatorioValidator torraObrigatoria =
                new CampoObrigatorioValidator(
                        "Tipo de torra",
                        dadosCafe.getTipoTorra()
                );

        if (!torraObrigatoria.validar(torraObrigatoria.getValor())) {
            mensagemErro = torraObrigatoria.getMensagemErro();
            return false;
        }

        if (dadosCafe.getPreco() <= 0) {
            mensagemErro = "O preço deve ser maior que zero.";
            return false;
        }

        mensagemErro = null;
        return true;
    }

    @Override
    public String getMensagemErro() {
        return mensagemErro;
    }
}