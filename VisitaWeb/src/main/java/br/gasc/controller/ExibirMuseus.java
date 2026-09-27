package br.gasc.controller;

import java.util.List;

import br.gasc.dao.MuseuDao;
import br.gasc.model.Museu;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ExibirMuseus implements Logica {
	
	@Override
	public String executar(HttpServletRequest request, HttpServletResponse response) {
		MuseuDao museuDAO = new MuseuDao();
		List<Museu> museusList = museuDAO.listar();
		
		request.setAttribute("museusList", museusList);
		
		return "agendamento.jsp";
	}

}
