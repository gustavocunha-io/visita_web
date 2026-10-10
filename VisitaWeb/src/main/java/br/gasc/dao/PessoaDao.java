package br.gasc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	public Pessoa inserir(Pessoa pessoa) {
		final String query = "INSERT INTO pessoa (nome, cpf) VALUES (?, ?)";
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, pessoa.getNome());
			preparedStatement.setString(2, pessoa.getCpf());
			preparedStatement.executeUpdate();
			
			try (ResultSet resultSet = preparedStatement.getGeneratedKeys()){
				if(resultSet.next())
					pessoa.setId(resultSet.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return pessoa;
	}

	@Override
	public boolean alterar(Pessoa pessoa) {
		final String query = "UPDATE pessoa SET nome=?, cpf=? WHERE id=?";
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, pessoa.getNome());
			preparedStatement.setString(2, pessoa.getCpf());
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
			preparedStatement.setInt(1, id);
			
			if(preparedStatement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public Optional<Pessoa> pesquisar(Integer id) {
		String query = "SELECT * FROM pessoa WHERE id=?";
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				Pessoa pessoa = new Pessoa();
					
				pessoa.setId(resultSet.getInt("id"));
				pessoa.setNome(resultSet.getString("nome"));
				pessoa.setCpf(resultSet.getString("cpf"));
					
				return Optional.of(pessoa);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return Optional.empty();
	}
	
	public Optional<Pessoa> pesquisar(String cpf) {
		String query = "SELECT * FROM pessoa WHERE cpf=?";
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, cpf);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				Pessoa pessoa = new Pessoa();
					
				pessoa.setId(resultSet.getInt("id"));
				pessoa.setNome(resultSet.getString("nome"));
				pessoa.setCpf(resultSet.getString("cpf"));
					
				return Optional.of(pessoa);
			}
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
				
				pessoasList.add(pessoa);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return pessoasList;
	}

}