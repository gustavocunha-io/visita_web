<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/acesso.css">
<title>Insert title here</title>
</head>
<body>
	<div class="card-autenticacao">
		<div id="bloco-login" class="bloco-autenticacao">
			<h1 id="titulo">Painel Administrativo</h1>
			<h3 id="mensagem-alerta">Acesso somente de pessoal autorizado!</h3>
			<form action="controladora" method="post" class="formulario-login">
				<input type="hidden" name="logica" value="AcessoAdministrativo">
				<input type="text" id="campo-login" name="campo-login" placeholder="usename" autocomplete="off" required="required">
				<input type="password" id="campo-senha-login" name="campo-senha-login" placeholder="password" required="required">
				<input type="checkbox" id="mostrar-senha-login" onchange="mostrarSenha('campo-senha-login', 'mostrar-senha-login')">
				<label for="mostrar-senha-login">Mostra senha</label>
				<input type="submit" id="botao-enviar" value="Entrar">
			</form>
			<footer id="rodape">Sistema GASC - agendamentos online</footer>
		</div>
	</div>
	<script type="text/javascript">
		function mostrarSenha(idCampoSenha, idCampoCheckbox) {
			const campoSenha = document.getElementById(idCampoSenha);
			const checkboxSenha = document.getElementById(idCampoCheckbox);
			
			if(checkboxSenha.checked) {
				campoSenha.type = "text";
			} else {
				campoSenha.type = "password";
			}
		}
	</script>
</body>
</html>