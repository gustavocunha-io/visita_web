package br.gasc.model;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class Museu {
	private Integer id;
	private String nome;
	private Set<DayOfWeek> diasFuncionamento; 
	private LocalTime horarioAbertura;
	private LocalTime horarioFechamento;
	private static final Locale PT_BR = Locale.of("pt", "BR");
	
	public Museu() {
		this.diasFuncionamento = EnumSet.noneOf(DayOfWeek.class); 
	}

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

	public List<String> getDiasFuncionamento() {
		List<String> diasList = new ArrayList<String>();
		
		for (DayOfWeek dia : diasFuncionamento)
			diasList.add(dia.getDisplayName(TextStyle.FULL, PT_BR));
		return diasList;
	}

	public void setDiasFuncionamento(List<Integer> diasFuncionamento) {
		for (Integer dia : diasFuncionamento)
			this.diasFuncionamento.add(DayOfWeek.of(dia));
	}

	public LocalTime getHorarioAbertura() {
		return horarioAbertura;
	}

	public void setHorarioAbertura(LocalTime horarioAbertura) {
		this.horarioAbertura = horarioAbertura;
	}

	public LocalTime getHorarioFechamento() {
		return horarioFechamento;
	}

	public void setHorarioFechamento(LocalTime horarioFechamento) {
		this.horarioFechamento = horarioFechamento;
	}
	
}