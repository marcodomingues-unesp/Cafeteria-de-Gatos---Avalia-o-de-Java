package com.projeto_java.validator;

import model.dto.CafeDTO;

import java.util.ArrayList;
import java.util.List;

public class CafeValidator implements ICafeValidator {

    private String mensagemErro;

    @Override
    public boolean validar(CafeDTO dadosCafe) {

        if (dadosCafe == null) {
            mensagemErro = "Informe os dados do café.";
            return false;
        }

        List<Validador<String>> validadores =
                new ArrayList<>();

        validadores.add(
                new CampoObrigatorioValidator(
                        "Nome do produto",
                        dadosCafe.getNomeProduto()
                )
        );

        validadores.add(
                new NomeValidator(
                        dadosCafe.getNomeProduto()
                )
        );

        validadores.add(
                new CampoObrigatorioValidator(
                        "Tamanho do café",
                        dadosCafe.getTamanhoProduto()
                )
        );

        validadores.add(
                new CampoObrigatorioValidator(
                        "Tipo de torra",
                        dadosCafe.getTipoTorra()
                )
        );

        for (Validador<String> validador : validadores) {

            if (validador.validar(
                    validador.getValor()
            )) {

                mensagemErro =
                        validador.getMensagemErro();

                return false;
            }
        }

        Validador<Double> precoValidator =
                new PrecoValidator(
                        dadosCafe.getPreco()
                );

        if (precoValidator.validar(
                precoValidator.getValor()
        )) {

            mensagemErro =
                    precoValidator.getMensagemErro();

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