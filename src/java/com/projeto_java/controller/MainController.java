package com.projeto_java.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dao.CafeDAO;
import model.dto.CafeDTO;

import com.projeto_java.util.AlertUtil;
import com.projeto_java.validator.CafeValidator;

import java.util.List;

public class MainController {

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtPreco;

    @FXML
    private ComboBox<String> comboTamanho;

    @FXML
    private ComboBox<String> comboTorra;

    @FXML
    private Label lblMensagem;

    @FXML
    private TableView<CafeDTO> tablePedidos;

    @FXML
    private TableColumn<CafeDTO, Integer> colId;

    @FXML
    private TableColumn<CafeDTO, String> colNome;

    @FXML
    private TableColumn<CafeDTO, String> colTamanho;

    @FXML
    private TableColumn<CafeDTO, String> colTorra;

    @FXML
    private TableColumn<CafeDTO, Double> colPreco;


    @FXML
    public void initialize() {

        // Opções do tamanho
        comboTamanho.getItems().addAll("Pequeno", "Médio", "Grande");

        // Opções do tipo de torra
        comboTorra.getItems().addAll("Clara", "Média", "Escura"
        );

        // Configuração das colunas da tabela
        colId.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        colNome.setCellValueFactory(
                new PropertyValueFactory<>("nomeProduto")
        );

        colTamanho.setCellValueFactory(
                new PropertyValueFactory<>("tamanhoProduto")
        );

        colTorra.setCellValueFactory(
                new PropertyValueFactory<>("tipoTorra")
        );

        colPreco.setCellValueFactory(
                new PropertyValueFactory<>("preco")
        );

        bloquearCampoPreco();

        carregarTabela();

        carregarDadosSelecionados();
    }


    // Permite somente números e ponto no campo de preço
    private void bloquearCampoPreco() {

        txtPreco.textProperty().addListener(
                (obs, valorAntigo, valorNovo) -> {

                    if (!valorNovo.matches("\\d*(\\.\\d*)?")) {

                        txtPreco.setText(
                                valorNovo.replaceAll("[^\\d.]", "")
                        );
                    }
                }
        );
    }


