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
	<c:if test="${empty pessoasList}">
		<form action="controladora" method="post">
			<input type="hidden" name="logica" value="CancelamentoController">
			<input type="hidden" name="acao" value="buscarAgendamento">
			<div>
				<label for="codigo-confirmacao"> Codigo de confirmação:
					<input type="text" id="codigo-confirmacao" name="codigo-confirmacao">
				</label>		
			</div>
			<div>
				<label for="email-responsavel"> Email:
					<input type="email" id="email-responsavel" name="email-responsavel">
				</label>
			</div>
			<input type="submit" value="buscar">
		</form>
	</c:if>
	
	<c:if test="${not empty pessoasList}">
		<form action="controladora" method="post">
			<input type="hidden" name="logica" value="CancelamentoController">
			<input type="hidden" name="id-agendamento" value="${idAgendamento}">
			<input type="hidden" name="acao" value="removerAgendamentoPessoa">
			<div>
				<c:forEach var="pessoa" items="${pessoasList}">
				<div>
					<span>${pessoa.nome} - (${pessoa.cpf})</span>
					<label>
						<input type="checkbox" id="botao-cancelar" name="botao-cancelar" value="${pessoa.id}">
						Cancelar				
					</label>
				</div>
			</c:forEach>
			
			<input type="submit" id="botao-confirmar" name="botao-excluir" value="Excluir">
			<input type="submit" id="botao-excluir-agendamento" name="botao-excluir" value="Excluir tudo">
			</div>
			
		</form>
	
	</c:if>
</body>
</html>