<%-- 
   Document   : produtos
   Created on : 22/10/2016, 15:59:22
   Author     : Wpmartins
--%>

<%@page import="br.com.app.modelo.Produto"%>
<%@page import="java.util.List"%>
<%@ page import="br.com.controller.LoginController" %>

<%
    if (LoginController.estaLogado(request)) {
        List<Produto> produtosList = (List<Produto>) request.getAttribute("produtos");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Produtos</title>
    </head>
    <body>
        <%
            if (produtosList != null) {
        %> 
        <form>
            <h3>Lista de produtos</h3>
            <table border="1">
                <br /><br />
                <tr>
                    <th>Código</th>
                    <th>Descrição</th>
                    <th>Editar</th>
                    <th>Excluir</th>
                </tr>
                <%
                    for (Produto p : produtosList) {
                %>
                <tr>
                    <td><%= p.getId()%></td>
                    <td><%= p.getDescricao()%></td>
                    <td><a href="/ServletProduto?acao=editar&id=<%= p.getId()%>"><b>Editar</b></a></td>
                    <td><a href="/ServletProduto?acao=excluir&id=<%= p.getId()%>"><b>Excluir</b></a></td>
                </tr>
                <%
                
                    }

                %>
                <tr>
                    <td colspan="2"></td>
                    <td colspan="2"><button style="background-color: cyan"><a href="cadastroProduto.jsp">ADICIONAR NOVO</a></button></td>
                </tr>
            </table>
        </form>
        <% 
         }    
        %>    
        <br />
        <a href="/main.jsp">Voltar</a>
    </body>
</html>

<% } else {
        response.sendRedirect("/ServletLogin");
    }
%>