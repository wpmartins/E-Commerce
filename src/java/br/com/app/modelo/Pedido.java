package br.com.app.modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Wpmartins
 */
public class Pedido {
    private int idPedido;
    private int idUsuario;
    private List<PedidoItem> itens;

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public List<PedidoItem> getItens() {
        return itens;
    }

    
    public void adicionarProduto(PedidoItem pedidoItem){
        if(this.itens==null){
            this.itens = new ArrayList<PedidoItem>();
        }
        this.itens.add(pedidoItem);
    }
    
    public void removerProduto(PedidoItem pedidoItemRemover){
        for(Iterator i = itens.iterator(); i.hasNext();){
            PedidoItem pedidoItem = (PedidoItem) i.next();
            if (pedidoItem.getProduto().getId() == pedidoItemRemover.getProduto().getId()) {
                i.remove();
            }
        }
    }
}
