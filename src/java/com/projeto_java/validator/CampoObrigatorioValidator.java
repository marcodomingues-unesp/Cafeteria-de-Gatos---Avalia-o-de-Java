package com.projeto_java.validator;

public class CampoObrigatorioValidator implements Validador<String> {

    private final String nomeCampo;
    private final String valor;

    public CampoObrigatorioValidator(String nomeCampo, String valor) {
        this.nomeCampo = nomeCampo;
        this.valor = valor;
    }

    // Verifica se o campo foi preenchido
    @Override
    public boolean validar(String valorAtual) {
        return valorAtual != null
                && !valorAtual.trim().isEmpty();
    }

    // Retorna a mensagem do erro
    @Override
    public String getMensagemErro() {
        return "O campo " + getCampo()
                + " deve ser preenchido.";
    }

    // Retorna o valor que será validado
    @Override
    public String getValor() {
        return valor;
    }

    // Retorna o nome do campo
    public String getCampo() {
        return nomeCampo;
    }
}