<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
	<c:import url="cabecalho.jsp"></c:import>
	<form action="controladora">
		<label for="nome">Nome: </label>
		<input id="nome" type="text">
		<label for="cpf">CPF: </label>
		<input id="cpf" type="text">
		<label for="email">Email: </label>
		<input id="email" type="email">
		<input id="logica" type="submit">
	</form>
<body>
</body>
</html>