package br.gasc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import br.gasc.dto.FabricaConexoes;
import br.gasc.model.Funcionario;

public class FuncionarioDao implements Dao<Funcionario, Integer> {
	private Connection connection;
	
	public FuncionarioDao() {
		connection = FabricaConexoes.getConnection();
	}

	@Override
	public Funcionario inserir(Funcionario funcionario) {
		String query = "INSERT INTO funcionario (nome, cpf, id_museu) VALUES (?, ?, ?)";
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, funcionario.getNome());
			preparedStatement.setString(2, funcionario.getCpf());
			preparedStatement.setInt(3, funcionario.getIdMuseu());
			
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

	@Override
	public List<Funcionario> listar() {
		return null;
	}

}
