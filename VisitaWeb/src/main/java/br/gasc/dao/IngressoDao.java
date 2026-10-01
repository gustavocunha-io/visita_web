package br.gasc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.gasc.dto.FabricaConexoes;
import br.gasc.model.Ingresso;

public class IngressoDao implements Dao<Ingresso, Integer> {
	private Connection connection;

	public IngressoDao() {
		this.connection = FabricaConexoes.getConnection();
	}

	@Override
	public boolean inserir(Ingresso ingresso) {
		return false;
	}

	@Override
	public boolean alterar(Ingresso ingresso) {
		return false;
	}

	@Override
	public boolean deletar(Integer id) {
		return false;
	}

	@Override
	public Optional<Ingresso> pesquisar(Integer id) {
		return Optional.empty();
	}

	@Override
	public List<Ingresso> listar() {
		String query = "SELECT * FROM tipo_ingresso";
		List<Ingresso> ingressosList = new ArrayList<Ingresso>();
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				Ingresso ingresso = new Ingresso();
				
				ingresso.setId(resultSet.getInt("id"));
				ingresso.setTipo(resultSet.getString("tipo"));
				ingresso.setPreco(resultSet.getBigDecimal("preco"));
				ingressosList.add(ingresso);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ingressosList;
	}

}
