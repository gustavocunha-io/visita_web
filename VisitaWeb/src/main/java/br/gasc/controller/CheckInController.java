package br.gasc.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import br.gasc.dao.AgendamentoDao;
import br.gasc.dao.AgendamentoPessoaDao;
import br.gasc.dao.IngressoDao;
import br.gasc.dao.PessoaDao;
import br.gasc.model.Agendamento;
import br.gasc.model.AgendamentoPessoa;
import br.gasc.model.Ingresso;
import br.gasc.model.Pessoa;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CheckInController implements Logica {

	@Override
	public String executar(HttpServletRequest request, HttpServletResponse response) {
		String acao = request.getParameter("acao");
		
		if("buscarAgendamento".equals(acao)) {		
			String dataAgendamentoStr = request.getParameter("data-agendamento");
			String horarioAgendamentoStr = request.getParameter("horario-agendamento");
			String idMuseuFuncionarioStr = request.getParameter("id-museu");
			
			if(dataAgendamentoStr != null || horarioAgendamentoStr != null) {
				LocalDate dataAgendameno = LocalDate.parse(dataAgendamentoStr);
				LocalTime horarioAgendamento = LocalTime.parse(horarioAgendamentoStr);
				LocalDateTime dataHoraAgendamento = LocalDateTime.of(dataAgendameno, horarioAgendamento);
				Integer idMuseuFuncionario = Integer.valueOf(idMuseuFuncionarioStr); 
				
				AgendamentoDao agendamentoDao = new AgendamentoDao();
				Optional<Agendamento> agendamentoOpt = agendamentoDao.pesquisar(dataHoraAgendamento, idMuseuFuncionario);
				
				if(agendamentoOpt.isPresent()) {
					Agendamento agendamento = agendamentoOpt.get();
					AgendamentoPessoaDao agendamentoPessoaDao = new AgendamentoPessoaDao();
					PessoaDao pessoaDao = new PessoaDao();
					IngressoDao ingressoDao = new IngressoDao();
					List<AgendamentoPessoa> agendamentoPessoasList = agendamentoPessoaDao.obterPessoasAgendamento(agendamento.getId());
					List<Pessoa> pessoasList = new ArrayList<Pessoa>();
					List<Ingresso> ingressosList = ingressoDao.listar();
					
					
					for(AgendamentoPessoa agendamentoPessoa : agendamentoPessoasList) {
						Optional<Pessoa> pessoaOpt = pessoaDao.pesquisar(agendamentoPessoa.getIdPessoa());
						Optional<Ingresso> ingressoOpt = ingressoDao.pesquisar(agendamentoPessoa.getTipoIngresso());
			
						if(pessoaOpt.isPresent() && ingressoOpt.isPresent())
							pessoasList.add(pessoaOpt.get());
					}
					
					if(pessoasList.size() > 0) {
						request.setAttribute("pessoasList", pessoasList);
						request.setAttribute("agendamentoPessoasList", agendamentoPessoasList);
						request.setAttribute("ingressosList", ingressosList);
						request.setAttribute("idAgendamento", agendamento.getId());
						request.setAttribute("status", true);
						
						return "check-in.jsp";
					} else
						request.setAttribute("mensagem", "Erro: não foram encontradas pesssoas nesse agendamento!");
				} else
					request.setAttribute("mensagem", "Erro: não foi possível encontrar o agendamento");
			} else
				request.setAttribute("mensagem", "Erro: A data ou a hora do agendamento são inválidas!");
			
			request.setAttribute("status", false);
			return "check-in.jsp";
		}
		
		if("salvarCheckIn".equals(acao)) {
			List<String> compareceramList = Arrays.asList(request.getParameterValues("compareceu"));
			String idAgendamentoStr = request.getParameter("id-agendamento");
			
			if(compareceramList.size() > 0) {
				Integer idAgendamento = Integer.valueOf(idAgendamentoStr);
				AgendamentoPessoaDao agendamentoPessoaDao = new AgendamentoPessoaDao();
				
				System.out.println(idAgendamento);
				
				for(String compareceu : compareceramList)
					agendamentoPessoaDao.confirmarComparecimento(Integer.valueOf(compareceu), idAgendamento);
				
				request.setAttribute("mensagem", "Sucesso: O check-in foi realizado com sucesso");
				request.setAttribute("status", true);
			}
		}
		
		return "index-administrativo.jsp";
	}

}
