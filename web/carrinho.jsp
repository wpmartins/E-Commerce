<%-- 
    Document   : carrinho
    Created on : 02/11/2016, 16:29:25
    Author     : Wpmartins
--%>

<%@page import="br.com.controller.LoginController"%>
<%@page import="br.com.app.modelo.PedidoItem"%>
<%@page import="br.com.app.modelo.Pedido"%>
<%
    if (LoginController.estaLogado(request)) {
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Carrinho de Compras</title>
    </head>
    <body>
        <h2>Meu Carrinho</h2>
        <table border="1" callpadding="2">
            <tr>
                <td>Excluir</td>
                <td>Item</td>
                <td>Qtde</td>
                <td>Valor Unitário</td>
                <td>Adicionar +1</td>
            </tr>
            <% 
                Pedido pedido = (Pedido) session.getAttribute("pedido");
                for(PedidoItem pedidoItem : pedido.getItens()){
            %>
            <tr>
                <td align="center"><a href="ServletPedido?acao=removerProduto&id=<%= pedidoItem.getProduto().getId()%>">X</a></td>
                <td><%= pedidoItem.getProduto().getDescricao()%></td>
                <td align="center"><%= pedidoItem.getQtde()%></td>
                <td><%= pedidoItem.getProduto().getValor()%></td>
                <td style="font-size: 20px" align="center"><a href="ServletPedido?acao=adicionarProduto&id=<%= pedidoItem.getProduto().getId()%>">+</a></td>
            </tr>
            <%
                }
            %>
        </table>
        <br/><br/>
        <a href="ServletPedido?acao=finalizarCompra">Finalizar Compra</a>
        <br/><br/>
        <a href="ServletProduto?acao=listarUsuario">Continue Comprando</a>
        <br/><br/>
        <td><a href="ServletPedido?acao=cancelarCompra">Cancelar Compra</a></td>
    </body>
</html>
<% } else {
        response.sendRedirect("/ServletLogin");
    }
%>
