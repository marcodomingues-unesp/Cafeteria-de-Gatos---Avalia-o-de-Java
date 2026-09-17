package com.projeto_java.validator;

import model.dto.CafeDTO;

public class CafeValidator implements ICafeValidator {

    // Guarda a mensagem da validação
    private String mensagemErro;

    @Override
    public boolean validar(CafeDTO dadosCafe) {

        // Verifica se o objeto foi informado
        if (dadosCafe == null) {
            mensagemErro = "Informe os dados do café.";
            return false;
        }

        // Valida o preenchimento do nome
        CampoObrigatorioValidator nomeObrigatorio =
                new CampoObrigatorioValidator(
                        "Nome do produto",
                        dadosCafe.getNomeProduto()
                );

        if (!nomeObrigatorio.validar(
                nomeObrigatorio.getValor())) {

            mensagemErro =
                    nomeObrigatorio.getMensagemErro();

            return false;
        }

        // Valida o formato do nome
        NomeValidator nomeValidator =
                new NomeValidator(
                        dadosCafe.getNomeProduto()
                );

        if (!nomeValidator.validar(
                dadosCafe.getNomeProduto())) {

            mensagemErro =
                    nomeValidator.getMensagemErro();

            return false;
        }

        // Valida o preenchimento do tamanho
        CampoObrigatorioValidator tamanhoObrigatorio =
                new CampoObrigatorioValidator(
                        "Tamanho do produto",
                        dadosCafe.getTamanhoProduto()
                );

        if (!tamanhoObrigatorio.validar(
                tamanhoObrigatorio.getValor())) {

            mensagemErro =
                    tamanhoObrigatorio.getMensagemErro();

            return false;
        }

        // Valida o preenchimento da torra
        CampoObrigatorioValidator torraObrigatoria =
                new CampoObrigatorioValidator(
                        "Tipo de torra",
                        dadosCafe.getTipoTorra()
                );

        if (!torraObrigatoria.validar(
                torraObrigatoria.getValor())) {

            mensagemErro =
                    torraObrigatoria.getMensagemErro();

            return false;
        }

        // Valida o preço
        PrecoValidator precoValidator =
                new PrecoValidator(
                        dadosCafe.getPreco()
                );

        if (!precoValidator.validar(
                precoValidator.getValor())) {

            mensagemErro =
                    precoValidator.getMensagemErro();

            return false;
        }

        // Limpa mensagens de validações anteriores
        mensagemErro = null;

        return true;
    }

    // Retorna a mensagem do erro encontrado
    @Override
    public String getMensagemErro() {
        return mensagemErro;
    }
}