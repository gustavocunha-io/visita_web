<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:import url="cabecalho.jsp" />

    <c:if test="${empty numeroPessoas}">
        <form action="controladora" method="post">
            <input type="hidden" name="logica" value="AgendamentoController">
            <input type="hidden" name="acao" value="confirmarNumeroPessoas">
            
            <input type="hidden" name="id-museu" value="${museu.id}">
			<input type="hidden" name="data-agendamento" value="${dataAgendamento}">
			<input type="hidden" name="horario-agendamento" value="${horarioAgendamento}">
            
            <div>
                <label for="numero-pessoa"> Número de pessoas do agendamento:
                    <input type="number" id="numero-pessoas" name="numero-pessoas" min="1" max="${vagasRestantes}" required>
                </label>
            </div>
            <button type="submit">Avançar</button>
        </form>
    </c:if>

    <c:if test="${not empty numeroPessoas}">
        <form action="controladora" method="post">
            <input type="hidden" name="logica" value="AgendamentoController">
            <input type="hidden" name="acao" value="confirmarAgendamento">
            
            <input type="hidden" name="id-museu" value="${idMuseuSelecionado}">
            <input type="hidden" name="data-agendamento" value="${dataSelecionada}">
            <input type="hidden" name="horario-agendamento" value="${horarioSelecionado}">
            <input type="hidden" name="numero-pessoas" value="${numeroPessoas}">

            <p>Número de pessoas selecionado: <strong>${numeroPessoas}</strong> 
                (<a href="data-agendamento.jsp">Alterar</a>)
            </p>

            <div style="margin: 15px 0;">
                <p>Insira o email do responsável:</p>
                <div>
                	<label for="email-responsavel"> Email:
                		<input type="email" id="email-responsavel" name="email-responsavel">
                	</label>
                </div>
                <div style="display: grid; margin-bottom: 5px;">
	                <c:forEach var="indice" begin="1" end="${numeroPessoas}">
		                <div>
		                	<p>Dados pessoa ${indice}</p>
		                    <label > CPF: 
		                        <input type="text" name="cpf-visitante">
		                    </label>
		                    <label>Nome:
		                    	<input type="text" name="nome-visitante">
		                    </label>
		                    
		                    <select name="ingresso-selecionado">
		                    	<option value="" disabled selected>tipo ingresso</option>
				              	<c:forEach var="ingresso" items="${ingressosList}">
				                    <option value="${ingresso.id}">${ingresso.tipo}</option>
				            	</c:forEach>
		                    </select>
		                </div>
	                </c:forEach>
                </div>
            </div>
            
            
            <div style="margin: 25px 0; background: #fff3cd; padding: 15px; border: 1px solid #ffeeba;">
            <h4>Termo de Responsabilidade</h4>
            <p style="font-size: 0.9em; color: #856404;">
                Ao confirmar este agendamento, declaro estar ciente de que as vagas são limitadas e assumo o compromisso 
                de comparecer no dia e horário estipulados. Declaro ainda que os dados informados acima são verdadeiros 
                e que os documentos comprobatórios de meia-entrada ou isenção serão apresentados na recepção do museu.
            </p>
            <label>
                <input type="checkbox" name="termo-responsabilidade" value="true" required>
                <strong>Declaro que li e concordo com o Termo de Responsabilidade.</strong>
            </label>
        </div>
        
        <input type="submit" value="Confirmar Agendamento">
        
        </form>
    </c:if>
</body>
</html>