package br.com.app;

import br.com.app.modelo.Produto;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.com.app.modelo.ProdutoDAO;
import br.com.controller.LoginController;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServletProduto extends HttpServlet {

    Produto produto = new Produto();
    ProdutoDAO produtoDAO = new ProdutoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String acao = req.getParameter("acao");
        String id = req.getParameter("id");

        switch (acao) {
            case "listar":
                listaProdutos(req, resp);
                break;

            case "editar":
                editarProduto(req, resp, id);
                break;

            case "excluir":
                excluirProduto(req, resp, id);
                break;

            case "cadastrar":
                cadastrarProduto(req, resp);
                break;
            
            case "listarUsuario":
                listaProdutosUsuario(req, resp);
                break;
            case "sair":
                sair(req, resp);
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String descricao = req.getParameter("descricao");
        String valor = req.getParameter("valor");
        if (valor.equals("")){
            valor = "0";
        }
        String informacao = req.getParameter("informacao");

        if (!id.equals("0")) {
            produto.setId(Integer.parseInt(id));
        }
        produto.setDescricao(descricao);
        produto.setValor(Double.parseDouble(valor));
        produto.setInformacao(informacao);

        if (id.equals("0")) {
            cadastrarProduto(produto, req, resp);
        } else {
            try {
                editarProduto(produto, req, resp);
            } catch (SQLException ex) {
                Logger.getLogger(ServletProduto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private void listaProdutos(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Connection conexao = (Connection) req.getAttribute("conexao");
            ProdutoDAO produtoDAO = new ProdutoDAO();

            List<Produto> produtosList = produtoDAO.listarProdutos();

            req.setAttribute("produtos", produtosList);
            /*Load or List*/
        } catch (SQLException ex) {
            Logger.getLogger(ServletProduto.class.getName()).log(Level.SEVERE, null, ex);
        }

        req.getRequestDispatcher("/listaProdutoAdmin.jsp").forward(req, resp);
    }

    /**
     * Método responsável por efetivar um cadastro
     * @param produto
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException 
     */
    private void cadastrarProduto(Produto produto, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!produto.getDescricao().equals("")) {
            try {
                produtoDAO.incluir(produto);
                listaProdutos(req, resp);
            } catch (SQLException ex) {
                Logger.getLogger(ServletProduto.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            String msgErro = "Informe a descrição !";
            req.setAttribute("msgErro", msgErro);
            req.getRequestDispatcher("/cadastroProduto.jsp").forward(req, resp);
        }
    }

    /**
     * Método para redirecionar para página de cadastro
     * @param req
     * @param resp 
     */
    private void cadastrarProduto(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/cadastroProduto.jsp").forward(req, resp);
    }

    private void editarProduto(Produto produto, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        produtoDAO.alterar(produto);
        listaProdutos(req, resp);
    }

    private void editarProduto(HttpServletRequest req, HttpServletResponse resp, String id) throws ServletException, IOException {
        produto.setId(Integer.parseInt(id));
        {
            try {
                req.setAttribute("produto", produtoDAO.retornaProdutoId(produto));
                req.getRequestDispatcher("/cadastroProduto.jsp").forward(req, resp);
            } catch (SQLException ex) {
                Logger.getLogger(ServletProduto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void excluirProduto(HttpServletRequest req, HttpServletResponse resp, String id) throws ServletException, IOException {
        produto.setId(Integer.parseInt(id));
        try {
            produtoDAO.excluir(produto);
            listaProdutos(req, resp);
        } catch (SQLException ex) {
            Logger.getLogger(ServletProduto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listaProdutosUsuario(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Connection conexao = (Connection) req.getAttribute("conexao");
            ProdutoDAO produtoDAO = new ProdutoDAO();

            List<Produto> produtosList = produtoDAO.listarProdutos();

            req.setAttribute("produtos", produtosList);
            /*Load or List*/
        } catch (SQLException ex) {
            Logger.getLogger(ServletProduto.class.getName()).log(Level.SEVERE, null, ex);
        }

        req.getRequestDispatcher("/listaProdutoUsuario.jsp").forward(req, resp);
    }

    private void sair(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.addCookie(LoginController.getCookie("usuario", null));
        resp.addCookie(LoginController.getCookie("senha", null));
        
        resp.sendRedirect("/index.jsp");
    }

}
