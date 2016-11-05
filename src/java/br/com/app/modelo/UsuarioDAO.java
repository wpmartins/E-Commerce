package br.com.app.modelo;

import br.com.conexao.Mysql;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Wpmartins
 */
public class UsuarioDAO {

    Connection conexao;
    Usuario usuario;

    public UsuarioDAO() {
        conexao = Mysql.getConexaoMySQL();
        usuario = new Usuario();
    }

    public int retornaIdUsuario(String valor) throws SQLException {
        String sql = "SELECT ID FROM USUARIO WHERE USUARIO LIKE ?";
        PreparedStatement p = conexao.prepareStatement(sql);
        p.setString(1, valor);
        
        ResultSet result = p.executeQuery(sql);
        
        result.next();
        
        usuario.setIdUsuario(result.getInt("ID"));
        
        result.close();
        return usuario.getIdUsuario();
    }
}
