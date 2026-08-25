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

    // Campos do formulário
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

    // Tabela de pedidos
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

    // Serviço recebido pelo Main
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

    // Inicializa a tela
    @FXML
    public void initialize() {

        configurarCombos();
        configurarTabela();
        configurarSelecao();
        carregarPedidos();
    }

    // Define as opções dos ComboBox
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

    // Liga as colunas aos atributos do DTO
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

        tablePedidos
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((_, _, newValue) -> {

                    if (newValue != null) {
                        preencherFormulario(newValue);
                    }
                });
    }

    // Mostra os dados do pedido nos campos
    private void preencherFormulario(CafeDTO cafe) {

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

    // Cadastra um novo pedido
    @FXML
    private void comprarCafe() {

        try {

            CafeDTO cafe = criarCafe();

            cafeService.cadastrar(cafe);

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

        CafeDTO cafe = selecionarPedido();

        if (cafe == null) {
            return;
        }

        try {

            preencherCafe(cafe);

            cafeService.alterar(cafe);

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

        CafeDTO cafe = selecionarPedido();

        if (cafe == null) {
            return;
        }

        // Só continua se o usuário confirmar
        if (!AlertUtil.showConfirmation(
                "Deseja realmente cancelar este pedido?"
        )) {
            return;
        }

        try {

            cafeService.cancelar(
                    cafe.getId()
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

        // Só continua se o usuário confirmar
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

    // Limpa o formulário e a seleção
    @FXML
    private void limparFormulario() {

        limparCampos();

        tablePedidos
                .getSelectionModel()
                .clearSelection();
    }

    // Cria um novo CafeDTO
    private CafeDTO criarCafe() {

        CafeDTO cafe = new CafeDTO();

        preencherCafe(cafe);

        return cafe;
    }

    // Transfere os dados do formulário para o DTO
    private void preencherCafe(CafeDTO cafe) {

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
                obterPreco()
        );
    }

    // Converte o preço para double
    private double obterPreco() {

        try {

            return Double.parseDouble(
                    txtPreco.getText()
                            .trim()
                            .replace(",", ".")
            );

        } catch (NumberFormatException e) {

            throw new IllegalArgumentException(
                    "Informe um preço válido."
            );
        }
    }

    // Retorna o pedido selecionado
    private CafeDTO selecionarPedido() {

        CafeDTO cafe = tablePedidos
                .getSelectionModel()
                .getSelectedItem();

        if (cafe == null) {

            AlertUtil.showWarning(
                    "Selecione um pedido."
            );
        }

        return cafe;
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

    // Atualiza a tela após uma operação
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