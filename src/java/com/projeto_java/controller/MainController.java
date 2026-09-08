package com.projeto_java.controller;

import com.projeto_java.service.ICafeService;
import com.projeto_java.util.AlertUtil;
import com.projeto_java.validator.ICafeValidator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import model.dto.CafeDTO;

public class MainController {

    public Button btnNovo;
    public Button btnAlterar;
    public Button btnLimpar;
    public Button btnCancelar;
    public Button btnExcluir;

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
    private final ICafeValidator cafeValidator;

    public MainController(
            ICafeService cafeService,
            ICafeValidator cafeValidator
    ) {

        if (cafeService == null) {
            throw new IllegalArgumentException(
                    "O CafeService não pode ser null."
            );
        }

        if (cafeValidator == null) {
            throw new IllegalArgumentException(
                    "O CafeValidator não pode ser null."
            );
        }

        this.cafeService = cafeService;
        this.cafeValidator = cafeValidator;
    }

    @FXML
    public void initialize() {

        configurarCombos();
        configurarTabela();
        configurarSelecao();
        carregarPedidos();
    }

    // Configura as opções dos combos
    private void configurarCombos() {

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

    // Atualiza o formulário ao selecionar um pedido
    private void configurarSelecao() {

        tablePedidos
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((_, _, novoCafe) -> {

                    if (novoCafe != null) {
                        preencherFormulario(novoCafe);
                    }
                });
    }

    // Preenche o formulário com os dados selecionados
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
    }

    // Cadastra um novo pedido
    @FXML
    private void comprarCafe() {

        try {

            CafeDTO dadosCafe = criarCafe();

            if (!cafeValidator.validar(dadosCafe)) {
                return;
            }

            cafeService.cadastrar(dadosCafe);

            finalizarOperacao(
                    "Pedido cadastrado com sucesso."
            );

        } catch (RuntimeException e) {

            AlertUtil.showWarning(
                    e.getMessage()
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

            if (!cafeValidator.validar(dadosCafe)) {
                return;
            }

            cafeService.alterar(dadosCafe);

            finalizarOperacao(
                    "Pedido alterado com sucesso."
            );

        } catch (RuntimeException e) {

            AlertUtil.showWarning(
                    e.getMessage()
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

        if (!AlertUtil.showConfirmation(
                "Deseja realmente cancelar este pedido?"
        )) {
            return;
        }

        try {

            cafeService.cancelar(
                    dadosCafe.getId()
            );

            finalizarOperacao(
                    "Pedido cancelado com sucesso."
            );

        } catch (RuntimeException e) {

            AlertUtil.showError(
                    e.getMessage()
            );
        }
    }

    // Exclui todos os pedidos
    @FXML
    private void excluirTudo() {

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
                    e.getMessage()
            );
        }
    }

    // Limpa o formulário
    @FXML
    private void limparFormulario() {

        limparCampos();

        tablePedidos
                .getSelectionModel()
                .clearSelection();
    }

    // Cria o DTO do pedido
    private CafeDTO criarCafe() {

        CafeDTO dadosCafe = new CafeDTO();

        preencherCafe(dadosCafe);

        return dadosCafe;
    }

    // Preenche o DTO com os dados do formulário
    private void preencherCafe(CafeDTO dadosCafe) {

        dadosCafe.setNomeProduto(
                txtNome.getText().trim()
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

            return 0;
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

    // Atualiza a tabela com os pedidos
    public void carregarPedidos() {

        try {

            tablePedidos.getItems().setAll(
                    cafeService.listarPedidos()
            );

        } catch (RuntimeException e) {

            AlertUtil.showError(
                    e.getMessage()
            );
        }
    }

    // Finaliza a operação e atualiza a tela
    private void finalizarOperacao(String mensagem) {

        carregarPedidos();

        limparFormulario();

        AlertUtil.showInformation(
                mensagem
        );
    }

    // Limpa os campos do formulário
    private void limparCampos() {

        txtNome.clear();

        txtPreco.clear();

        comboTamanho.setValue(null);

        comboTorra.setValue(null);

        lblMensagem.setText("");
    }
}