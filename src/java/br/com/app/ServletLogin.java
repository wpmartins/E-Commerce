package br.com.app;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.com.controller.LoginController;
import javax.servlet.http.HttpSession;

public class ServletLogin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        login(req, resp);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String usuario = req.getParameter("usuario");
        String senha = req.getParameter("senha");

        if (LoginController.login(usuario, senha)) {
            resp.addCookie(LoginController.getCookie("usuario", usuario));
            resp.addCookie(LoginController.getCookie("senha", senha));
            
            if (LoginController.getTipo() == 1){
                HttpSession sessao = req.getSession();
                sessao.setAttribute("usuarioLogado", usuario);
                resp.sendRedirect("/mainAdmin.jsp");
            } else {
                resp.sendRedirect("/mainUsuario.jsp");
            }
            
        } else {
            req.setAttribute("mensagem", "Dados inválidos!");
            doGet(req, resp);
        }
    }

}
