package com.projeto_java;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.CafeDAO;
import model.CafeDTO;

import java.util.List;

public class MainController {

    @FXML private TextField txtNome;
    @FXML private TextField txtPreco;
    @FXML private ComboBox<String> comboTamanho;
    @FXML private ComboBox<String> comboTorra;

    @FXML private TableView<CafeDTO> tablePedidos;

    @FXML private TableColumn<CafeDTO, Integer> colId;
    @FXML private TableColumn<CafeDTO, String> colNome;
    @FXML private TableColumn<CafeDTO, String> colTamanho;
    @FXML private TableColumn<CafeDTO, String> colTorra;
    @FXML private TableColumn<CafeDTO, Double> colPreco;

    private final CafeDAO dao = new CafeDAO();

    @FXML
    public void initialize() {

        comboTamanho.getItems().addAll("Pequeno", "Médio", "Grande");
        comboTorra.getItems().addAll("Clara", "Média", "Escura");

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
        colTamanho.setCellValueFactory(new PropertyValueFactory<>("tamanhoProduto"));
        colTorra.setCellValueFactory(new PropertyValueFactory<>("tipoTorra"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        carregarTabela();
    }

    // =========================
    // CREATE
    // =========================
    @FXML
    private void comprarCafe() {

        if (!validar()) return;

        CafeDTO cafe = new CafeDTO();
        cafe.setNomeProduto(txtNome.getText());
        cafe.setTamanhoProduto(comboTamanho.getValue());
        cafe.setTipoTorra(comboTorra.getValue());
        cafe.setPreco(Double.parseDouble(txtPreco.getText()));

        dao.comprarCafe(cafe);

        carregarTabela();
        limparCampos();
    }

    // =========================
    // CANCELAR (LIMPA SELEÇÃO)
    // =========================
    @FXML
    private void cancelarPedido() {
        limparCampos();
        tablePedidos.getSelectionModel().clearSelection();
    }

    // =========================
    // UPDATE
    // =========================
    @FXML
    private void alterarPedido() {

        CafeDTO cafe = tablePedidos.getSelectionModel().getSelectedItem();

        if (cafe == null || !validar()) return;

        cafe.setNomeProduto(txtNome.getText());
        cafe.setTamanhoProduto(comboTamanho.getValue());
        cafe.setTipoTorra(comboTorra.getValue());
        cafe.setPreco(Double.parseDouble(txtPreco.getText()));

        dao.alterarPedido(cafe);

        carregarTabela();
        limparCampos();
    }

    // =========================
    // DELETE 1 ITEM
    // =========================
    @FXML
    private void excluirPedidos() {

        CafeDTO cafe = tablePedidos.getSelectionModel().getSelectedItem();

        if (cafe == null) return;

        dao.cancelarPedido(cafe.getId());

        carregarTabela();
        limparCampos();
    }

    // =========================
    // 🔥 EXCLUIR TUDO (RESET COMANDAS)
    // =========================
    @FXML
    private void excluirTudo() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText(null);
        alert.setContentText("Tem certeza que deseja EXCLUIR TODOS os pedidos?");

        if (alert.showAndWait().get() == ButtonType.OK) {

            dao.excluirTodosPedidos();

            carregarTabela();
            limparCampos();
        }
    }

    // =========================
    // LIMPAR CAMPOS
    // =========================
    private void limparCampos() {
        txtNome.clear();
        txtPreco.clear();
        comboTamanho.getSelectionModel().clearSelection();
        comboTorra.getSelectionModel().clearSelection();
    }

    // =========================
    // CARREGAR TABELA
    // =========================
    private void carregarTabela() {

        List<CafeDTO> lista = dao.selecionarCafe();

        tablePedidos.getItems().clear();
        tablePedidos.getItems().addAll(lista);
    }

    // =========================
    // VALIDAÇÃO
    // =========================
    private boolean validar() {

        if (txtNome.getText().isEmpty()
                || txtPreco.getText().isEmpty()
                || comboTamanho.getValue() == null
                || comboTorra.getValue() == null) {
            return false;
        }

        try {
            Double.parseDouble(txtPreco.getText());
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}