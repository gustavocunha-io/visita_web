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
		String idMuseuStr = request.getParameter("museu-selecionado");
		
		System.out.println(nomeFuncionario);
		System.out.println(cpfFuncionario);
		System.out.println(idMuseuStr);
		
		if(nomeFuncionario != null && cpfFuncionario != null && idMuseuStr != null) {
			FuncionarioDao funcionarioDao = new FuncionarioDao();
			Integer idMuseu = Integer.valueOf(idMuseuStr);
			Funcionario funcionario = new Funcionario();
			
			funcionario.setNome(nomeFuncionario);
			funcionario.setCpf(cpfFuncionario);
			funcionario.setIdMuseu(idMuseu);
			
			funcionarioDao.inserir(funcionario);
		}
		
		return "index-administrativo.jsp";
	}

}
