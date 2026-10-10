package br.gasc.controller;

import java.time.LocalTime;
import java.util.List;

import br.gasc.service.MuseuService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CarregarHorarios implements Logica {
	private MuseuService museuService = new MuseuService();

	@Override
	public String executar(HttpServletRequest request, HttpServletResponse response) {
		String museuIdStr = request.getParameter("id-museu");
		
		if(museuIdStr != null) {
			Integer museuId = Integer.valueOf(museuIdStr);
			List<LocalTime> horariosList = museuService.gerarHorariosDisponiveis(museuId);
			
			request.setAttribute("horariosList", horariosList);
			request.setAttribute("museuSelecionado", museuId);
		}
		
		return "agendamento.jsp";
	}

}
