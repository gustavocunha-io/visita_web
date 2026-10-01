package br.gasc.model;

import java.time.LocalDateTime;

public class Agendamento {
	private Integer id;
	private LocalDateTime dataHoraInicio;
	private int numeroPessoas;
	private boolean termosResponsabilidade;

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

	public boolean getTermosResponsabilidade() {
		return termosResponsabilidade;
	}

	public void setTermosResponsabilidade(boolean termosResponsabilidade) {
		this.termosResponsabilidade = termosResponsabilidade;
	}

}