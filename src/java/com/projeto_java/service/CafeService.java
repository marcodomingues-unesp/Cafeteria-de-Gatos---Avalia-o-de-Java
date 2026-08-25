package com.projeto_java.service;

import model.dao.CafeDAO;
import model.dto.CafeDTO;
import com.projeto_java.validator.CafeValidator;

import java.util.List;

public class CafeService implements ICafeService {

    private final CafeDAO cafeDAO = new CafeDAO();

    @Override
    public List<CafeDTO> listarPedidos() {
        return cafeDAO.selecionarCafe();
    }

    @Override
    public void cadastrar(CafeDTO cafe) {
        validar(cafe);
        cafeDAO.comprarCafe(cafe);
    }

    @Override
    public void alterar(CafeDTO cafe) {
        validar(cafe);
        cafeDAO.alterarPedido(cafe);
    }

    @Override
    public void cancelar(int id) {
        cafeDAO.cancelarPedido(id);
    }

    @Override
    public void excluirTodos() {
        cafeDAO.excluirTodosPedidos();
    }

    private void validar(CafeDTO cafe) {

        CafeValidator validator = new CafeValidator(cafe);

        if (!validator.validar(cafe)) {
            throw new IllegalArgumentException(
                    validator.getMensagemErro()
            );
        }
    }
}
