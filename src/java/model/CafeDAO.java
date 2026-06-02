package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.projeto_java.Conexao;

public class CafeDAO {

    // =========================
    // LISTAR
    // =========================
    public List<CafeDTO> selecionarCafe() {

        List<CafeDTO> lista = new ArrayList<>();

        String sql = "SELECT * FROM tabela_cafe";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {

                CafeDTO cafe = new CafeDTO();

                cafe.setId(resultado.getInt("id"));
                cafe.setNomeProduto(resultado.getString("nome_produto"));
                cafe.setTamanhoProduto(resultado.getString("tamanho_produto"));
                cafe.setTipoTorra(resultado.getString("tipo_torra"));
                cafe.setPreco(resultado.getDouble("preco"));

                lista.add(cafe);
            }

        } catch (SQLException e) {
            System.out.println("Erro SELECT: " + e.getMessage());
        }

        return lista;
    }

    // =========================
    // INSERIR
    // =========================
    public void comprarCafe(CafeDTO cafe) {

        String sql = "INSERT INTO tabela_cafe (nome_produto, tamanho_produto, tipo_torra, preco) VALUES (?, ?, ?, ?)";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, cafe.getNomeProduto());
            comando.setString(2, cafe.getTamanhoProduto());
            comando.setString(3, cafe.getTipoTorra());
            comando.setDouble(4, cafe.getPreco());

            comando.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro INSERT: " + e.getMessage());
        }
    }

    // =========================
    // UPDATE
    // =========================
    public void alterarPedido(CafeDTO cafe) {

        String sql = "UPDATE tabela_cafe SET nome_produto=?, tamanho_produto=?, tipo_torra=?, preco=? WHERE id=?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, cafe.getNomeProduto());
            comando.setString(2, cafe.getTamanhoProduto());
            comando.setString(3, cafe.getTipoTorra());
            comando.setDouble(4, cafe.getPreco());
            comando.setInt(5, cafe.getId());

            comando.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro UPDATE: " + e.getMessage());
        }
    }

    // =========================
    // DELETE (1 ITEM)
    // =========================
    public void cancelarPedido(int id) {

        String sql = "DELETE FROM tabela_cafe WHERE id=?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, id);

            comando.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro DELETE: " + e.getMessage());
        }
    }

    // =========================
    // 🔥 DELETE TUDO (RESET COMANDAS)
    // =========================
    public void excluirTodosPedidos() {

        String sql1 = "DELETE FROM tabela_cafe";
        String sql2 = "ALTER TABLE tabela_cafe AUTO_INCREMENT = 1";

        try (Connection conexao = Conexao.conectar();
             Statement stmt = conexao.createStatement()) {

            stmt.executeUpdate(sql1);
            stmt.executeUpdate(sql2);

        } catch (SQLException e) {
            System.out.println("Erro DELETE ALL: " + e.getMessage());
        }
    }
}