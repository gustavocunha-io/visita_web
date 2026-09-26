package br.gasc.model;

import br.gasc.controller.SenhaService;

public class TestarHash {
	public static void main(String[] args) {
		String senha = SenhaService.hashSenha("049076@GG");
		
		System.out.println(senha);
	}
}
