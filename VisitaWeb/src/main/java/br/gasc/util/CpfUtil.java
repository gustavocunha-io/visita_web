package br.gasc.util;

public class CpfUtil {
	private static final String REGEX = "^(\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{11})$";
	private static final String DELIMITADORES = "[.-]";
	
	/**
	 * Esse método tem como objetivo auxiliar na formatação dos cpf's que forem inseridos pelo usuário
	 * de modo que sera removido quaisquer caracteres como <strong>( . )</strong> e/ou <strong>( - )</strong>
	 * aos quais não são dígitos para que seja armazenado no banco de dados os estritamente os 11 dígitos que
	 * compões o cpf, além disso, também é utilizado expressão regular para garantir que a string fornecida
	 * contenha 11 digitos.
	 * 
	 * @param cpf - string contendo o cpf não formatado 
	 * @return retorna o cpf apenas com os dígitos
	 */
	public static String formatarCpf(String cpf) {
		if(cpf == null)
			return "";
		
		String cpfFormatado = cpf.trim();
		
		if(cpf.matches(REGEX))
			return cpfFormatado.replaceAll(DELIMITADORES, "");
		
		return "";
	}

}