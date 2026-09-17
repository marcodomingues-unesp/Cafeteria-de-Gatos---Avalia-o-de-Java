package com.projeto_java.validator;

public class PrecoValidator implements Validador<Double> {

    private final Double valor;
    private String mensagemErro;

    public PrecoValidator(Double valor) {
        this.valor = valor;
    }

    @Override
    public boolean validar(Double preco) {

        // Verifica se o preço é maior que zero
        if (preco == null || preco <= 0) {
            mensagemErro = "O preço deve ser maior que zero.";
            return false;
        }

        // Indica que a validação foi aprovada
        mensagemErro = null;
        return true;
    }

    @Override
    public String getMensagemErro() {
        return mensagemErro;
    }

    @Override
    public Double getValor() {
        return valor;
    }
}