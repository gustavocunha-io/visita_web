<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="autenticacao.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:import url="cabecalho-administrativo.jsp"></c:import>
	<c:if test="${not empty status}">
		<p>${mensagem}</p>
	</c:if>
	<div id="mensagem-saudacao">
		<p>
			Bem vindo a página de gerenciamento do administrador e os funcionários de cada museu
		</p>
	</div>
</body>
</html>