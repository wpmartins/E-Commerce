package br.com.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Mysql {

    private static boolean connect = false;

    public static java.sql.Connection getConexaoMySQL() {
        Connection connection = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");

            String serverName = "localhost";
            String database = "projetoweb";
            String url = "jdbc:mysql://" + serverName + "/" + database;
            String username = "root";
            String password = "root";

            connection = DriverManager.getConnection(url, username, password);

            connect = (connection != null);
        } catch (ClassNotFoundException e) {
            System.out.println("O driver expecificado nao foi encontrado.");
        } catch (SQLException e) {
            System.out.println("Nao foi possivel conectar ao Banco de Dados.");
        }

        return connection;
    }

    public static boolean conectado() {
        return connect;
    }

    public static boolean FecharConexao() {
        try {
            Mysql.getConexaoMySQL().close();
            return true;
        } catch (SQLException e) {
            return false;
        }

    }

    public static java.sql.Connection ReiniciarConexao() {
        FecharConexao();
        return Mysql.getConexaoMySQL();
    }

}
