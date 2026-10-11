<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:import url="cabecalho-administrativo.jsp"></c:import>
	<c:if test="${not empty status}">
		Opa
	</c:if>
	<h1>Cadastro de Funcionarios</h1>
	<form action="controladora" method="post">
		<div>
			<input type="hidden" name="logica" value="CadastroFuncionariosController">
			<label for="nome-funcionario">
				Nome: <input type="text" id="nome-funcionario" name="nome-funcionario" autocomplete="off">
			</label>
			<label>
				CPF: <input type="text" id="cpf-funcionario" name="cpf-funcionario" autocomplete="off"> 
			</label>
			
			<select id="museu-selecionado" name="museu-selecionado">
				<option value="" disabled="disabled" selected="selected">selecionar museu</option>
				<c:forEach var="museu" items="${museusList}">
					<option value="${museu.id}">${museu.nome}</option>
				</c:forEach>
			</select>
			<input type="submit" value="cadastrar">
		</div>
	</form>
</body>
</html>