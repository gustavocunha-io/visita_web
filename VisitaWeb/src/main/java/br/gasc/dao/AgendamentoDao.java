package br.gasc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
	public Agendamento inserir(Agendamento agendamento) {
		String query = "INSERT INTO agendamento (data_hora, numero_pessoas, email_responsavel, termo_consentimento,"
				+ "codigo_confirmacao, id_museu) VALUES (?, ?, ?, ?, ?, ?)";
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			if(agendamento != null) {
				MuseuDao museuDao = new MuseuDao();
				int numeroMaximoPessoasMuseu = museuDao.obterMaximoPessoaMuseu(agendamento.getIdMuseu());
				int numeroPessoasAgendamento = agendamento.getNumeroPessoas(); 
				
				if(numeroPessoasAgendamento <= numeroMaximoPessoasMuseu && numeroMaximoPessoasMuseu > 0) {
					preparedStatement.setTimestamp(1, Timestamp.valueOf(agendamento.getDataHoraInicio()));
					preparedStatement.setInt(2, agendamento.getNumeroPessoas());
					preparedStatement.setString(3, agendamento.getEmailResponsavel());
					preparedStatement.setBoolean(4, agendamento.getTermoConsentimento());
					preparedStatement.setString(5, agendamento.getCodigoConfimacao());
					preparedStatement.setInt(6, agendamento.getIdMuseu());
					
					if(preparedStatement.executeUpdate() > 0) {
						try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
							if(resultSet.next())
								agendamento.setId(resultSet.getInt(1));
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return agendamento;
	}

	@Override
	public boolean alterar(Agendamento agendamento) {
		String query = "UPDATE agendamento SET data_hora=?, numero_pessoas=?, "
				+ "email_responsavel=?, termo_consentimento=?, "
				+ "codigo_confirmacao=? WHERE id=?";
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setTimestamp(1, Timestamp.valueOf(agendamento.getDataHoraInicio()));
			preparedStatement.setInt(2, agendamento.getNumeroPessoas());
			preparedStatement.setString(3, agendamento.getEmailResponsavel());
			preparedStatement.setBoolean(4,	agendamento.getTermoConsentimento());
			preparedStatement.setString(5, agendamento.getCodigoConfimacao());
			preparedStatement.setInt(6, agendamento.getId());
			
			if(preparedStatement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean deletar(Integer id) {
		String query = "DELETE FROM agendamento WHERE id=?";
		
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
	public Optional<Agendamento> pesquisar(Integer id) {
		String query = "SELECT * FROM agendamento WHERE id=?";
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				Agendamento agendamento = new Agendamento();
				
				agendamento.setId(resultSet.getInt("id"));
				agendamento.setDataHoraInicio(resultSet.getTimestamp("data_hora").toLocalDateTime());
				agendamento.setNumeroPessoas(resultSet.getInt("numero_pessoas"));
				agendamento.setEmailResponsavel(resultSet.getString("email_responsavel"));
				agendamento.setTermoConsentimento(resultSet.getBoolean("termo_consentimento"));
				agendamento.setCodigoConfimacao(resultSet.getString("codigo_confirmacao"));
				agendamento.setIdMuseu(resultSet.getInt("id_museu"));
				
				return Optional.of(agendamento);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return Optional.empty();
	}
	
	public Optional<Agendamento> pesquisar(String codigo, String email) {
		String query = "SELECT * FROM agendamento WHERE codigo_confirmacao=? AND email_responsavel=?";
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, codigo);
			preparedStatement.setString(2, email);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				Agendamento agendamento = new Agendamento();
				
				agendamento.setId(resultSet.getInt("id"));
				agendamento.setDataHoraInicio(resultSet.getTimestamp("data_hora").toLocalDateTime());
				agendamento.setNumeroPessoas(resultSet.getInt("numero_pessoas"));
				agendamento.setEmailResponsavel(resultSet.getString("email_responsavel"));
				agendamento.setTermoConsentimento(resultSet.getBoolean("termo_consentimento"));
				agendamento.setCodigoConfimacao(resultSet.getString("codigo_confirmacao"));
				agendamento.setIdMuseu(resultSet.getInt("id_museu"));
				
				return Optional.of(agendamento);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return Optional.empty();
	}
	
	public Optional<Agendamento> pesquisar(LocalDateTime dataHoraAgendamento, Integer idMuseu) {
		String query = "SELECT * FROM agendamento WHERE id_museu=? AND data_hora=?";
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, idMuseu);
			preparedStatement.setTimestamp(2, Timestamp.valueOf(dataHoraAgendamento));
			
			try(ResultSet resultSet = preparedStatement.executeQuery()) {
				if(resultSet.next()) {
					Agendamento agendamento = new Agendamento();
					
					agendamento.setId(resultSet.getInt("id"));
					agendamento.setDataHoraInicio(resultSet.getTimestamp("data_hora").toLocalDateTime());
					agendamento.setNumeroPessoas(resultSet.getInt("numero_pessoas"));
					agendamento.setEmailResponsavel(resultSet.getString("email_responsavel"));
					agendamento.setTermoConsentimento(resultSet.getBoolean("termo_consentimento"));
					agendamento.setCodigoConfimacao(resultSet.getString("codigo_confirmacao"));
					agendamento.setIdMuseu(resultSet.getInt("id_museu"));
					
					return Optional.of(agendamento);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
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
				agendamento.setEmailResponsavel(resultSet.getString("email_responsavel"));
				agendamento.setTermoConsentimento(resultSet.getBoolean("termos_responsabilidade"));
				agendamento.setCodigoConfimacao(resultSet.getString("codigo_confirmacao"));
				agendamento.setIdMuseu(resultSet.getInt("id_museu"));
				
				agendamentosList.add(agendamento);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return agendamentosList;
	}
	
	public int obterNumeroPessoasAgendamento() {
		String query = "SELECT numero_pessoas FROM agendamento";
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next())
				return resultSet.getInt("numero_pessoas");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}

}