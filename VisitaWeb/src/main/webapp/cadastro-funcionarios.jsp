<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:if test="${not empty status}">
		
	</c:if>
	<h1>Cadastro de Funcionarios</h1>
	<form action="controladora" method="post">
		<input type="hidden" name="logica" value="CadastroFuncionariosController">
		<input type="text" id="nome-funcionario" name="nome-funcionario" autocomplete="off">
		<input type="">	
	</form>
</body>
</html>