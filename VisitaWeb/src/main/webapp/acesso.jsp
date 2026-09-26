<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" href="css/acesso.css">
</head>
<body>
	<div class="card-autenticacao">
		<div id="bloco-login" class="bloco-autenticacao">
			<h1>Login</h1>
			<form action="controladora" method="post">
				<input type="hidden" id="logica" value="logica">
				<input type="email" id="campo-email" placeholder="fulano@gmail.com" required="required">
				<input type="password" id="campo-senha-login" placeholder="senha" required="required">
				<input type="checkbox" id="mostrar-senha-login" onchange="mostrarSenha('campo-senha-login', 'mostrar-senha-login')">
				<label for="mostrar-senha-login">Mostra senha</label>
			</form>
			<p>Já tem conta? <a href="#" onclick="alternarAbas()">Cadastre-se aqui</a></p>
		</div>
		<div id="bloco-cadastro" class="bloco-autenticacao bloco-escondido">
			<form action="controladora" method="post">
				<input type="text" placeholder="nome e sobrenome" required="required">
				<input type="text" placeholder="cpf" required="required">
				<input type="email" placeholder="email" required="required">
				<input type="password" id="campo-senha-cadastro" placeholder="senha" required="required">
				<input type="checkbox" id="mostrar-senha-cadastro" onchange="mostrarSenha('campo-senha-cadastro', 'mostrar-senha-cadastro')">
				<label for="mostrar-senha">Mostra senha</label>
			</form>
			<p>Já tem conta? <a href="#" onclick="alternarAbas()">Cadastre-se aqui</a></p>
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
		
	    // JavaScript que faz a mágica de mudar no mesmo instante/tela
	    function alternarAbas() {
	        var login = document.getElementById("bloco-login");
	        var cadastro = document.getElementById("bloco-cadastro");
	        
	        login.classList.toggle("bloco-escondido");
	        cadastro.classList.toggle("bloco-escondido");
	    }
	</script>
</body>
</html>