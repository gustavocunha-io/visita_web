package br.gasc.controller;

import br.gasc.dao.FuncionarioDao;
import br.gasc.model.Funcionario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CadastroFuncionariosController implements Logica {

	@Override
	public String executar(HttpServletRequest request, HttpServletResponse response) {
		String nomeFuncionario = request.getParameter("nome-funcionario");
		String cpfFuncionario = request.getParameter("cpf-funcionario");
		String senhaFuncionario = request.getParameter("senha-funcionario");
		String idMuseuStr = request.getParameter("museu-selecionado");
		
		System.out.println(nomeFuncionario);
		System.out.println(cpfFuncionario);
		System.out.println(idMuseuStr);
		
		if(nomeFuncionario != null && cpfFuncionario != null && senhaFuncionario != null && idMuseuStr != null) {
			FuncionarioDao funcionarioDao = new FuncionarioDao();
			Funcionario funcionario = new Funcionario();
			Integer idMuseu = Integer.valueOf(idMuseuStr);
			
			funcionario.setNome(nomeFuncionario);
			funcionario.setCpf(cpfFuncionario);
			funcionario.setSenha(senhaFuncionario);
			funcionario.setIdMuseu(idMuseu);
			funcionarioDao.inserir(funcionario);
			
			request.setAttribute("status", true);
			
			return "index-administrativo.jsp";
		}
		
		request.setAttribute("mensagem-erro", "Erro: Os valores não podem estar em branco!");
		request.setAttribute("status", false);
		
		return "cadastro-funcionario.jsp";
	}

}
