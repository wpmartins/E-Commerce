<%@ page import="br.com.controller.LoginController" %>

<%
    if (LoginController.estaLogado(request)) {
        
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
    </head>
    <body>
        <H1>E-Commerce</H1>
        <br />
        <table border="1">
            <tr>
                <td><a href="ServletProduto?acao=listarUsuario">Lista de Produtos</a></td>
                <td><a href="ServletPedido?acao=listarCarrinho">Meu Carrinho</a></td>
            </tr>
        </table>        
        
    </body>
</html>

<% } else {
        response.sendRedirect("/ServletLogin");
    }
%>