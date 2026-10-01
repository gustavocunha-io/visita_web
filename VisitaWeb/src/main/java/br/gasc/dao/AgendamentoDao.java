package br.gasc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.gasc.dto.FabricaConexoes;
import br.gasc.model.Agendamento;

public class AgendamentoDao implements Dao<Agendamento, Integer> {
	private Connection connection;
	
	public AgendamentoDao() {
		this.connection = FabricaConexoes.getConnection();
	}

	@Override
	public boolean inserir(Agendamento agendamento) {
		String query = "INSERT INTO agendamento VALUES (?, ?, ?)";
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			if(agendamento != null) {
				preparedStatement.setTimestamp(1, Timestamp.valueOf(agendamento.getDataHoraInicio()));
				preparedStatement.setInt(2, agendamento.getNumeroPessoas());
				preparedStatement.setBoolean(1, agendamento.getTermosResponsabilidade());
				preparedStatement.execute();
				
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean alterar(Agendamento agendamento) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deletar(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Optional<Agendamento> pesquisar(Integer id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<Agendamento> listar() {
		String query = "SELECT * FROM agendamento";
		List<Agendamento> agendamentosList = new ArrayList<Agendamento>();
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				Agendamento agendamento = new Agendamento();
				
				agendamento.setId(resultSet.getInt("id"));
				agendamento.setDataHoraInicio(resultSet.getTimestamp("data_hora").toLocalDateTime());
				agendamento.setNumeroPessoas(resultSet.getInt("numero_pessoas"));
				agendamento.setTermosResponsabilidade(resultSet.getBoolean("termos_responsabilidade"));
				
				agendamentosList.add(agendamento);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return agendamentosList;
	}
	
	private int obterMaximoPessoaMuseu() {
		return 1;
	}

}