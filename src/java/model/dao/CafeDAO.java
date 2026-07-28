package model.dao;

import com.projeto_java.Conexao;
import model.dto.CafeDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.projeto_java.util.AlertUtil.showError;

public class CafeDAO {

    private static final Logger logger =
            Logger.getLogger(CafeDAO.class.getName());

    // Lista todos os pedidos cadastrados
    public List<CafeDTO> selecionarCafe() {

        List<CafeDTO> lista = new ArrayList<>();

        String sql = """
                SELECT *
                FROM tabela_cafe
                ORDER BY id
                """;

        try (
                Connection conexao = Conexao.conectar();
                PreparedStatement comando = conexao.prepareStatement(sql);
                ResultSet resultado = comando.executeQuery()
        ) {

            while (resultado.next()) {

                CafeDTO cafe = new CafeDTO();

                cafe.setId(resultado.getInt("id"));

                cafe.setNomeProduto(
                        resultado.getString("nome_produto")
                );

                cafe.setTamanhoProduto(
                        resultado.getString("tamanho_produto")
                );

                cafe.setTipoTorra(
                        resultado.getString("tipo_torra")
                );

                cafe.setPreco(
                        resultado.getDouble("preco")
                );

                lista.add(cafe);
            }

        } catch (SQLException e) {

            logger.log(
                    Level.SEVERE,
                    "Erro ao selecionar os pedidos.",
                    e
            );

            showError(
                    "Erro ao carregar os pedidos."
            );
        }

        return lista;
    }

    // Cadastra um novo pedido
    public void comprarCafe(CafeDTO cafe) {

        String sql = """
                INSERT INTO tabela_cafe
                (nome_produto, tamanho_produto, tipo_torra, preco)
                VALUES (?, ?, ?, ?)
                """;

        try (
                Connection conexao = Conexao.conectar();
                PreparedStatement comando =
                        conexao.prepareStatement(sql)
        ) {

            comando.setString(
                    1,
                    cafe.getNomeProduto()
            );

            comando.setString(
                    2,
                    cafe.getTamanhoProduto()
            );

            comando.setString(
                    3,
                    cafe.getTipoTorra()
            );

            comando.setDouble(
                    4,
                    cafe.getPreco()
            );

            comando.executeUpdate();

        } catch (SQLException e) {

            logger.log(
                    Level.SEVERE,
                    "Erro ao cadastrar o pedido.",
                    e
            );

            showError(
                    "Erro ao cadastrar o pedido."
            );
        }
    }

    // Atualiza um pedido existente
    public void alterarPedido(CafeDTO cafe) {

        String sql = """
                UPDATE tabela_cafe
                SET nome_produto = ?,
                    tamanho_produto = ?,
                    tipo_torra = ?,
                    preco = ?
                WHERE id = ?
                """;

        try (
                Connection conexao = Conexao.conectar();
                PreparedStatement comando =
                        conexao.prepareStatement(sql)
        ) {

            comando.setString(
                    1,
                    cafe.getNomeProduto()
            );

            comando.setString(
                    2,
                    cafe.getTamanhoProduto()
            );

            comando.setString(
                    3,
                    cafe.getTipoTorra()
            );

            comando.setDouble(
                    4,
                    cafe.getPreco()
            );

            comando.setInt(
                    5,
                    cafe.getId()
            );

            comando.executeUpdate();

        } catch (SQLException e) {

            logger.log(
                    Level.SEVERE,
                    "Erro ao alterar o pedido.",
                    e
            );

            showError(
                    "Erro ao alterar o pedido."
            );
        }
    }

    // Exclui um pedido pelo ID
    public void cancelarPedido(int id) {

        String sql =
                "DELETE FROM tabela_cafe WHERE id = ?";

        try (
                Connection conexao = Conexao.conectar();
                PreparedStatement comando =
                        conexao.prepareStatement(sql)
        ) {

            comando.setInt(1, id);

            comando.executeUpdate();

        } catch (SQLException e) {

            logger.log(
                    Level.SEVERE,
                    "Erro ao cancelar o pedido.",
                    e
            );

            showError(
                    "Erro ao cancelar o pedido."
            );
        }
    }

    // Exclui todos os pedidos
    public void excluirTodosPedidos() {

        String sql =
                "TRUNCATE TABLE tabela_cafe RESTART IDENTITY";

        try (
                Connection conexao = Conexao.conectar();
                Statement comando =
                        conexao.createStatement()
        ) {

            comando.executeUpdate(sql);

        } catch (SQLException e) {

            logger.log(
                    Level.SEVERE,
                    "Erro ao excluir todos os pedidos.",
                    e
            );

            showError(
                    "Erro ao excluir todos os pedidos."
            );
        }
    }
}