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
        p.execute();

        p.close();
        return retornaIdPedido(obj);
    }

    public void incluirItemDoPedido(PedidoItem obj) throws SQLException {
        String sql = "INSERT INTO PEDIDOITEM (IDPEDIDO, IDPEDIDOITEM, QTDE, VALORUNITARIO) "
                + "VALUES (?,?,?,?)";
        PreparedStatement p = conexao.prepareStatement(sql);
        p.setInt(1, obj.getIdPedido());
        p.setInt(2, obj.getProduto().getId());
        p.setInt(3, obj.getQtde());
        p.setDouble(4, obj.getProduto().getValor());

        p.execute();
        p.close();
    }

    private int retornaIdPedido(Pedido obj) throws SQLException {
        String sql = "SELECT COALESCE (MAX(IDPEDIDO),0) AS ULTIMO FROM PEDIDO";
        PreparedStatement p = conexao.prepareStatement(sql);
        ResultSet r = p.executeQuery();
        r.next();
        obj.setIdPedido(r.getInt("ULTIMO"));
        p.close();

        return obj.getIdPedido();
    }
}
