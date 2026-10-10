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
	<c:if test="${status eq false}">
    	<div style="padding: 10px; border: 1px solid #ccc; margin-bottom: 15px; color red;">
        	<span><strong>Erro:</strong> A data ou horário inserido não batem com os dias e horário de funcionamento do museu. </span> 
    	</div>
	</c:if>
	<h1>Agendamento Museu</h1>
	<form action="controladora" method="post">
		
		<label for="data-agendamento">Data do Agendamento: </label>
		<input id="data-agendamento" name="data-agendamento" type="date" <c:if test="${museu.id eq museuSelecionado}">checked</c:if> required><br>
		<label for="horario-agendamento">Horários: </label>
		<input id="horario-agendamento" name="horario-agendamento" type="time" required><br>
		
		<c:forEach var="museu" items="${museusList}">
    		<div style="margin-bottom: 8px;">
        		<label>
            		<input type="radio" name="id-museu" value="${museu.id}" ${museu.id eq museuSelecionado ? 'checked' : ''}/>
            		<strong>${museu.nome}</strong> 
            		Das ${museu.horarioAbertura} às ${museu.horarioFechamento}
        		</label>
    		</div>
		</c:forEach>
		
		<input type="text" hidden="true" name="logica" value="AgendamentoController">
		<input type="submit" value="confirmar">
		
		<c:set var="statusAgendamento" value="${not empty status ? '' : 'disabled'}"></c:set>
		
		<div>
			<label for="cpf-usuario">CPF: </label>
			<input id="cpf-usuario" name="cpf-usuario" type="text" ${statusAgendamento} required>
			<label for="nome-usuario">Nome: </label>
			<input id="nome-usuario" name="nome-usuario" type="text" ${statusAgendamento} required>
			<label for="emails">Email(s): </label>
			<input id="emails" name="emails" type="email" ${statusAgendamento} required>
		</div>
		
	</form>
</body>
</html>