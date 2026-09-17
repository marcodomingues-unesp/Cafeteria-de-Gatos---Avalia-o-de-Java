package com.projeto_java.service;

import model.dao.CafeDAO;
import model.dto.CafeDTO;

import com.projeto_java.validator.CafeValidator;
import com.projeto_java.validator.ICafeValidator;

import java.util.List;

public class CafeService implements ICafeService {

    private final CafeDAO cafeDAO;
    private final ICafeValidator cafeValidator;

    // Inicializa as dependências do serviço
    public CafeService() {
        this.cafeDAO = new CafeDAO();
        this.cafeValidator = new CafeValidator();
    }

    // Busca os pedidos no banco
    @Override
    public List<CafeDTO> listarPedidos() {
        return cafeDAO.selecionarCafe();
    }

    // Valida e cadastra um pedido
    @Override
    public void cadastrar(CafeDTO dadosCafe) {

        validarCafe(dadosCafe);

        cafeDAO.comprarCafe(dadosCafe);
    }

    // Valida e altera um pedido
    @Override
    public void alterar(CafeDTO dadosCafe) {

        validarCafe(dadosCafe);

        cafeDAO.alterarPedido(dadosCafe);
    }

    // Cancela um pedido pelo ID
    @Override
    public void cancelar(int id) {
        cafeDAO.cancelarPedido(id);
    }

    // Exclui todos os pedidos
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