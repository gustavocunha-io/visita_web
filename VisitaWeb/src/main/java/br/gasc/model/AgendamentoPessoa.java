package br.gasc.model;

public class AgendamentoPessoa {
	private Integer id;
	private Integer idPessoa;
	private Integer idAgendamento;
	private Integer tipoIngresso;
	private boolean compareceu;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getIdPessoa() {
		return idPessoa;
	}
	
	public void setIdPessoa(Integer idPessoa) {
		this.idPessoa = idPessoa;
	}
	
	public Integer getIdAgendamento() {
		return idAgendamento;
	}
	
	public void setIdAgendamento(Integer idAgendamento) {
		this.idAgendamento = idAgendamento;
	}
	
	public Integer getTipoIngresso() {
		return tipoIngresso;
	}
	
	public void setTipoIngresso(Integer tipoIngresso) {
		this.tipoIngresso = tipoIngresso;
	}
	
	public boolean isCompareceu() {
		return compareceu;
	}
	
	public void setCompareceu(boolean compareceu) {
		this.compareceu = compareceu;
	}
	
}