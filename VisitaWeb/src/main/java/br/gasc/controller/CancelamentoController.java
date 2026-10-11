package br.gasc.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import br.gasc.dao.AgendamentoDao;
import br.gasc.dao.AgendamentoPessoaDao;
import br.gasc.dao.PessoaDao;
import br.gasc.model.Agendamento;
import br.gasc.model.AgendamentoPessoa;
import br.gasc.model.Pessoa;
import br.gasc.service.EmailService;
import br.gasc.util.DataUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CancelamentoController implements Logica {

	@Override
	public String executar(HttpServletRequest request, HttpServletResponse response) {
		AgendamentoDao agendamentoDao = new AgendamentoDao();
		PessoaDao pessoaDao = new PessoaDao();
		String acao = request.getParameter("acao");
		
		if("buscarAgendamento".equals(acao)) {
			String codigoConfimacao = request.getParameter("codigo-confirmacao");
			String email = request.getParameter("email-responsavel");
			
			if(!codigoConfimacao.isEmpty() && !email.isEmpty()) {
				Optional<Agendamento> agendamentoOpt = agendamentoDao.pesquisar(codigoConfimacao, email);
				
				if(agendamentoOpt.isPresent()) {
					Agendamento agendamento = agendamentoOpt.get();
					AgendamentoPessoaDao agendamentoPessoaDao = new AgendamentoPessoaDao();
					List<AgendamentoPessoa> agendamentoPessoasList = agendamentoPessoaDao.obterPessoasAgendamento(agendamento.getId());					
					List<Pessoa> pessoasList = new ArrayList<Pessoa>();
					
					request.setAttribute("idAgendamento", agendamento.getId());
					
					for (AgendamentoPessoa agendamentoPessoa : agendamentoPessoasList) {
						Optional<Pessoa> pessoaOpt = pessoaDao.pesquisar(agendamentoPessoa.getIdPessoa());
						
						if(pessoaOpt.isPresent())
							pessoasList.add(pessoaOpt.get());
					}
					
					request.setAttribute("pessoasList", pessoasList);
				}
				
				return "cancelamento.jsp";
			}
		}
		
		if("removerAgendamentoPessoa".equals(acao)) {
			String botaoExcluir = request.getParameter("botao-excluir");
			String idAgendamentoStr = request.getParameter("id-agendamento");
			
			if(idAgendamentoStr != null) {
				Integer idAgendamento = Integer.valueOf(idAgendamentoStr);
				Optional<Agendamento> agendamentoOpt = agendamentoDao.pesquisar(idAgendamento);
				Agendamento agendamento = agendamentoOpt.get();
				
				if("Excluir".equals(botaoExcluir)) {
					List<String> idPessoasList = Arrays.asList(request.getParameterValues("botao-cancelar"));
					
					for(String id : idPessoasList)
						pessoaDao.deletar(Integer.valueOf(id));
					
					agendamento.setNumeroPessoas(agendamento.getNumeroPessoas() - idPessoasList.size());
					agendamentoDao.alterar(agendamento);
				} else if ("Excluir tudo".equals(botaoExcluir))
					agendamentoDao.deletar(agendamento.getId());
				
				LocalDateTime dataHoraAgendamento = agendamento.getDataHoraInicio();
				int numeroPessoasAgendamento = agendamento.getNumeroPessoas();
				String codigoConfirmacao = agendamento.getCodigoConfimacao();
					
				StringBuffer mensagemBuffer = new StringBuffer();
					
				mensagemBuffer.append("<h2>Confirmação de agendamento</h2>");
				mensagemBuffer.append("<p>Seu agendamento foi realizado com sucesso!</p>");
				mensagemBuffer.append("<hr>");
				mensagemBuffer.append(String.format("<p><strong>Horario:</strong> %s</p>", DataUtil.converterDataHoraToString(dataHoraAgendamento)));
				mensagemBuffer.append(String.format("<p><strong>Número de Visitantes:</strong> %d</p>", numeroPessoasAgendamento));
					
				AgendamentoPessoaDao agendamentoPessoaDao = new AgendamentoPessoaDao();
				List<AgendamentoPessoa> agendamentoPessoasList = agendamentoPessoaDao.obterPessoasAgendamento(idAgendamento);
				List<Pessoa> pessoasList = new ArrayList<Pessoa>();
					
				for(AgendamentoPessoa agendamentoPessoa : agendamentoPessoasList) {
					Optional<Pessoa> pessoaOpt = pessoaDao.pesquisar(agendamentoPessoa.getIdPessoa());
						
					if(pessoaOpt.isPresent())
						pessoasList.add(pessoaOpt.get());
				}
					
				for(Pessoa pessoa : pessoasList)
					mensagemBuffer.append(String.format("<p><pre>\t%s - (CPF: %s)</pre></p>", pessoa.getNome(), pessoa.getCpf()));
					
				mensagemBuffer.append(String.format("<p><strong>Codigo de confirmação:</strong> %s</p>", codigoConfirmacao));
				mensagemBuffer.append("<hr>");
			    mensagemBuffer.append("<p><em>Apresente um documento com foto na recepção do museu.</em></p>");
					
//			    EmailService emailService = new EmailService();
//			    emailService.sendEmail(agendamento.getEmailResponsavel(), "Sistema GASC - Alteração no Agendamento", mensagemBuffer);
					
				return "index.jsp";
			}
			
			return "cancelamento.jsp";
		}
		
		return "index.jsp";
	}

}
