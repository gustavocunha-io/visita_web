package br.gasc.model;

import java.math.BigDecimal;

public class Ingresso {
	private Integer id;
	private String tipo;
	private BigDecimal preco;
	
	public Ingresso() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	
}