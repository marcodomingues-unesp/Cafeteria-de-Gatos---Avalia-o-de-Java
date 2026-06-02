package model;

public class CafeDTO {

    private int id;
    private String nomeProduto;
    private String tamanhoProduto;
    private String tipoTorra;
    private double preco;

    public CafeDTO() {
    }

    public CafeDTO(int id, String nomeProduto, String tamanhoProduto, String tipoTorra, double preco) {
        this.id = id;
        this.nomeProduto = nomeProduto;
        this.tamanhoProduto = tamanhoProduto;
        this.tipoTorra = tipoTorra;
        this.preco = preco;
    }

    // =========================
    // GETTERS E SETTERS
    // =========================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getTamanhoProduto() {
        return tamanhoProduto;
    }

    public void setTamanhoProduto(String tamanhoProduto) {
        this.tamanhoProduto = tamanhoProduto;
    }

    public String getTipoTorra() {
        return tipoTorra;
    }

    public void setTipoTorra(String tipoTorra) {
        this.tipoTorra = tipoTorra;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}