package br.gasc.dao;

import java.util.List;
import java.util.Optional;

/*
 * Representa as operações CRUD de uma sistema de
 */
public interface Dao <T, K> {
	// Permite realizar inserações banco de dados de um determinado dado.
	boolean inserir(T t);
	
	// Permite realizar alterações no banco de dados de acordo com o identificador id.
	boolean alterar(T t);
	
	// Permite realizar remoções no banco de dados de acordo com o identificador id.
	boolean deletar(K id);
	
	// Permite realizar pesquisa no banco de dados de acordo com o indentificador id.
	Optional<T> pesquisar(K id);
	
	// Permite listar todos os dados de uma respectiva tabela do banco de dados.
	List<T> listar();
}
