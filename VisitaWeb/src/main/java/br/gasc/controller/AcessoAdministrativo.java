package br.gasc.controller;


import java.util.List;

import br.gasc.dao.AdministradorDao;
import br.gasc.dao.MuseuDao;
import br.gasc.model.Museu;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AcessoAdministrativo implements Logica {

	@Override
	public String executar(HttpServletRequest request, HttpServletResponse response) {
		String login = request.getParameter("campo-login");
		String senha = request.getParameter("campo-senha-login");
		
		AdministradorDao adminDao = new AdministradorDao();
		boolean flag = adminDao.validarCredenciais(login, senha);
				
		if(flag == true) {
			MuseuDao museuDao = new MuseuDao();
			List<Museu> museusList = museuDao.listar();
			HttpSession httpSession = request.getSession();
			
			httpSession.setMaxInactiveInterval(300); // 5 minutos.
			httpSession.setAttribute("status", true);
			httpSession.setAttribute("login", login);
			httpSession.setAttribute("museusList", museusList);
			
			return "index-administrativo.jsp";
		}
		
		return "acesso.jsp";
	}
}