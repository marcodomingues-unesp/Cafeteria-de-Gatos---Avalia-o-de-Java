package model.dao;

import com.projeto_java.Conexao;
import model.dto.CafeDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CafeDAO {

    private static final Logger logger =
            Logger.getLogger(CafeDAO.class.getName());

    // Busca todos os pedidos cadastrados
    public List<CafeDTO> selecionarCafe() {

        List<CafeDTO> lista = new ArrayList<>();

        String sql = """
                SELECT id, nome_produto, tamanho_produto,
                       tipo_torra, preco
                FROM tabela_cafe
                ORDER BY id
                """;

        try (
                Connection conexao = Conexao.conectar();
                PreparedStatement comando =
                        conexao.prepareStatement(sql);
                ResultSet resultado =
                        comando.executeQuery()
        ) {

            // Converte os dados do banco para objetos CafeDTO
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
            tratarErro(
                    "Erro ao selecionar os pedidos.",
                    e
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

            preencherDados(comando, cafe);
            comando.executeUpdate();

        } catch (SQLException e) {
            tratarErro(
                    "Erro ao cadastrar o pedido.",
                    e
            );
        }
    }

    // Altera um pedido existente
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

            preencherDados(comando, cafe);
            comando.setInt(5, cafe.getId());

            comando.executeUpdate();

        } catch (SQLException e) {
            tratarErro(
                    "Erro ao alterar o pedido.",
                    e
            );
        }
    }

    // Cancela um pedido pelo ID
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
            tratarErro(
                    "Erro ao cancelar o pedido.",
                    e
            );
        }
    }

    // Exclui todos os pedidos da tabela
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
            tratarErro(
                    "Erro ao excluir todos os pedidos.",
                    e
            );
        }
    }

    // Preenche os dados usados no cadastro e alteração
    private void preencherDados(
            PreparedStatement comando,
            CafeDTO cafe) throws SQLException {

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
    }

    // Registra e informa erros do banco de dados
    private void tratarErro(
            String mensagem,
            SQLException e) {

        logger.log(
                Level.SEVERE,
                mensagem,
                e
        );

        throw new RuntimeException(
                mensagem,
                e
        );
    }
}