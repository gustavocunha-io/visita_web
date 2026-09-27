<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${empty museusList}">
    <% response.sendRedirect("controladora?logica=ExibirMuseus"); %>
</c:if>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
</head>
<body>
	<c:import url="cabecalho.jsp"></c:import>
	<c:if test="${status eq false}">
    	<div style="padding: 10px; border: 1px solid #ccc; margin-bottom: 15px; color red;">
        	<span><strong>Erro:</strong> A data ou horário inserido não batem com os dias e horário de funcionamento do museu. </span> 
    	</div>
	</c:if>
	<h1>Agendamento Museu</h1>
	<form action="controladora" method="post">
		<input type="hidden" name="logica" value="SalvarAgendamento">
		<label for="data-agendamento">Data do Agendamento: </label>
		<input id="data-agendamento" name="data-agendamento" type="date" required="required">
		<label for="horário">Horário: </label>
		<input id="horario-agendamento" name="horario-agendamento" type="time" required="required">
		<input id="botao-enviar" type="submit" value="Confirmar">
		<c:forEach var="museu" items="${museusList}">
    	<div style="margin-bottom: 8px;">
        	<label>
            	<input type="radio" name="id-museu" value="${museu.id}" />
            	<strong>${museu.nome}</strong> 
            	Das ${museu.horarioAbertura} às ${museu.horarioFechamento}
        	</label>
    	</div>
	</c:forEach>
	<label for="cpf-usuario">CPF:</label>
	<input id="cpf-usuario" name="cpf-usuario" type="text" ${status eq true ? '' : 'disabled'} required>
	</form>
</body>
</html>