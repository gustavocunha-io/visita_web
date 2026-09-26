package br.gasc.service;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class SenhaService {
	private static final int SALTOS = 10;
	
	public static String hashSenha(String senhaPura) {
		return BCrypt.withDefaults().hashToString(SALTOS, senhaPura.toCharArray());
	}
	
	public static boolean varificarSenha(String senhaPura, String senhaHashBanco) {
		return BCrypt.verifyer().verify(senhaPura.toCharArray(), senhaHashBanco).verified;
	}
}