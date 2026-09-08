package com.projeto_java.validator;

import model.dto.CafeDTO;

public interface ICafeValidator {
    boolean validar(CafeDTO cafe);
}