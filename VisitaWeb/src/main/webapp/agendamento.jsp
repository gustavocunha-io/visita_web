<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
</head>
<body>
	<c:import url="cabecalho.jsp"></c:import>
	<h1>Agendamento Museu</h1>
	<form action="controladora" method="get">
		<input type="hidden" id="logica" value="ExibirMuseu">
		<label for="data-agendamento">Data do Agendamento</label>
		<input id="data-agendamento" name="data-agendamento" type="date">
		<label for="horário">Horário</label>
		<input id="horario" name="horario" type="time">
		<button id="botao-enviar" type="submit">Prosseguir</button>
		<label for="selecao-museu">
			<select id="selecao-museu" name="museu-id">
				<c:forEach var="museu" items="${museusList}">
					<option value="${museu.nome}"> Horário de funcionamento: ${museu.horarioAbertura}</option>
				</c:forEach>			
			</select>
			</label>
	</form>
</body>
</html>