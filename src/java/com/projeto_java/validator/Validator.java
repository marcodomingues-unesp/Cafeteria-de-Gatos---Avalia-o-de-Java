package com.projeto_java.validator;

public interface Validator<T> {

    boolean validar(T valor);

    String getMensagemErro();

    T getValor();
}