<%-- 
    Document   : compraEfetuada
    Created on : 05/11/2016, 17:14:47
    Author     : Wpmartins
--%>
<%@page import="br.com.controller.LoginController"%>
<%
    if (LoginController.estaLogado(request)) {
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Compra efetuada</title>
    </head>
    <body>
        <h2>Compra efetuada com sucesso, agradecemos a confiança !</h2>
        <br />
        <a href="ServletPedido?acao=cancelarCompra">Nova Compra</a>
        <br /><br />
        <a href="ServletProduto?acao=sair">Sair</a>
        <br />
    </body>
</html>
<% } else {
        response.sendRedirect("/ServletLogin");
    }
%>