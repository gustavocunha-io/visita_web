package br.gasc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.gasc.dto.FabricaConexoes;
import br.gasc.model.Pessoa;

public class PessoaDao implements Dao<Pessoa, Integer> {
	private Connection connection;
	
	public PessoaDao() {
		connection = FabricaConexoes.getConnection();
	}
	
	@Override
	public boolean inserir(Pessoa pessoa) {
		final String query = "INSERT INTO (nome, cpf, email) VALUES (?, ?, ?)";
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, pessoa.getNome());
			preparedStatement.setString(2, pessoa.getCpf());
			preparedStatement.setString(3, pessoa.getEmail());
			
			return preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean alterar(Pessoa pessoa) {
		final String query = "UPDATE pessoa SET nome=?, cpf=?, email=? WHERE id=?";
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, pessoa.getNome());
			preparedStatement.setString(2, pessoa.getCpf());
			preparedStatement.setString(3, pessoa.getEmail());
			preparedStatement.setInt(4, pessoa.getId());
			
			return preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean deletar(Integer id) {
		String query = "DELETE FROM pessoa WHERE id=?";
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			return preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public Optional<Pessoa> pesquisar(Integer id) {
		String query = "SELECT * FROM pessoa WHERE id=?";
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			Pessoa pessoa = new Pessoa();
				
			pessoa.setId(resultSet.getInt("id"));
			pessoa.setNome(resultSet.getString("nome"));
			pessoa.setCpf(resultSet.getString("cpf"));
			pessoa.setEmail(resultSet.getString("email"));
				
			return Optional.of(pessoa);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return Optional.empty();
	}

	@Override
	public List<Pessoa> listar() {
		final String query = "SELECT * FROM pessoa";
		List<Pessoa> pessoasList = new ArrayList<Pessoa>();
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				Pessoa pessoa = new Pessoa();
				
				pessoa.setNome(resultSet.getString("nome"));
				pessoa.setCpf(resultSet.getString("cpf"));
				pessoa.setEmail(resultSet.getString("email"));
				
				pessoasList.add(pessoa);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return pessoasList;
	}
	
	public static void main(String[] args) {
		PessoaDao pessoaDao = new PessoaDao();
		List<Pessoa> pessoasList = pessoaDao.listar();
		
		for(Pessoa pessoa : pessoasList)
			System.out.println(pessoa);
	}

}