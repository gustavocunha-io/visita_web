<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:import url="cabecalho.jsp"></c:import>
	<c:if test="${status eq false}">
        <p style="color: red;"><strong>Atenção:</strong> ${vagasRestantes}</p>
    </c:if>
	<form action="controladora" method="post">
		<input type="hidden" name="logica" value="AgendamentoController">
		<input type="hidden" name="acao" value="definirData">
	
		<div>
			<label for="data-agendamento">Data do agendamento: </label>
			<input type="date" id="data-agendamento" name="data-agendamento">
		</div>
		<div>a
			<label for="horario-agendamento">Horario: </label>
			<input type="time" id="horario-agendamento" name="horario-agendamento">
		</div>
		<div>
			<c:forEach var="museu" items="${museusList}">
				<div id="museu-${museu.id}">
					<label for="${museu.id}">
						<input type="radio" id="museu-${museu.id}" name="id-museu" value="${museu.id}">
						<strong>${museu.nome}: </strong> Das ${museu.horarioAbertura} às ${museu.horarioFechamento}
					</label>
				</div>
			</c:forEach>
		</div>
		<input type="submit" value="confirmar">
	</form>
</body>
</html>