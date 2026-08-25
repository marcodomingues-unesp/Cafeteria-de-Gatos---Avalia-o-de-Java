package com.projeto_java.service;

import java.util.List;

import model.dto.CafeDTO;

public interface ICafeService {

    void cadastrar(CafeDTO cafe);

    void alterar(CafeDTO cafe);

    void cancelar(int id);

    void excluirTodos();

    List<CafeDTO> listarPedidos();
}
