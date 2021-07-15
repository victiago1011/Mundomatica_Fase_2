package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

public class PerguntaMultiplaBD {

    public static boolean inserir(Pergunta pergunta) {
        final String query = "INSERT INTO questao (pergunta, tipo_questao, recompensa) VALUES (?, ?, ?)"; // comando questao
        final String query3 = "INSERT INTO alternativa (codigo_questao, certo_errado, valor_alternativo) VALUES (?, ?, ?)"; // comando3

        Connection conexao = null;
        PreparedStatement statement = null; // usado quando o comando tem parametros
        ResultSet resultSet = null; // conjunto de resultados que pega o retorno

        try {
            conexao  = ConexaoFactory.getConexao(); // lança SQLException

            statement  = conexao.prepareStatement(query, Statement.RETURN_GENERATED_KEYS); // prepara o comando a partir da conexao e retorna a chave gerada
            statement.setString(1, pergunta.getPergunta()); // substitui o primeiro parametro
            statement.setString(2, pergunta.getTipo()); // substitui o segundo parametro
            statement.setInt(3, pergunta.getPontuacao()); // substitui o terceiro parametro
            statement.execute(); // executa o insert na tabela do bd

            resultSet = statement.getGeneratedKeys(); // pega chave gerada
            while (resultSet.next()) {
                pergunta.setId(resultSet.getInt(1)); // define o id pegando a unica coluna do resultSet
            }

            statement.close(); // fecha o statement da primeira query
            
            statement = conexao.prepareStatement(query3);
            statement.setInt(1, pergunta.getId());
            for (Alternativa alternativa : pergunta.getAlternativas()) {
                statement.setBoolean(2, alternativa.getCertoErrado());
                statement.setInt(3, alternativa.getValorAlternativa());
                statement.addBatch();
            }
            statement.executeBatch();

        } catch (SQLException e) {

            e.printStackTrace();
            return false;

        } finally {
            try {

                if (resultSet != null) {
                    resultSet.close();
                }

                if (statement != null) {
                    statement.close(); // lança SQLException fecha o statement da terceira query
                }

            } catch (SQLException e) {

                e.printStackTrace();
                return false;

            }
        }

        return true;
    }

    public static boolean atualizar(Pergunta pergunta) {
        final String query = "UPDATE questao SET pergunta = ?, tipo_questao = ?, recompensa = ? WHERE codigo = ?";
        final String query3 = "UPDATE alternativa SET valor_alternativo = ? WHERE codigo_questao = ? AND codigo = ?";

        Connection conexao = null;
        PreparedStatement statement = null; // usado quando o comando tem parametros

        try {
            conexao  = ConexaoFactory.getConexao(); // lança SQLException

            statement  = conexao.prepareStatement(query); // prepara o comando a partir da conexao
            statement.setString(1, pergunta.getPergunta()); // substitui o primeiro parametro
            statement.setString(2, pergunta.getTipo()); // substitui o segundo parametro
            statement.setInt(3, pergunta.getPontuacao()); // substitui o terceiro parametro
            statement.setInt(4, pergunta.getId()); // substitui o quarto parametro
            statement.execute(); // executa o update na tabela do bd
            statement.close(); // fecha o statement da primeira query

            statement = conexao.prepareStatement(query3);
            for (Alternativa alternativa : pergunta.getAlternativas()) {
                statement.setInt(1, alternativa.getValorAlternativa());
                statement.setInt(2, pergunta.getId());
                statement.setInt(3, alternativa.getId());
                statement.addBatch();
            }
            statement.executeBatch();

        } catch (SQLException e) {

            e.printStackTrace();
            return false;

        } finally {
            try {

                if (statement != null) {
                    statement.close(); // lança SQLException e fecha o statement da terceira query
                }

            } catch (SQLException e) {

                e.printStackTrace();
                return false;

            }

        }

        return true;
    }

    public static List<Pergunta> listarQuestoes() {
        List<Pergunta> perguntas = new ArrayList<>();

        final String query = "SELECT q.codigo, q.pergunta, a.certo_errado, a.valor_alternativo, q.recompensa, q.tipo_questao FROM questao as q INNER JOIN alternativa as a ON q.codigo = a.codigo_questao WHERE certo_errado = true ORDER BY codigo";

        Connection conexao = null;
        Statement statement = null; // usado quando o comando não tem parametros
        ResultSet resultSet = null; // conjunto de resultados que pega o retorno

        try {
            conexao = ConexaoFactory.getConexao(); // lança SQLException
            statement = conexao.createStatement(); // cria uma instrução
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Pergunta pergunta = new Pergunta();

                pergunta.setId(resultSet.getInt("codigo"));
                pergunta.setPergunta(resultSet.getString("pergunta"));
                pergunta.setPontuacao(resultSet.getInt("recompensa"));
                pergunta.setTipo(resultSet.getString("tipo_questao"));
                pergunta.setResposta(resultSet.getInt("valor_alternativo"));

                perguntas.add(pergunta); // adiciona na lista a pergunta
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                
                if (resultSet != null) {
                    resultSet.close(); // lança SQLException
                }

                if (statement != null) {
                    statement.close(); // lança SQLException
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return perguntas;
    }

    public static List<Alternativa> listarAlternativas(int id_pergunta) {
        List<Alternativa> alternativas = new ArrayList<>();

        final String query ="SELECT * FROM alternativa WHERE codigo_questao = ?";

        Connection conexao = null;
        PreparedStatement statement = null; // usado quando o comando não tem parametros
        ResultSet resultSet = null; // conjunto de resultados que pega o retorno

        try {
            conexao = ConexaoFactory.getConexao(); // lança SQLException
            statement = conexao.prepareStatement(query);
            statement.setInt(1, id_pergunta);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Alternativa alternativa = new Alternativa();

                alternativa.setId(resultSet.getInt("codigo"));
                alternativa.setCertoErrado(resultSet.getBoolean("certo_errado"));
                alternativa.setValorAlternativa(resultSet.getInt("valor_alternativo"));
                 
                alternativas.add(alternativa); // adiciona na lista a alternativa
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                
                if (resultSet != null) {
                    resultSet.close(); // lança SQLException
                }

                if (statement != null) {
                    statement.close(); // lança SQLException
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return alternativas;
    }
}// fim da classe PerguntaMultiplaBD