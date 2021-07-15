package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class PerguntaBD {

    public static boolean remover(Pergunta pergunta){
        final String query = "DELETE FROM questao WHERE codigo = ?";
        final String query2 = "DELETE FROM discursiva WHERE codigo_questao = ?";
        final String query3 = "DELETE FROM alternativa WHERE codigo_questao = ?";

        Connection conexao = null;
        PreparedStatement statement = null; // usado quando o comando tem parametros

        try {
            conexao  = ConexaoFactory.getConexao(); // lança SQLException 

            statement  = conexao.prepareStatement(query2); // prepara o comando a partir da conexao
            statement.setInt(1, pergunta.getId()); // substitui o primeiro parametro
            statement.execute(); // executa o delete na tabela do bd
            statement.close(); // fecha o statement da segunda query

            statement  = conexao.prepareStatement(query3);
            statement.setInt(1, pergunta.getId());
            statement.execute();
            statement.close(); // fecha o statement da terceira query

            statement = conexao.prepareStatement(query);
            statement.setInt(1, pergunta.getId());
            statement.execute();

        } catch (SQLException e) {

            e.printStackTrace();
            return false;

        } finally {
            try {
                
                if (statement != null) {
                    statement.close(); // lança SQLException
                }

            } catch (SQLException e) {

                e.printStackTrace();
                return false;

            }
        }

        return true;
    }
}// fim da classe PerguntaBD