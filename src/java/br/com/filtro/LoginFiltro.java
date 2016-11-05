package br.com.filtro;

import br.com.app.modelo.Usuario;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Douglas
 */
public class LoginFiltro implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession sessao = req.getSession();

        if (permitirAcesso(req) || estaLogado(sessao)) {
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect("/ServletLogin");
        }
    }

    private boolean estaLogado(HttpSession sessao) {
        Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
        if (usuario != null) {
            return true;
        }
        return false;
    }

    private boolean permitirAcesso(HttpServletRequest req) {
        String servletPath = req.getServletPath();
        if (servletPath.equals("/ServletLogin")) {
            return true;
        }
        return false;
    }

    @Override
    public void destroy() {
    }
}
