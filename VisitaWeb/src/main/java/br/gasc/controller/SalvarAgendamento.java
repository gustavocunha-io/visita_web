package br.gasc.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Set;

import br.gasc.dao.MuseuDao;
import br.gasc.model.Museu;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SalvarAgendamento implements Logica {

	@Override
	public String executar(HttpServletRequest request, HttpServletResponse response) {
		String idMuseuStr = request.getParameter("id-museu");
		String dataAgendamentoStr = request.getParameter("data-agendamento");
		String horarioAgendamentoStr = request.getParameter("horario-agendamento");
		MuseuDao museuDao = new MuseuDao();
		
		if(idMuseuStr == null || idMuseuStr.isEmpty() || dataAgendamentoStr.isEmpty() || horarioAgendamentoStr.isEmpty()) {
			request.setAttribute("status", false);
		} else {
			Integer idMuseu = Integer.valueOf(idMuseuStr);
			LocalDate dataAgendamento = LocalDate.parse(dataAgendamentoStr);
			LocalTime horarioAgendamento = LocalTime.parse(horarioAgendamentoStr);
			Optional<Museu> museuOpt = museuDao.pesquisar(idMuseu);
			
			if(museuOpt.isPresent()) {
				Museu museu = museuOpt.get();
				LocalTime horarioAbertura = museu.getHorarioAbertura();
				LocalTime horarioFechamento = museu.getHorarioFechamento();
				Set<DayOfWeek> diasFuncionamento = museu.getDiasFuncionamento();
				
				if(diasFuncionamento.contains(dataAgendamento.getDayOfWeek()) &&
				  (horarioAgendamento.compareTo(horarioAbertura) >= 0 && horarioAgendamento.compareTo(horarioFechamento) <= 0))
					request.setAttribute("status", true);
				else
					request.setAttribute("status", false);		
			} else {
				request.setAttribute("status", false);
			}
		}
		
		request.setAttribute("museusList", museuDao.listar());
		
		return "agendamento.jsp";
	}

}
