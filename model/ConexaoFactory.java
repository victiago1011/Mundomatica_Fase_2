package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoFactory {
    //jdbc:postgresql://endereco_servidor:porta/nome_do_banco
    private static final String URL = "jdbc:postgresql://localhost:5432/MundoMatica";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "020814";

    private static Connection conexao;

    private ConexaoFactory() {} // constrututor privado faz com que não seja possivel criar um obj

    public static Connection getConexao() throws SQLException {
        if (conexao == null) {
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
        }

        return conexao;
    }
} // fim da classe ConexaoFactory