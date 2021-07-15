package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

public class PerguntaDiscursivaBD {

    public static boolean inserir(Pergunta pergunta) {
        final String query = "INSERT INTO questao (pergunta, tipo_questao, recompensa) VALUES (?, ?, ?)"; // comando questao
        final String query2 = "INSERT INTO discursiva (codigo_questao, resposta) VALUES (?, ?)"; // comando2 discursiva

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

            statement = conexao.prepareStatement(query2);
            statement.setInt(1, pergunta.getId());
            statement.setInt(2, pergunta.getResposta());
            statement.execute();

        } catch (SQLException e) {

            e.printStackTrace();
            return false;

        } finally {
            try {

                if (resultSet != null) {
                    resultSet.close();
                }

                if (statement != null) {
                    statement.close(); // lança SQLException fecha o statement da segunda query
                }

            } catch (SQLException e) {

                e.printStackTrace();
                return false;

            }
        }

        return true;
    }

    public static boolean atualizar(Pergunta pergunta){
        final String query = "UPDATE questao SET pergunta = ?, tipo_questao = ?, recompensa = ? WHERE codigo = ?";
        final String query2 = "UPDATE discursiva SET resposta = ? WHERE codigo_questao = ?";

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

            statement = conexao.prepareStatement(query2);
            statement.setInt(1, pergunta.getResposta());
            statement.setInt(2, pergunta.getId());
            statement.execute();
            statement.close(); // fecha o statement da segunda query

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
    
    public static List<Pergunta> listar(){
        List<Pergunta> perguntas = new ArrayList<>();

        final String query = "SELECT q.codigo, q.pergunta, d.resposta, q.recompensa, q.tipo_questao FROM questao as q INNER JOIN discursiva as d ON q.codigo = d.codigo_questao ORDER BY q.codigo";

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
                pergunta.setResposta(resultSet.getInt("resposta"));
                pergunta.setPontuacao(resultSet.getInt("recompensa"));
                pergunta.setTipo(resultSet.getString("tipo_questao"));
                 
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
}// fim da classe PerguntaBD