package com.projeto_java.validator;

import model.dto.CafeDTO;

import java.util.ArrayList;
import java.util.List;

public class CafeValidator implements ICafeValidator {

    @Override
    public boolean validar(CafeDTO dadosCafe) {

        List<Validador<String>> validadores = new ArrayList<>();

        validadores.add(
                new CampoObrigatorioValidador(
                        "Nome do produto",
                        dadosCafe.getNomeProduto()
                )
        );

        validadores.add(
                new NomeValidator(dadosCafe.getNomeProduto())
        );

        validadores.add(
                new CampoObrigatorioValidador(
                        "Tamanho do café",
                        dadosCafe.getTamanhoProduto()
                )
        );

        validadores.add(
                new CampoObrigatorioValidador(
                        "Tipo de torra",
                        dadosCafe.getTipoTorra()
                )
        );

        for (Validador<String> validador : validadores) {

            if (!validador.validar(validador.getValor())) {
                validador.getMensagemErro();
                return false;
            }
        }

        return true;
    }

}