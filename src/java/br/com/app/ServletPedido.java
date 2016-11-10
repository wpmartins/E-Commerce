package br.com.app;

import br.com.app.modelo.Pedido;
import br.com.app.modelo.PedidoDAO;
import br.com.app.modelo.PedidoItem;
import br.com.app.modelo.Produto;
import br.com.app.modelo.ProdutoDAO;
import br.com.app.modelo.UsuarioDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ServletPedido extends HttpServlet {
    private Produto produto = new Produto();
    private ProdutoDAO produtoDAO = new ProdutoDAO();
    private PedidoDAO pedidoDAO = new PedidoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String acao = req.getParameter("acao");
        String id = (String) req.getParameter("id");

        switch (acao) {
            case "informacaoProduto":
                mostrarProduto(req, resp, id);
                break;
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
            case "finalizarCompra": {
                try {
                    finalizarCompra(req, resp);
                } catch (SQLException ex) {
                    Logger.getLogger(ServletPedido.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
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

    private void finalizarCompra(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        HttpSession sessao = req.getSession();
        Pedido pedido = (Pedido) sessao.getAttribute("pedido");

        Cookie[] cookies = req.getCookies();

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        int idUsuario = usuarioDAO.retornaIdUsuario(cookies[1].getName());
        pedido.setIdUsuario(idUsuario);

        boolean inclusao = false;
        int idPedido = 0;
        try {
            idPedido = pedidoDAO.incluirPedido(pedido);
            inclusao = true;
        } catch (Exception e) {
            resp.sendRedirect("/erro.jsp");
        }

        if (inclusao == true) {
            for (PedidoItem pedidoItem : pedido.getItens()) {
                pedidoItem.setIdPedido(idPedido);
                pedidoDAO.incluirItemDoPedido(pedidoItem);

            }
            resp.sendRedirect("/compraEfetuada.jsp");
        }

    }

    private void mostrarProduto(HttpServletRequest req, HttpServletResponse resp, String id) throws ServletException, IOException {
        produto.setId(Integer.parseInt(id));
        {
            try {
                req.setAttribute("produto", produtoDAO.retornaProdutoId(produto));
                req.getRequestDispatcher("/produtoInformacao.jsp").forward(req, resp);
            } catch (SQLException ex) {
                Logger.getLogger(ServletProduto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
