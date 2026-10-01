package br.gasc.model;

import br.gasc.service.SenhaService;

public class TestarHash {
	public static void main(String[] args) {
		String senha = SenhaService.hashSenha("");
		
		System.out.println(senha);
	}
}
