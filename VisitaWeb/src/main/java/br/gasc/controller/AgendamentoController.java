package br.gasc.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import br.gasc.service.EmailService;
import br.gasc.service.MuseuService;
import br.gasc.service.SenhaService;
import br.gasc.util.DataUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AgendamentoController implements Logica {

	@Override
	public String executar(HttpServletRequest request, HttpServletResponse response) {
		String acao = request.getParameter("acao");
		String idMuseuStr = request.getParameter("id-museu");
		MuseuService museuService = new MuseuService();
		MuseuDao museuDao = new MuseuDao();
				
		if("definirData".equals(acao)) {
			Integer idMuseu = Integer.valueOf(idMuseuStr);
			Museu museu = museuService.obterMuseuPorId(idMuseu);
			
			String dataAgendamentoStr = request.getParameter("data-agendamento");
			String horarioAgendamentoStr = request.getParameter("horario-agendamento");
			
			if(dataAgendamentoStr != null && horarioAgendamentoStr != null) {
				LocalDate dataAgendamento = LocalDate.parse(dataAgendamentoStr);
				LocalTime horarioAgendamento = LocalTime.parse(horarioAgendamentoStr);
				Set<DayOfWeek> diasFuncionamento = museu.getDiasFuncionamento();
				LocalTime horarioAbertura = museu.getHorarioAbertura();
				LocalTime horarioFechamento = museu.getHorarioFechamento();
				LocalDateTime dataHora = LocalDateTime.of(dataAgendamento, horarioAgendamento);
				
				int numeroTotalVagas = museu.getNumeroTotalVagas();
				int numeroPessoasAgendamento = museuDao.buscarVagasOcupadas(idMuseu, dataHora);
				int vagasRestantes = numeroTotalVagas - numeroPessoasAgendamento;
				
				boolean isDiaValido = diasFuncionamento.contains(dataAgendamento.getDayOfWeek());
				boolean isHorarioValido = !horarioAgendamento.isBefore(horarioAbertura) && !horarioAgendamento.isAfter(horarioFechamento);
				
				if(isDiaValido && isHorarioValido) {					
					if(vagasRestantes > 0) {
						request.setAttribute("status", true);
						request.setAttribute("vagasRestantes", vagasRestantes);
						request.setAttribute("museu", museu);
						request.setAttribute("dataAgendamento", dataAgendamento);
						request.setAttribute("horarioAgendamento", horarioAgendamento);
						
						return "dados-usuario.jsp";
					} else {
						request.setAttribute("status", false);
						request.setAttribute("vagasRestantes", "Nenhuma vaga disponível nessa data e nesse horário");
						return "data-agendamento.jsp";
					}
				} else {
					request.setAttribute("status", false);
	                request.setAttribute("vagasRestantes", "O museu não está aberto nesta data/horário.");
	                
	                return "data-agendamento.jsp";
				}
			}
		}
		
		if("confirmarNumeroPessoas".equals(acao)) {
			String numeroPessoasStr = request.getParameter("numero-pessoas");
			String dataAgendamentoStr = request.getParameter("data-agendamento");
			String horarioAgendamentoStr = request.getParameter("horario-agendamento");
			
			if(numeroPessoasStr != null && idMuseuStr != null) {
				int numeroPessoas = Integer.valueOf(numeroPessoasStr);
				int idMuseu = Integer.valueOf(idMuseuStr);
				Museu museu = museuService.obterMuseuPorId(idMuseu);
				IngressoDao ingressoDao = new IngressoDao();
				List<Ingresso> ingressosList = ingressoDao.listar();
				
				request.setAttribute("numeroPessoas", numeroPessoas);
				request.setAttribute("museu", museu);
				request.setAttribute("idMuseuSelecionado", idMuseuStr);
				request.setAttribute("dataSelecionada", dataAgendamentoStr);
				request.setAttribute("horarioSelecionado", horarioAgendamentoStr);
				request.setAttribute("ingressosList", ingressosList);
				
				return "dados-usuario.jsp";
			}
		}
		
		if("confirmarAgendamento".equals(acao)) {
			AgendamentoDao agendamentoDao = new AgendamentoDao();
			
			String idMuseuFinalStr = request.getParameter("id-museu");
			String dataFinalStr = request.getParameter("data-agendamento");
			String horarioFinalStr = request.getParameter("horario-agendamento");
			String numeroPessoasFinalStr = request.getParameter("numero-pessoas");
			
			List<String> cpfsList = Arrays.asList(request.getParameterValues("cpf-visitante"));
			List<String> nomesVisitantesList = Arrays.asList(request.getParameterValues("nome-visitante"));
			
			if (idMuseuFinalStr != null && dataFinalStr != null && horarioFinalStr != null) {
				Integer idMuseu = Integer.valueOf(idMuseuFinalStr);
				LocalDate dataAgendamento = LocalDate.parse(dataFinalStr);
				LocalTime horarioAgendamento = LocalTime.parse(horarioFinalStr);
				LocalDateTime dataHora = LocalDateTime.of(dataAgendamento, horarioAgendamento);
				int numeroPessoasAgendamento = Integer.valueOf(numeroPessoasFinalStr);
				String emailResponsavel = request.getParameter("email-responsavel");
				boolean termoConsentimento = Boolean.valueOf(request.getParameter("termo-responsabilidade"));
				String codigoConfirmacao = SenhaService.hashSenha(DataUtil.converterDataHoraToString(dataHora));
				String idIngressoStr = request.getParameter("ingresso-selecionado");
				
				Agendamento agendamento = new Agendamento();
				
				agendamento.setDataHoraInicio(dataHora);
				agendamento.setNumeroPessoas(Integer.valueOf(numeroPessoasFinalStr));
				agendamento.setEmailResponsavel(emailResponsavel);
				agendamento.setTermoConsentimento(termoConsentimento);
				agendamento.setCodigoConfimacao(codigoConfirmacao);
				agendamento.setIdMuseu(idMuseu);
				
				agendamento = agendamentoDao.inserir(agendamento);
				
				if(agendamento.getId() != null) {
					AgendamentoPessoaDao agendamentoPessoaDao = new AgendamentoPessoaDao();
					AgendamentoPessoa agendamentoPessoa = new AgendamentoPessoa();
					PessoaDao pessoaDao = new PessoaDao();
					List<Pessoa> pessoasList = new ArrayList<Pessoa>();
					Integer idIngresso = Integer.getInteger(idIngressoStr);
					
					for(int indice = 0; indice < numeroPessoasAgendamento; indice++) {
						String cpfVisitante = cpfsList.get(indice);
						String nomeVisitante = nomesVisitantesList.get(indice);	
						Optional<Pessoa> pessoaOpt = pessoaDao.pesquisar(cpfVisitante);
						Pessoa pessoa = new Pessoa();
						
						if(pessoaOpt.isPresent())
							pessoa = pessoaOpt.get();
						else {
							pessoa.setCpf(cpfVisitante);
							pessoa.setNome(nomeVisitante);
							pessoa = pessoaDao.inserir(pessoa);
						}
						
						agendamentoPessoa.setIdPessoa(pessoa.getId());
						agendamentoPessoa.setIdAgendamento(agendamento.getId());
						agendamentoPessoa.setCompareceu(false);
						agendamentoPessoa.setTipoIngresso(idIngresso);
						agendamentoPessoaDao.inserir(agendamentoPessoa);
						
						pessoasList.add(pessoa);
					}
					
					StringBuffer mensagemBuffer = new StringBuffer();
					
					mensagemBuffer.append("<h2>Confirmação de agendamento</h2>");
					mensagemBuffer.append("<p>Seu agendamento foi realizado com sucesso!</p>");
					mensagemBuffer.append("<hr>");
					mensagemBuffer.append(String.format("<p><strong>Horario:</strong> %s</p>", DataUtil.converterDataHoraToString(dataHora)));
					mensagemBuffer.append(String.format("<p><strong>Número de Visitantes:</strong> %d</p>", numeroPessoasAgendamento));
					
					for(Pessoa pessoa : pessoasList)
						mensagemBuffer.append(String.format("<p><pre>\t%s - (CPF: %s)</pre></p>", pessoa.getNome(), pessoa.getCpf()));
					
					mensagemBuffer.append(String.format("<p><strong>Codigo de confirmação:</strong> %s</p>", codigoConfirmacao));
					mensagemBuffer.append("<hr>");
			        mensagemBuffer.append("<p><em>Apresente um documento com foto na recepção do museu.</em></p>");
					
					EmailService emailService = new EmailService();
					
					// emailService.sendEmail(emailResponsavel, "Sistema GASC - Agendamento Museu", mensagemBuffer);
					
					request.setAttribute("mensagemSucesso", "Agendamento realizado com sucesso para " + dataFinalStr);
				}
				
				request.setAttribute("mensagemSucesso", "Agendamento não realizado!");
			}
			
		}
		
		return "index.jsp";
	}
}