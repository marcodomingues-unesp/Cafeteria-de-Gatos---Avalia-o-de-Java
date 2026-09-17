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

    // Recebe o serviço por injeção de dependência
    public MainController(ICafeService cafeService) {

        if (cafeService == null) {
            throw new IllegalArgumentException(
                    "O CafeService não pode ser null."
            );
        }

        this.cafeService = cafeService;
    }

    // Inicializa os componentes da tela
    @FXML
    public void initialize() {

        configurarCombos();
        configurarTabela();
        configurarSelecao();
        carregarPedidos();
    }

    // Define as opções dos campos de seleção
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

    // Define os dados exibidos nas colunas
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

    // Preenche o formulário ao selecionar um pedido
    private void configurarSelecao() {

        tablePedidos.getSelectionModel()
                .selectedItemProperty()
                .addListener((_, _, novoCafe) -> {

                    if (novoCafe != null) {
                        preencherFormulario(novoCafe);
                    }
                });
    }

    // Mostra os dados do pedido no formulário
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

            if (AlertUtil.showConfirmation(
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

    // Cancela o pedido selecionado
    @FXML
    private void cancelarPedido() {

        CafeDTO dadosCafe = selecionarPedido();

        if (dadosCafe == null) {
            return;
        }

        if (AlertUtil.showConfirmation(
                "Deseja realmente cancelar este pedido?"
        )) {
            return;
        }

        try {

            int idPedido = dadosCafe.getId();

            // Remove apenas o pedido selecionado
            cafeService.cancelar(idPedido);

            // Atualiza os dados da tabela
            carregarPedidos();

            // Limpa o formulário e a seleção
            limparCampos();

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

        if (AlertUtil.showConfirmation(
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

    // Limpa o formulário e a seleção
    @FXML
    private void limparFormulario() {

        limparCampos();

        tablePedidos.getSelectionModel()
                .clearSelection();
    }

    // Cria o DTO com os dados do formulário
    private CafeDTO criarCafe() {

        CafeDTO dadosCafe = new CafeDTO();

        preencherCafe(dadosCafe);

        return dadosCafe;
    }

    // Transfere os dados do formulário para o DTO
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

    // Converte o preço para número
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

    // Retorna o pedido selecionado
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

    // Carrega os pedidos do banco
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

    // Atualiza a tela após uma operação
    private void finalizarOperacao(String mensagem) {

        carregarPedidos();

        limparCampos();

        tablePedidos.getSelectionModel()
                .clearSelection();

        AlertUtil.showInformation(
                mensagem
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

        lblMensagem.setText("");
    }

    // Garante uma mensagem de erro válida
    private String obterMensagemErro(RuntimeException e) {

        if (e.getMessage() == null ||
                e.getMessage().trim().isEmpty()) {

            return "Ocorreu um erro ao realizar a operação.";
        }

        return e.getMessage();
    }
}