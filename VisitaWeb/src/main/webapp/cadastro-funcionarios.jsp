<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="autenticacao.jsp"%>
<%@ include file="isAdministrador.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" href="css/cadastro-funcionario.css">
</head>
<body>
	<c:import url="cabecalho-administrativo.jsp"></c:import>
	<h1 id="cabecalho">Cadastro de Funcionarios</h1>
	<c:if test="${status == false}">
		<hr>
		<p id="mensagem-alerta">${mensagem}</p>
	</c:if>
	<hr>
	<form action="controladora" method="post">
		<div class="dados-funcionario">
			<input type="hidden" name="logica" value="CadastroFuncionariosController">
			<label for="nome-funcionario">Nome:<br>
				<input type="text" id="nome-funcionario" name="nome-funcionario" autocomplete="off">
			</label>
			<label>CPF:<br>
				<input type="text" id="cpf-funcionario" name="cpf-funcionario" autocomplete="off"> 
			</label>
			<label> Senha:<br>
				<input type="text" id="senha-funcionario" name="senha-funcionario" autocomplete="off">
			</label>
			<label> Museu:<br>
				<select id="museu-selecionado" name="museu-selecionado">
				<option value="" disabled="disabled" selected="selected">selecionar museu</option>
				<c:forEach var="museu" items="${museusList}">
					<option value="${museu.id}">${museu.nome}</option>
				</c:forEach>
			</select>
			</label>
			<hr>
			<input type="submit" id="botao-cadastrar" value="cadastrar">
		</div>
	</form>
</body>
</html>