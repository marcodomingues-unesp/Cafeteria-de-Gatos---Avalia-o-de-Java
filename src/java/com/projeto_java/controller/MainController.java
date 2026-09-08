package com.projeto_java.controller;

import com.projeto_java.service.ICafeService;
import com.projeto_java.util.AlertUtil;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import model.dto.CafeDTO;

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

    private final ICafeService cafeService;

    public MainController(ICafeService cafeService) {

        if (cafeService == null) {
            throw new IllegalArgumentException(
                    "O CafeService não pode ser null."
            );
        }

        this.cafeService = cafeService;
    }

    @FXML
    public void initialize() {

        configurarCombos();
        configurarTabela();
        configurarSelecao();
        carregarPedidos();
    }

    // Configura as opções dos campos de seleção
    private void configurarCombos() {

        comboTamanho.getItems().setAll(
                "Pequeno",
                "Médio",
                "Grande"
        );

        comboTorra.getItems().setAll(
                "Clara",
                "Média",
                "Escura"
        );
    }

    // Configura as colunas da tabela
    private void configurarTabela() {

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
    }

    // Preenche o formulário quando um pedido é selecionado
    private void configurarSelecao() {

        tablePedidos.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, antigoCafe, novoCafe) -> {

                    if (novoCafe != null) {
                        preencherFormulario(novoCafe);
                    }
                });
    }

    // Preenche os campos com os dados do pedido selecionado
    private void preencherFormulario(CafeDTO dadosCafe) {

        txtNome.setText(
                dadosCafe.getNomeProduto()
        );

        txtPreco.setText(
                String.valueOf(dadosCafe.getPreco())
        );

        comboTamanho.setValue(
                dadosCafe.getTamanhoProduto()
        );

        comboTorra.setValue(
                dadosCafe.getTipoTorra()
        );

        lblMensagem.setText("");
    }

    // Cadastra um novo pedido
    @FXML
    private void comprarCafe() {

        try {

            CafeDTO dadosCafe = criarCafe();

            cafeService.cadastrar(dadosCafe);

            finalizarOperacao(
                    "Pedido cadastrado com sucesso."
            );

        } catch (IllegalArgumentException e) {

            lblMensagem.setText(
                    e.getMessage()
            );

        } catch (RuntimeException e) {

            AlertUtil.showError(
                    obterMensagemErro(e)
            );
        }
    }

    // Altera o pedido selecionado
    @FXML
    private void alterarPedido() {

        CafeDTO dadosCafe = selecionarPedido();

        if (dadosCafe == null) {
            return;
        }

        try {

            preencherCafe(dadosCafe);

            if (!AlertUtil.showConfirmation(
                    "Deseja realmente alterar este pedido?"
            )) {
                return;
            }

            cafeService.alterar(dadosCafe);

            finalizarOperacao(
                    "Pedido alterado com sucesso."
            );

        } catch (IllegalArgumentException e) {

            lblMensagem.setText(
                    e.getMessage()
            );

        } catch (RuntimeException e) {

            AlertUtil.showError(
                    obterMensagemErro(e)
            );
        }
    }

    // Cancela somente o pedido selecionado
    @FXML
    private void cancelarPedido() {

        CafeDTO dadosCafe = selecionarPedido();

        if (dadosCafe == null) {
            return;
        }

        if (!AlertUtil.showConfirmation(
                "Deseja realmente cancelar este pedido?"
        )) {
            return;
        }

        try {

            int idPedido = dadosCafe.getId();

            // Exclui somente o pedido selecionado no banco
            cafeService.cancelar(idPedido);

            // Atualiza a tabela com os dados atuais do banco
            carregarPedidos();

            // Limpa os campos do formulário
            limparCampos();

            // Remove a seleção da tabela
            tablePedidos.getSelectionModel()
                    .clearSelection();

            AlertUtil.showInformation(
                    "Pedido cancelado com sucesso."
            );

        } catch (RuntimeException e) {

            AlertUtil.showError(
                    obterMensagemErro(e)
            );
        }
    }

    // Exclui todos os pedidos
    @FXML
    private void excluirTudo() {

        if (tablePedidos.getItems().isEmpty()) {

            AlertUtil.showWarning(
                    "Não existem pedidos para excluir."
            );

            return;
        }

        if (!AlertUtil.showConfirmation(
                "Deseja realmente excluir todos os pedidos?"
        )) {
            return;
        }

        try {

            cafeService.excluirTodos();

            finalizarOperacao(
                    "Todos os pedidos foram removidos."
            );

        } catch (RuntimeException e) {

            AlertUtil.showError(
                    obterMensagemErro(e)
            );
        }
    }

    // Limpa o formulário
    @FXML
    private void limparFormulario() {

        limparCampos();

        tablePedidos.getSelectionModel()
                .clearSelection();
    }

    // Cria um novo objeto CafeDTO
    private CafeDTO criarCafe() {

        CafeDTO dadosCafe = new CafeDTO();

        preencherCafe(dadosCafe);

        return dadosCafe;
    }

    // Preenche o DTO com os dados do formulário
    private void preencherCafe(CafeDTO dadosCafe) {

        dadosCafe.setNomeProduto(
                obterNome()
        );

        dadosCafe.setTamanhoProduto(
                comboTamanho.getValue()
        );

        dadosCafe.setTipoTorra(
                comboTorra.getValue()
        );

        dadosCafe.setPreco(
                obterPreco()
        );
    }

    // Obtém o nome informado
    private String obterNome() {

        if (txtNome.getText() == null) {
            return "";
        }

        return txtNome.getText().trim();
    }

    // Converte o preço informado para número
    private double obterPreco() {

        String textoPreco = txtPreco.getText();

        if (textoPreco == null ||
                textoPreco.trim().isEmpty()) {

            return 0;
        }

        try {

            return Double.parseDouble(
                    textoPreco
                            .trim()
                            .replace(",", ".")
            );

        } catch (NumberFormatException e) {

            throw new IllegalArgumentException(
                    "Digite um preço válido."
            );
        }
    }

    // Retorna o pedido atualmente selecionado
    private CafeDTO selecionarPedido() {

        CafeDTO dadosCafe = tablePedidos
                .getSelectionModel()
                .getSelectedItem();

        if (dadosCafe == null) {

            AlertUtil.showWarning(
                    "Selecione um pedido."
            );
        }

        return dadosCafe;
    }

    // Busca os pedidos no banco e atualiza a tabela
    private void carregarPedidos() {

        try {

            tablePedidos.getItems().setAll(
                    cafeService.listarPedidos()
            );

        } catch (RuntimeException e) {

            AlertUtil.showError(
                    obterMensagemErro(e)
            );
        }
    }

    // Finaliza uma operação e atualiza a interface
    private void finalizarOperacao(String mensagem) {

        carregarPedidos();

        limparCampos();

        tablePedidos.getSelectionModel()
                .clearSelection();

        AlertUtil.showInformation(
                mensagem
        );
    }

    // Limpa todos os campos do formulário
    private void limparCampos() {

        txtNome.clear();

        txtPreco.clear();

        comboTamanho.getSelectionModel()
                .clearSelection();

        comboTorra.getSelectionModel()
                .clearSelection();

        lblMensagem.setText("");
    }

    // Retorna uma mensagem adequada para erros
    private String obterMensagemErro(RuntimeException e) {

        if (e.getMessage() == null ||
                e.getMessage().trim().isEmpty()) {

            return "Ocorreu um erro ao realizar a operação.";
        }

        return e.getMessage();
    }
}