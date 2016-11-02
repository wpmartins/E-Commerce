package br.com.app;

import br.com.app.modelo.Pedido;
import br.com.app.modelo.PedidoItem;
import br.com.app.modelo.Produto;
import br.com.app.modelo.ProdutoDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ServletPedido extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String acao = req.getParameter("acao");
        String id = (String) req.getParameter("id");

        switch (acao) {
            case "adicionarProduto":
                adicionarAoCarrinho(req, resp, id);
                break;
            case "listarCarrinho":
                listarCarrinho(req, resp);
            case "removerProduto":
                removerDoCarrinho(req, resp, id);
                break;
            case "cancelarCompra":
                cancelarCompra(req, resp);
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private void adicionarAoCarrinho(HttpServletRequest req, HttpServletResponse resp, String id) throws ServletException, IOException {
        int idProduto = Integer.parseInt(id);
        boolean existe = false;

        HttpSession sessao = req.getSession();
        Pedido pedido = (Pedido) sessao.getAttribute("pedido");

        if (pedido == null) {
            pedido = new Pedido();
            sessao.setAttribute("pedido", pedido);
        }

        if (pedido.getItens() != null) {
            for (PedidoItem pedidoItem : pedido.getItens()) {
                if (pedidoItem.getProduto().getId() == idProduto) {
                    pedidoItem.setQtde(pedidoItem.getQtde() + 1);
                    existe = true;
                }
            }
        }

        if (existe == false) {
            Produto produto = null;
            try {
                produto = new ProdutoDAO().retornaProdutoId(idProduto);
            } catch (SQLException ex) {
                Logger.getLogger(ServletPedido.class.getName()).log(Level.SEVERE, null, ex);
            }

            PedidoItem pedidoItem = new PedidoItem();
            pedidoItem.setProduto(produto);
            pedidoItem.setQtde(1);

            pedido.adicionarProduto(pedidoItem);
        }

        req.getRequestDispatcher("/carrinho.jsp").forward(req, resp);
    }

    private void removerDoCarrinho(HttpServletRequest req, HttpServletResponse resp, String id) throws ServletException, IOException {
        HttpSession sessao = req.getSession();

        Pedido pedido = (Pedido) sessao.getAttribute("pedido");

        int idProduto = Integer.parseInt(id);

        PedidoItem pedidoItem = new PedidoItem();
        Produto produto = new Produto();
        produto.setId(idProduto);
        pedidoItem.setProduto(produto);

        pedido.removerProduto(pedidoItem);
        
        req.getRequestDispatcher("/carrinho.jsp").forward(req, resp);
    }

    private void cancelarCompra(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sessao = req.getSession();

        sessao.removeAttribute("pedido");

        req.getRequestDispatcher("ServletProduto?acao=listarUsuario").forward(req, resp);
    }

    private void listarCarrinho(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sessao = req.getSession();
        Pedido pedido = (Pedido) sessao.getAttribute("pedido");

        if (pedido == null) {
            String msgErro = "Não existem itens no carrinho";
            req.setAttribute("msgErro", msgErro);
            req.getRequestDispatcher("/mainUsuario.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/carrinho.jsp").forward(req, resp);
        }
    }
}
