package br.gasc.model;

public class Funcionario {
	private Integer id;
	private String nome;
	private String cpf;
	private Integer idMuseu;
	
	public Funcionario() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public Integer getIdMuseu() {
		return idMuseu;
	}
	
	public void setIdMuseu(Integer idMuseu) {
		this.idMuseu = idMuseu;
	}
	
}