package com.projeto_java.validator;

import model.dto.CafeDTO;

import java.util.ArrayList;
import java.util.List;

public class CafeValidator implements ICafeValidator {

    @Override
    public boolean validar(CafeDTO dadosCafe) {

        List<Validator<String>> validadores = new ArrayList<>();

        validadores.add(
                new CampoObrigatorioValidator(
                        "Nome do produto",
                        dadosCafe.getNomeProduto()
                )
        );

        validadores.add(
                new NomeValidator(dadosCafe.getNomeProduto())
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

        for (Validator<String> validador : validadores) {

            if (!validador.validar(validador.getValor())) {
                validador.getMensagemErro();
                return false;
            }
        }

        return true;
    }

}