package br.gasc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import br.gasc.db.FabricaConexoes;
import br.gasc.model.Funcionario;
import br.gasc.service.SenhaService;

public class FuncionarioDao implements Dao<Funcionario, Integer> {
	private Connection connection;
	
	public FuncionarioDao() {
		connection = FabricaConexoes.getConnection();
	}

	@Override
	public Funcionario inserir(Funcionario funcionario) {
		String query = "INSERT INTO funcionario (nome, cpf, senha, id_museu) VALUES (?, ?, ?, ?)";
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, funcionario.getNome());
			preparedStatement.setString(2, funcionario.getCpf());
			preparedStatement.setString(3, SenhaService.hashSenha(funcionario.getSenha()));
			preparedStatement.setInt(4, funcionario.getIdMuseu());
			
			if(preparedStatement.executeUpdate() > 0) {
				try(ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
					if(resultSet.next())
						funcionario.setId(resultSet.getInt(1));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return funcionario;
	}

	@Override
	public boolean alterar(Funcionario funcionario) {
		return false;
	}

	@Override
	public boolean deletar(Integer id) {
		return false;
	}

	@Override
	public Optional<Funcionario> pesquisar(Integer id) {
		return Optional.empty();
	}
	
	public Optional<Funcionario> pesquisar(String cpf) {
		String query = "SELECT * FROM funcionario WHERE cpf=?";
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, cpf);
			
			try(ResultSet resultSet = preparedStatement.executeQuery()) {
				if(resultSet.next()) {
					Funcionario funcionario = new Funcionario();
					
					funcionario.setId(resultSet.getInt("id"));
					funcionario.setNome(resultSet.getString("nome"));
					funcionario.setCpf(resultSet.getString("cpf"));
					funcionario.setSenha(resultSet.getString("senha"));
					funcionario.setIdMuseu(resultSet.getInt("id_museu"));
					
					return Optional.of(funcionario);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return Optional.empty();
	}

	@Override
	public List<Funcionario> listar() {
		return null;
	}
	
	public boolean validarCredenciais(String login, String senha) {
		String query = "SELECT senha FROM funcionario WHERE cpf=?";
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, login);
			
			try(ResultSet resultSet = preparedStatement.executeQuery()) {
				if(resultSet.next())
					return SenhaService.varificarSenha(senha, resultSet.getString("senha"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

}
