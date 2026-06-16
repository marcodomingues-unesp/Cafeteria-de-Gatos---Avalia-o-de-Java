package com.projeto_java;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.CafeDAO;
import model.CafeDTO;

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

    private final CafeDAO obj = new CafeDAO();

    @FXML
    public void initialize() {

        comboTamanho.getItems().addAll(
                "Pequeno",
                "Médio",
                "Grande"
        );

        comboTorra.getItems().addAll(
                "Clara",
                "Média",
                "Escura"
        );

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

        obj.comprarCafe(cafe);

        carregarTabela();

        limparCampos();

        lblMensagem.setText(
                "Pedido cadastrado com sucesso."
        );
    }

    @FXML
    private void alterarPedido() {

        CafeDTO cafe =
                tablePedidos.getSelectionModel()
                        .getSelectedItem();

        if (cafe == null) {

            lblMensagem.setText(
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

        obj.alterarPedido(cafe);

        carregarTabela();

        limparCampos();

        tablePedidos.getSelectionModel()
                .clearSelection();

        lblMensagem.setText(
                "Pedido alterado com sucesso."
        );
    }

    @FXML
    private void cancelarPedido() {

        CafeDTO cafe =
                tablePedidos.getSelectionModel()
                        .getSelectedItem();

        if (cafe == null) {

            lblMensagem.setText(
                    "Selecione um pedido para cancelar."
            );

            return;
        }

        obj.cancelarPedido(cafe.getId());

        carregarTabela();

        limparCampos();

        tablePedidos.getSelectionModel()
                .clearSelection();

        lblMensagem.setText(
                "Pedido cancelado com sucesso."
        );
    }

    @FXML
    private void excluirTudo() {

        obj.excluirTodosPedidos();

        carregarTabela();

        limparCampos();

        tablePedidos.getSelectionModel()
                .clearSelection();

        lblMensagem.setText(
                "Todos os pedidos foram removidos."
        );
    }

    @FXML
    private void limparFormulario() {

        limparCampos();

        tablePedidos.getSelectionModel()
                .clearSelection();

        lblMensagem.setText(
                "Campos limpos."
        );
    }

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
    }

    private void carregarTabela() {

        List<CafeDTO> lista =
                obj.selecionarCafe();

        tablePedidos.getItems().clear();

        tablePedidos.getItems().addAll(lista);
    }

    private boolean validar() {

        txtNome.setStyle("");
        txtPreco.setStyle("");
        comboTamanho.setStyle("");
        comboTorra.setStyle("");

        boolean valido = true;

        if (txtNome.getText().trim().isEmpty()) {

            txtNome.setStyle(
                    "-fx-border-color: red;"
            );

            valido = false;
        }

        if (txtPreco.getText().trim().isEmpty()) {

            txtPreco.setStyle(
                    "-fx-border-color: red;"
            );

            valido = false;
        }

        if (comboTamanho.getValue() == null) {

            comboTamanho.setStyle(
                    "-fx-border-color: red;"
            );

            valido = false;
        }

        if (comboTorra.getValue() == null) {

            comboTorra.setStyle(
                    "-fx-border-color: red;"
            );

            valido = false;
        }

        if (!valido) {

            lblMensagem.setText(
                    "Preencha todos os campos obrigatórios."
            );

            return false;
        }

        try {

            Double.parseDouble(
                    txtPreco.getText()
            );

        } catch (Exception e) {

            txtPreco.setStyle(
                    "-fx-border-color: red;"
            );

            lblMensagem.setText(
                    "Preço inválido."
            );

            return false;
        }

        lblMensagem.setText("");

        return true;
    }
}