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
	<form action="controladora" method="post">
		<input type="hidden" id="logica" value="logica">
		<label for="data-agendamento">Data do Agendamento</label>
		<input id="data-agendamento" name="data-agendamento" type="date">
		<label for="horário">Horário</label>
		<input id="horario" name="horario" type="time">
		<label for="selecao-museu">
			<select id="selecao-museu" name="museu-id">
				<c:forEach var="museu" items="${museusList}">
					<option value="${museu.name}"> Horário de funcionamento: ${museu.horario_funcionamento}</option>
				</c:forEach>			
			</select>
			</label>
		<button id="botao-enviar" type="submit">Enviar</button>
	</form>
</body>
</html>