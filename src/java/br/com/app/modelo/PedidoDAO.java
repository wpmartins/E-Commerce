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
public class PedidoDAO {

    Connection conexao;

    public PedidoDAO() {
        conexao = Mysql.getConexaoMySQL();
    }

    public int incluirPedido(Pedido obj) throws SQLException {
        boolean insert = false;
        String sql = "INSERT INTO PEDIDO (IDUSUARIO) VALUES (?)";
        PreparedStatement p = conexao.prepareStatement(sql);
        p.setInt(1, obj.getIdUsuario());
        p.execute(sql);
        
        p.close();

        return retornaIdPedido(obj);
    }

    public void incluirItemDoPedido(PedidoItem obj) throws SQLException {
        String sql = "INSERT INTO PEDIDOITEM (IDPEDIDO, IDPEDIDOITEM, QTDE, VALORUNITARIO) "
                + "VALUES (?,?,?,?)";
        PreparedStatement p = conexao.prepareStatement(sql);
        p.setInt(1, obj.getIdPedido());
        p.setInt(4, obj.getProduto().getId());
        p.setInt(1, obj.getQtde());
        p.setDouble(1, obj.getProduto().getValor());

        p.execute(sql);
        p.close();
    }

    private int retornaIdPedido(Pedido obj) throws SQLException {
        String sql = "select coalesce(max(?),0) from ?";
        PreparedStatement p = conexao.prepareStatement(sql);
        p.setString(1, "IDPEDIDO");
        p.setString(2, "PEDIDO");
        ResultSet result = p.executeQuery(sql);

        result.next();
        obj.setIdPedido(result.getInt("IDPEDIDO"));
        p.close();

        return obj.getIdPedido();
    }
}
