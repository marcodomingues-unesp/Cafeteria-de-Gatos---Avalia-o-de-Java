package com.projeto_java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Responsável pela conexão com o banco de dados.

public class Conexao {

    private static final String URL = "jdbc:postgresql://localhost:5432/TabelaCafe";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados: " + e.getMessage(), e);
        }
    }
}