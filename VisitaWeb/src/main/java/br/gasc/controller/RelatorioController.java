package br.gasc.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import br.gasc.dao.AgendamentoDao;
import br.gasc.dao.AgendamentoPessoaDao;
import br.gasc.dao.IngressoDao;
import br.gasc.dao.MuseuDao;
import br.gasc.dao.PessoaDao;
import br.gasc.model.Agendamento;
import br.gasc.model.AgendamentoPessoa;
import br.gasc.model.Ingresso;
import br.gasc.model.Museu;
import br.gasc.model.Pessoa;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RelatorioController implements Logica {

	@Override
	public String executar(HttpServletRequest request, HttpServletResponse response) {
		String tipoRelatorio = request.getParameter("tipo-relatorio");
		
		if (tipoRelatorio != null) {
			AgendamentoDao agendamentoDao = new AgendamentoDao();
			AgendamentoPessoaDao agendamentoPessoaDao = new AgendamentoPessoaDao();
			PessoaDao pessoaDao = new PessoaDao();
			MuseuDao museuDao = new MuseuDao();
			IngressoDao tipoIngressoDao = new IngressoDao();
			
			List<Map<String, Object>> relatorioList = new ArrayList<>();
			
			// RELATÓRIO POR DIA
			if ("porDia".equals(tipoRelatorio)) {
				String dataStr = request.getParameter("dataFiltro");
				if (dataStr != null && !dataStr.isEmpty()) {
					LocalDate dataFiltro = LocalDate.parse(dataStr);
					
					List<Agendamento> agendamentosList = agendamentoDao.listar();
					
					for (Agendamento agendamento : agendamentosList) {
						LocalDate dataAgendamento = agendamento.getDataHoraInicio().toLocalDate();
						
						if (dataAgendamento.equals(dataFiltro)) {
							List<AgendamentoPessoa> pessoasDoAgendamento = agendamentoPessoaDao.obterPessoasAgendamento(agendamento.getId());
							
							for (AgendamentoPessoa agendamentoPessoa : pessoasDoAgendamento) {
								Optional<Pessoa> pessoaOpt = pessoaDao.pesquisar(agendamentoPessoa.getIdPessoa());
								Optional<Ingresso> ingressoOpt = tipoIngressoDao.pesquisar(agendamentoPessoa.getTipoIngresso());
								Optional<Museu> museuOpt = museuDao.pesquisar(agendamento.getIdMuseu());
								
								if (pessoaOpt.isPresent()) {
									Pessoa pessoa = pessoaOpt.get();
									
									Map<String, Object> linha = new HashMap<>();
									linha.put("nomePessoa", pessoa.getNome());
									linha.put("cpf", pessoa.getCpf());
									linha.put("nomeMuseu", museuOpt.isPresent() ? museuOpt.get().getNome() : "Desconhecido");
									linha.put("dataHora", agendamento.getDataHoraInicio());
									linha.put("tipoIngresso", ingressoOpt.isPresent() ? ingressoOpt.get().getTipo() : "Desconhecido");
									linha.put("preco", ingressoOpt.isPresent() ? ingressoOpt.get().getPreco() : 0.0);
									linha.put("compareceu", agendamentoPessoa.isCompareceu());
									
									relatorioList.add(linha);
								}
							}
						}
					}
					request.setAttribute("tituloRelatorio", "Relatório por Dia: " + dataFiltro.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
				}
			}
			
			else if ("porDataHora".equals(tipoRelatorio)) {
				String dataHoraStr = request.getParameter("dataHoraFiltro"); 
				
				if (dataHoraStr != null && !dataHoraStr.isEmpty()) {
					LocalDateTime dataHoraFiltro = LocalDateTime.parse(dataHoraStr);
					
					List<Agendamento> agendamentosList = agendamentoDao.listar();
					
					for (Agendamento agendamento : agendamentosList) {
						if (agendamento.getDataHoraInicio().equals(dataHoraFiltro)) {
							List<AgendamentoPessoa> pessoasDoAgendamento = agendamentoPessoaDao.obterPessoasAgendamento(agendamento.getId());
							
							for (AgendamentoPessoa agendamentoPessoa : pessoasDoAgendamento) {
								Optional<Pessoa> pessoaOpt = pessoaDao.pesquisar(agendamentoPessoa.getIdPessoa());
								Optional<Ingresso> ingressoOpt = tipoIngressoDao.pesquisar(agendamentoPessoa.getTipoIngresso());
								Optional<Museu> museuOpt = museuDao.pesquisar(agendamento.getIdMuseu());
								
								if (pessoaOpt.isPresent()) {
									Pessoa pessoa = pessoaOpt.get();
									
									Map<String, Object> linha = new HashMap<>();
									linha.put("nomePessoa", pessoa.getNome());
									linha.put("cpf", pessoa.getCpf());
									linha.put("nomeMuseu", museuOpt.isPresent() ? museuOpt.get().getNome() : "Desconhecido");
									linha.put("dataHora", agendamento.getDataHoraInicio());
									linha.put("tipoIngresso", ingressoOpt.isPresent() ? ingressoOpt.get().getTipo() : "Desconhecido");
									linha.put("preco", ingressoOpt.isPresent() ? ingressoOpt.get().getPreco() : 0.0);
									linha.put("compareceu", agendamentoPessoa.isCompareceu());
									
									relatorioList.add(linha);
								}
							}
						}
					}
					request.setAttribute("tituloRelatorio", "Relatório por Data e Hora: " + dataHoraFiltro.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
				}
			}
			
			request.setAttribute("relatorio", relatorioList);
		}
		
		return "relatorio.jsp";
	}
}