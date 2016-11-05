package br.com.app.modelo;

import br.com.conexao.Mysql;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Wpmartins
 */
public class ProdutoDAO {
    Connection conexao;
    public ProdutoDAO() {
        conexao = Mysql.getConexaoMySQL();
    }

    public void incluir(Produto obj) throws SQLException {
        String sql = "INSERT INTO PRODUTO (DESCRICAO,VALOR,INFORMACAO) VALUES (?,?,?)";
        PreparedStatement p = conexao.prepareStatement(sql);
        p.setString(1, obj.getDescricao());
        p.setDouble(2, obj.getValor());
        p.setString(3, obj.getInformacao());

        p.execute();
        p.close();

    }

    public void alterar(Produto obj) throws SQLException {
        String sql = "UPDATE PRODUTO SET DESCRICAO = ?, VALOR = ?, INFORMACAO = ? WHERE ID = ?";
        PreparedStatement p = conexao.prepareStatement(sql);
        p.setString(1, obj.getDescricao());
        p.setDouble(2, obj.getValor());
        p.setString(3, obj.getInformacao());
        p.setInt(4, obj.getId());

        p.execute();
        p.close();
    }

    public void excluir(Produto obj) throws SQLException {
        String sql = "DELETE FROM PRODUTO WHERE ID = ?";
        PreparedStatement p = conexao.prepareStatement(sql);
        p.setInt(1, obj.getId());
        p.execute();
        p.close();
    }

    public List<Produto> listarProdutos() throws SQLException{
        String sql = "SELECT ID, DESCRICAO, VALOR, INFORMACAO FROM PRODUTO";
        PreparedStatement p = conexao.prepareStatement(sql);
        ResultSet result = p.executeQuery();
        
        List<Produto> produtos = new ArrayList<Produto>();
        
        while (result.next()) {
            Produto obj = new Produto();
            obj.setId(result.getInt("ID"));
            obj.setDescricao(result.getString("DESCRICAO"));
            obj.setValor(result.getDouble("VALOR"));
            obj.setInformacao(result.getString("INFORMACAO"));
            produtos.add(obj);
            
        }
        result.close();
        return produtos;
    }
    
    public Produto retornaProdutoId(Produto obj) throws SQLException{
        String sql = "SELECT ID, DESCRICAO, VALOR, INFORMACAO FROM PRODUTO "
                + "WHERE ID = ?";
        PreparedStatement p = conexao.prepareStatement(sql);
        p.setInt(1, obj.getId());
        ResultSet result = p.executeQuery();
        
        result.next();
        obj.setId(result.getInt("ID"));
        obj.setDescricao(result.getString("DESCRICAO"));
        obj.setValor(result.getDouble("VALOR"));
        obj.setInformacao(result.getString("INFORMACAO"));
        result.close();
        
        return obj;
    }

    public Produto retornaProdutoId(int idProduto) throws SQLException {
        Produto obj = new Produto();
        String sql = "SELECT ID, DESCRICAO, VALOR, INFORMACAO FROM PRODUTO "
                + "WHERE ID = ?";
        PreparedStatement p = conexao.prepareStatement(sql);
        p.setInt(1, idProduto);
        ResultSet result = p.executeQuery();
        
        result.next();
        obj.setId(result.getInt("ID"));
        obj.setDescricao(result.getString("DESCRICAO"));
        obj.setValor(result.getDouble("VALOR"));
        obj.setInformacao(result.getString("INFORMACAO"));
        result.close();
        
        return obj;
    }
}
