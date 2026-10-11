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
import br.gasc.model.AgendamentoPessoa;

public class AgendamentoPessoaDao implements Dao<AgendamentoPessoa, Integer> {
	private Connection connection;

	public AgendamentoPessoaDao() {
		connection = FabricaConexoes.getConnection();
	}

	@Override
	public AgendamentoPessoa inserir(AgendamentoPessoa agendamentoPessoa) {
		String query = "INSERT INTO agendamento_pessoas (id_pessoa, id_agendamento, id_ingresso, compareceu) VALUES (?, ?, ?, ?)";
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			if(agendamentoPessoa != null) {
				preparedStatement.setInt(1, agendamentoPessoa.getIdPessoa());
				preparedStatement.setInt(2, agendamentoPessoa.getIdAgendamento());
				preparedStatement.setInt(3, agendamentoPessoa.getTipoIngresso());
				preparedStatement.setBoolean(4, agendamentoPessoa.isCompareceu());
				
				if(preparedStatement.executeUpdate() > 0) {
					try(ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
						if(resultSet.next())
							agendamentoPessoa.setId(resultSet.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return agendamentoPessoa;
	}

	@Override
	public boolean alterar(AgendamentoPessoa agendamentoPessoa) {
		return false;
	}

	@Override
	public boolean deletar(Integer id) {
		return false;
	}

	@Override
	public Optional<AgendamentoPessoa> pesquisar(Integer id) {
		return Optional.empty();
	}
	
	public List<AgendamentoPessoa> obterPessoasAgendamento(Integer idAgendamento) {
		String query = "SELECT * FROM agendamento_pessoas WHERE id_agendamento=?";
		List<AgendamentoPessoa> agendamentoPessoasList = new ArrayList<AgendamentoPessoa>();
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, idAgendamento);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				AgendamentoPessoa agendamentoPessoa = new AgendamentoPessoa();
				
				agendamentoPessoa.setId(resultSet.getInt("id"));
				agendamentoPessoa.setIdPessoa(resultSet.getInt("id_pessoa"));
				agendamentoPessoa.setIdAgendamento(resultSet.getInt("id_agendamento"));
				agendamentoPessoa.setCompareceu(false);
				agendamentoPessoa.setTipoIngresso(resultSet.getInt("id_ingresso"));
				
				agendamentoPessoasList.add(agendamentoPessoa);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return agendamentoPessoasList;
	}

	@Override
	public List<AgendamentoPessoa> listar() {
		return null;
	}

}
