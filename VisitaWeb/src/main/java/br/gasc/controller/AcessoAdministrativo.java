package br.gasc.controller;


import br.gasc.dao.AdministradorDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AcessoAdministrativo implements Logica {

	@Override
	public String executar(HttpServletRequest request, HttpServletResponse response) {
		String login = request.getParameter("campo-login");
		String senha = request.getParameter("campo-senha-login");
		
		System.out.println(login);
		System.out.println(senha);
		
		AdministradorDao adminDao = new AdministradorDao();
		boolean flag = adminDao.validarCredenciais(login, senha);
		
		System.out.println(flag);
		
		if(flag == true) {
			HttpSession httpSession = request.getSession();
			
			httpSession.setMaxInactiveInterval(300); // 5 minutos.
			httpSession.setAttribute("status", true);
			httpSession.setAttribute("login", login);
			
			return "cadastro-funcionarios.jsp";
		}
		
		return "acesso.jsp";
	}
}