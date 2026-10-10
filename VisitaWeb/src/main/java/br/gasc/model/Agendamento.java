package br.gasc.model;

import java.time.LocalDateTime;

public class Agendamento {
	private Integer id;
	private LocalDateTime dataHoraInicio;
	private int numeroPessoas;
	private String emailResponsavel;
	private boolean termoConsentimento;
	private String codigoConfimacao;
	private Integer idMuseu;

	public Agendamento() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getDataHoraInicio() {
		return dataHoraInicio;
	}

	public void setDataHoraInicio(LocalDateTime dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}

	public LocalDateTime getDataHoraFim() {
		return (this.dataHoraInicio == null) ? null : this.dataHoraInicio.plusHours(1);
	}

	public int getNumeroPessoas() {
		return numeroPessoas;
	}

	public void setNumeroPessoas(int numeroPessoas) {
		this.numeroPessoas = numeroPessoas;
	}
	
	public String getEmailResponsavel() {
		return emailResponsavel;
	}

	public void setEmailResponsavel(String emailResponsavel) {
		this.emailResponsavel = emailResponsavel;
	}

	public boolean getTermoConsentimento() {
		return termoConsentimento;
	}

	public void setTermoConsentimento(boolean termosResponsabilidade) {
		this.termoConsentimento = termosResponsabilidade;
	}
	
	public String getCodigoConfimacao() {
		return codigoConfimacao;
	}

	public void setCodigoConfimacao(String codigoConfimacao) {
		this.codigoConfimacao = codigoConfimacao;
	}

	public Integer getIdMuseu() {
		return idMuseu;
	}

	public void setIdMuseu(Integer idMuseu) {
		this.idMuseu = idMuseu;
	}

}