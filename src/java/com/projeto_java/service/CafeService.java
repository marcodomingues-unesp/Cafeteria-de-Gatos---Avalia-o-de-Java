package com.projeto_java.service;

import model.dao.CafeDAO;
import model.dto.CafeDTO;

import com.projeto_java.validator.CafeValidator;
import com.projeto_java.validator.ICafeValidator;

import java.util.List;

public class CafeService implements ICafeService {

    private final CafeDAO cafeDAO;
    private final ICafeValidator cafeValidator;

    public CafeService() {
        this.cafeDAO = new CafeDAO();
        this.cafeValidator = new CafeValidator();
    }

    @Override
    public List<CafeDTO> listarPedidos() {
        return cafeDAO.selecionarCafe();
    }

    @Override
    public void cadastrar(CafeDTO dadosCafe) {

        validarCafe(dadosCafe);

        cafeDAO.comprarCafe(dadosCafe);
    }

    @Override
    public void alterar(CafeDTO dadosCafe) {

        validarCafe(dadosCafe);

        cafeDAO.alterarPedido(dadosCafe);
    }

    @Override
    public void cancelar(int id) {
        cafeDAO.cancelarPedido(id);
    }

    @Override
    public void excluirTodos() {
        cafeDAO.excluirTodosPedidos();
    }

    // Valida antes de acessar o banco
    private void validarCafe(CafeDTO dadosCafe) {

        if (!cafeValidator.validar(dadosCafe)) {

            throw new IllegalArgumentException(
                    cafeValidator.getMensagemErro()
            );
        }
    }
}