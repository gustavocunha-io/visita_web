package br.gasc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.gasc.dto.FabricaConexoes;
import br.gasc.model.Museu;

public class MuseuDao implements Dao<Museu, Integer> {
	private Connection connection;

	public MuseuDao() {
		this.connection = FabricaConexoes.getConnection();
	}

	@Override
	public Museu inserir(Museu museu) {
		return museu;
	}

	@Override
	public boolean alterar(Museu museu) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deletar(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Optional<Museu> pesquisar(Integer id) {
		String query = "SELECT * FROM museu WHERE id=?";
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			Museu museu = new Museu();
			
			while(resultSet.next()) {					
				museu.setId(resultSet.getInt("id"));
				museu.setNome(resultSet.getString("nome"));
				museu.setDiasFuncionamento(obterDiasFuncionamento(resultSet.getString("dias_semana")));
				museu.setHorarioAbertura(resultSet.getTime("horario_abertura").toLocalTime());
				museu.setHorarioFechamento(resultSet.getTime("horario_fechamento").toLocalTime());
				museu.setNumeroTotalVagas(resultSet.getInt("limite_pessoas"));
			}
				
			return Optional.of(museu);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return Optional.empty();
	}
	
	/**
	 * Obtém as informações de todos os museus existentes no banco de dados como nome, dias de funcionamento, horario de abertura
	 * horario de fechamento que são utilizados para serem apresentados ao usuário no front-end.
	 * 
	 * @return lista com os dados dos museus existentes
	 */
	@Override
	public List<Museu> listar() {
		String query = "SELECT * FROM museu";
		List<Museu> museusList = new ArrayList<Museu>();
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				Museu museu = new Museu();
				
				museu.setId(resultSet.getInt("id"));
				museu.setNome(resultSet.getString("nome"));
				museu.setDiasFuncionamento(obterDiasFuncionamento(resultSet.getString("dias_semana")));
				museu.setHorarioAbertura(resultSet.getTime("horario_abertura").toLocalTime());
				museu.setHorarioFechamento(resultSet.getTime("horario_fechamento").toLocalTime());
				museu.setNumeroTotalVagas(resultSet.getInt("limite_pessoas"));
				
				museusList.add(museu);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return museusList;
	}
	
	public int buscarVagasOcupadas(Integer idMuseu, LocalDateTime dataHoraAgendameno) {
		String query = "SELECT COALESCE(SUM(numero_pessoas), 0) AS total FROM agendamento WHERE data_hora=?";
		int totalPessoas = 0;
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setTimestamp(1, Timestamp.valueOf(dataHoraAgendameno));
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
				totalPessoas += resultSet.getInt("total");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return totalPessoas;
	}
	
	/**
	 * Obtem o número máximo de pessoas que podem realizar uma visita simultaneamente no museu se não
	 * retorna -1 para indicar que occoreu algum problema ao realizar a consulta no banco de daos
	 *  
	 * @return número maximo de pessoas por horário de visita
	 */
	public int obterMaximoPessoaMuseu(Integer idMuseu) {
		String query = "SELECT limite_pessoas FROM museu WHERE id=?";
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, idMuseu);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next())
				return resultSet.getInt("limite_pessoas");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	/**
	 * <p>
	 * Realiza a formatação dos dias da semana de acordo com os dados armazenados no banco, ao qual é do tipo {@code String} contendo
	 * números onde cada um é separados por vírgula de acordo com os seguintes valores:<br><br>
	 * <pre>
	 * 	Segunda = 1
	 * 	Terça = 2
	 * 	Quarta = 3
	 * 	Quinta = 4
	 * 	Sexta = 5
	 * 	Sábado = 6
	 * 	Domingo = 7
	 * </pre>
	 * Após a formatação é retornado uma {@code List} do tipo Integer a qual será armazenada em uma variável da classe {@code Museu}
	 * que define os dias da semana a qual o mesmo funcionará.
	 * 
	 * @param diasFuncionamento dias da semana ao qual o museu funciona
	 * @return uma lista de {@code Integer} contendo o número respectivo de cada dia da semana ao qual o museu funciona
	 * </p>
	 */
	private List<Integer> obterDiasFuncionamento(String diasFuncionamento) {
		List<Integer> diasFuncionamentoList = new ArrayList<Integer>();
		String[] diasSemana = diasFuncionamento.split(",");
		
		for (String dia : diasSemana)
			diasFuncionamentoList.add(Integer.valueOf(dia));
		
		return diasFuncionamentoList;
	}
}
