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
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastro de Produto</title>
    </head>
    <body>
        <form action="ServletProduto" method="POST">
            <input type="hidden" name="id" value="<%= p.getId()%>"/>
            <label>Descrição:</label>
            <br/>
            <input type="text" name="descricao" value="<%= p.getDescricao()%>"/>
            <br/><br/>
            <label>Valor:</label>
            <br/>
            <input type="text" name="valor" value="<%= p.getValor()%>"/>
            <br/><br/>
            <label>Informações:</label>
            <br/>
            <input type="text" name="informacao" value="<%= p.getInformacao()%>"/>
            <br/>
            <input type="submit" value="Gravar"/>

        </form>
    </body>
</html>

<% } else {
        response.sendRedirect("/ServletLogin");
    }
%>