<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>check-in</title>
	<link rel="stylesheet" href="css/check-in.css">
</head>
<body>
	<c:import url="cabecalho-administrativo.jsp"></c:import>
	<c:if test="${status == false}">
		<p id="mensagem-alerta">${mensagem}</p>
	</c:if>
	<c:if test="${empty pessoasList}">
		<form action="controladora" method="post">
			<div>
				<input type="hidden" name="logica" value="CheckInController">
				<input type="hidden" name="acao" value="buscarAgendamento">
				<label>Data: <input type="date" id="data-agendamento" name="data-agendamento" required="required"></label>
				<label>Horário: <input type="time" id="horario-agendamento" name="horario-agendamento" required="required"></label>
				<input type="submit" value="buscar">
			</div>
		</form>
	</c:if>
	<c:if test="${not empty pessoasList}">
		<h1>Dados dos visitantes</h1>
		<form action="controladora" method="post">
			<input type="hidden" name="logica" value="CheckInController">
			<input type="hidden" name="acao" value="salvarCheckIn">
			<hr>
			<c:forEach var="pessoa" items="${pessoasList}">
				<div id="dados-pessoa">
					<label>Nome:<br><input type="text" id="nome-pessoa" value="${pessoa.nome}" disabled="disabled"></label>
					<label>CPF:<br><input type="text" value="${pessoa.cpf}" disabled="disabled"></label>
					<c:forEach var="agendamentoPessoa" items="${agendamentoPessoasList}">
						<c:if test="${agendamentoPessoa.idPessoa == pessoa.id}">
							<c:forEach var="ingresso" items="${ingressosList}">
								<c:if test="${ingresso.id == agendamentoPessoa.tipoIngresso}">
									<label>Tipo ingresso:<br><input type="text" value="${ingresso.tipo}" disabled="disabled"></label>
								</c:if>
							</c:forEach>
							<label><br><input type="checkbox" id="compareceu" name="compareceu" value="${pessoa.id}" ${agendmanetoPessoa.compareceu == true ? 'checked="checked" disabled="disabled"' : ''}> Compareceu</label>
						</c:if>
					</c:forEach>
				</div>
				<hr>
			</c:forEach>
			<input type="submit" id="botao-salvar" value="salvar">
		</form>
	</c:if>
</body>
</html>