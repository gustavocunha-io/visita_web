package br.gasc.controller;

import java.util.List;

import br.gasc.dao.MuseuDAO;
import br.gasc.model.Museu;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ExibirMuseus implements Logica {
	
	@Override
	public String executar(HttpServletRequest request, HttpServletResponse response) {
		MuseuDAO museuDAO = new MuseuDAO();
		List<Museu> museusList = museuDAO.listarMuseus();
		
		request.setAttribute("museusList", museusList);
		
		return "agendamento.jsp";
	}

}
