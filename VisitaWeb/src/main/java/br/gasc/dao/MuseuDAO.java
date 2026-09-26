package br.gasc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.gasc.dto.FabricaConexoes;
import br.gasc.model.Museu;

public class MuseuDAO {
	private Connection connection;

	public MuseuDAO() {
		this.connection = FabricaConexoes.getConnection();
	}
	
	/**
	 * Obtém as informações de todos os museus existentes no banco de dados como nome, dias de funcionamento, horario de abertura
	 * horario de fechamento que são utilizados para serem apresentados ao usuário no front-end.
	 * 
	 * @return lista com os dados dos museus existentes
	 */
	public List<Museu> listarMuseus() {
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
				museusList.add(museu);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("Passe aqui mane");
		
		return museusList;
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
