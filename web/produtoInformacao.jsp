<%-- 
    Document   : produtos
    Created on : 22/10/2016, 15:59:22
    Author     : Wpmartins
--%>

<%@page import="br.com.app.modelo.Produto"%>
<%@ page import="br.com.controller.LoginController" %>

<%
    if (LoginController.estaLogado(request)) {
        Produto p = (Produto) request.getAttribute("produto");
        String msgErro = (String) request.getAttribute("msgErro");
        if (p == null) {

            p = new Produto(0, "", 0, "");
        }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastro de Produto</title>
    </head>
    <body>
        <form>
            <%
                if (msgErro != null) {
            %>
            <br />
            <%= msgErro%>
            <br /><br />
            <%
                }
            %>
            <input type="hidden" name="id" value="<%= p.getId()%>"/>
            <label>Descrição:</label>
            <br/>
            <input type="text" name="descricao" value="<%= p.getDescricao()%>"/>
            <br/><br/>
            <label>Valor:</label>
            <br/>
            <input type="text" name="valor" <% if (p.getValor() == 0) { %> placeholder="0.0"<% } else {%> value="<%= p.getValor()%>" <% }%>/>
            <br/><br/>
            <label>Informações:</label>
            <br/>
            <input type="text" name="informacao" value="<%= p.getInformacao()%>"/>
            <br/><br/>
            <a href="/ServletPedido?acao=adicionarProduto&id=<%= p.getId()%>"><b>Comprar</b></a>

        </form>
        <br/><br/>
        <a href="/mainUsuario.jsp">Voltar</a>
    </body>
</html>

<% } else {
        response.sendRedirect("/ServletLogin");
    }
%>