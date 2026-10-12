<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>administracao</title>
	<link rel="stylesheet" href="css/cabecalho.css">
</head>
<body>
	<nav class="menu">
		<ul>
			<li><a href="index-administrativo.jsp">Inicio</a></li>
			<c:if test="${isAdministrador == true}">
				<li><a href="cadastro-funcionarios.jsp">Cadastro</a></li>
			</c:if>
			<li><a href="check-in.jsp">Check-in</a></li>
			<li><a href="relatorio.jsp">Relatório</a></li>
		</ul>
	</nav>
</body>
</html>