    // Carrega os dados do pedido selecionado na tabela
    private void carregarDadosSelecionados() {

        tablePedidos.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, antigo, cafe) -> {

                    if (cafe != null) {

                        txtNome.setText(
                                cafe.getNomeProduto()
                        );

                        txtPreco.setText(
                                String.valueOf(cafe.getPreco())
                        );

                        comboTamanho.setValue(
                                cafe.getTamanhoProduto()
                        );

                        comboTorra.setValue(
                                cafe.getTipoTorra()
                        );
                    }
                });
    }


    // Cadastrar um novo pedido
    @FXML
    private void comprarCafe() {

        if (!validar()) {
            return;
        }

        CafeDTO cafe = new CafeDTO();

        cafe.setNomeProduto(
                txtNome.getText().trim()
        );

        cafe.setTamanhoProduto(
                comboTamanho.getValue()
        );

        cafe.setTipoTorra(
                comboTorra.getValue()
        );

        cafe.setPreco(
                Double.parseDouble(txtPreco.getText())
        );

        CafeDAO dao = new CafeDAO();

        dao.comprarCafe(cafe);

        carregarTabela();

        limparCampos();

        AlertUtil.showInformation(
                "Pedido cadastrado com sucesso."
        );
    }


    // Alterar um pedido existente
    @FXML
    private void alterarPedido() {

        CafeDTO cafe = tablePedidos
                .getSelectionModel()
                .getSelectedItem();

        if (cafe == null) {

            AlertUtil.showWarning(
                    "Selecione um pedido para alterar."
            );

            return;
        }

        if (!validar()) {
            return;
        }

        cafe.setNomeProduto(
                txtNome.getText().trim()
        );

        cafe.setTamanhoProduto(
                comboTamanho.getValue()
        );

        cafe.setTipoTorra(
                comboTorra.getValue()
        );

        cafe.setPreco(
                Double.parseDouble(txtPreco.getText())
        );

        CafeDAO dao = new CafeDAO();

        dao.alterarPedido(cafe);

        carregarTabela();

        limparCampos();

        tablePedidos.getSelectionModel()
                .clearSelection();

        AlertUtil.showInformation(
                "Pedido alterado com sucesso."
        );
    }


    // Cancelar um pedido
    @FXML
    private void cancelarPedido() {

        CafeDTO cafe = tablePedidos
                .getSelectionModel()
                .getSelectedItem();

        if (cafe == null) {

            AlertUtil.showWarning(
                    "Selecione um pedido para cancelar."
            );

            return;
        }

        if (!AlertUtil.showConfirmation(
                "Deseja realmente cancelar este pedido?"
        )) {

            return;
        }

        CafeDAO dao = new CafeDAO();

        dao.cancelarPedido(cafe.getId());

        carregarTabela();

        limparCampos();

        tablePedidos.getSelectionModel()
                .clearSelection();

        AlertUtil.showInformation(
                "Pedido cancelado com sucesso."
        );
    }


    // Excluir todos os pedidos
    @FXML
    private void excluirTudo() {

        if (!AlertUtil.showConfirmation(
                "Deseja realmente excluir todos os pedidos?"
        )) {

            return;
        }

        CafeDAO dao = new CafeDAO();

        dao.excluirTodosPedidos();

        carregarTabela();

        limparCampos();

        tablePedidos.getSelectionModel()
                .clearSelection();

        AlertUtil.showInformation(
                "Todos os pedidos foram removidos."
        );
    }


    // Limpar formulário
    @FXML
    private void limparFormulario() {

        limparCampos();

        tablePedidos.getSelectionModel()
                .clearSelection();

        AlertUtil.showInformation(
                "Campos limpos."
        );
    }


    // Limpa os campos do formulário
    private void limparCampos() {

        txtNome.clear();

        txtPreco.clear();

        comboTamanho.getSelectionModel()
                .clearSelection();

        comboTorra.getSelectionModel()
                .clearSelection();

        txtNome.setStyle("");

        txtPreco.setStyle("");

        comboTamanho.setStyle("");

        comboTorra.setStyle("");

        lblMensagem.setText("");
    }


    // Carrega os pedidos na tabela
    private void carregarTabela() {

        CafeDAO dao = new CafeDAO();

        List<CafeDTO> lista = dao.selecionarCafe();

        tablePedidos.getItems().clear();

        tablePedidos.getItems().addAll(lista);
    }


    // Validação do formulário
    private boolean validar() {

        // Limpa as bordas vermelhas anteriores
        txtNome.setStyle("");

        txtPreco.setStyle("");

        comboTamanho.setStyle("");

        comboTorra.setStyle("");


        String nomeProduto = txtNome.getText().trim();

        String tamanhoProduto = comboTamanho.getValue();

        String tipoTorra = comboTorra.getValue();

        String precoTexto = txtPreco.getText().trim();


        boolean camposVazios = false;


        // Verifica nome
        if (nomeProduto.isEmpty()) {

            txtNome.setStyle(
                    "-fx-border-color: red;"
            );

            camposVazios = true;
        }


        // Verifica preço
        if (precoTexto.isEmpty()) {

            txtPreco.setStyle(
                    "-fx-border-color: red;"
            );

            camposVazios = true;
        }


        // Verifica tamanho
        if (tamanhoProduto == null) {

            comboTamanho.setStyle(
                    "-fx-border-color: red;"
            );

            camposVazios = true;
        }


        // Verifica tipo de torra
        if (tipoTorra == null) {

            comboTorra.setStyle(
                    "-fx-border-color: red;"
            );

            camposVazios = true;
        }


        // Se algum campo estiver vazio
        if (camposVazios) {

            AlertUtil.showWarning(
                    "Preencha todos os campos obrigatórios."
            );

            return false;
        }


        // Converte o preço para double
        double preco;

        try {

            preco = Double.parseDouble(precoTexto);

        } catch (NumberFormatException e) {

            txtPreco.setStyle(
                    "-fx-border-color: red;"
            );

            AlertUtil.showWarning(
                    "Preço inválido."
            );

            return false;
        }


        // Chama o CafeValidator
        if (!CafeValidator.validarCafe(
                nomeProduto,
                tamanhoProduto,
                tipoTorra,
                preco
        )) {

            txtNome.setStyle(
                    "-fx-border-color: red;"
            );

            return false;
        }


        lblMensagem.setText("");

        return true;
    }
}