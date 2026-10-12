<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Relatórios de Visitação</title>
    <style>
        table { border-collapse: collapse; width: 100%; margin-top: 15px; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
    </style>
</head>
<body>
	<c:import url="cabecalho-administrativo.jsp"></c:import>
    <h1>Painel de Relatórios de Visitação</h1>
    <hr>
    <div class="filtros">
        <form action="controladora" method="post" class="bloco-filtro">
            <input type="hidden" name="logica" value="RelatorioController">
            <label><strong>Relatório por Dia:</strong></label>
            <input type="date" name="dataFiltro" required>
            <button type="submit" name="tipo-relatorio" value="porDia">Gerar por Dia</button>
        </form>

        <form action="controladora" method="post" class="bloco-filtro">
            <input type="hidden" name="logica" value="RelatorioController">
            <label><strong>Relatório por Data e Hora:</strong></label>
            <input type="datetime-local" name="dataHoraFiltro" required>
            <button type="submit" name="tipo-relatorio" value="porDataHora">Gerar por Data e Hora</button>
        </form>
    </div>
    <c:if test="${not empty tituloRelatorio}">
        <h2>${tituloRelatorio}</h2>
    </c:if>
    <c:if test="${not empty relatorio}">
        <table>
            <thead>
                <tr>
                    <th>Visitante</th>
                    <th>CPF</th>
                    <th>Museu</th>
                    <th>Data / Hora</th>
                    <th>Ingresso</th>
                    <th>Valor (R$)</th>
                    <th>Compareceu?</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${relatorio}">
                    <tr>
                        <td>${item.nomePessoa}</td>
                        <td>${item.cpf}</td>
                        <td>${item.nomeMuseu}</td>
                        <td>${item.dataHora}</td>
                        <td>${item.tipoIngresso}</td>
                        <td>R$ ${item.preco}</td>
                        <td>${item.compareceu ? "Sim" : "Não"}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${empty relatorio and not empty tituloRelatorio}">
        <p>Nenhuma visitação encontrada para o filtro selecionado.</p>
    </c:if>
</body>
</html>