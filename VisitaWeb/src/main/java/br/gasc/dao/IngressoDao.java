package br.gasc.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.gasc.db.FabricaConexoes;
import br.gasc.model.Ingresso;

public class IngressoDao implements Dao<Ingresso, Integer> {
	private Connection connection;

	public IngressoDao() {
		this.connection = FabricaConexoes.getConnection();
	}

	@Override
	public Ingresso inserir(Ingresso ingresso) {
		String query = "INSERT INTO tipo_ingresso (tipo, preco) VALUES (?, ?)";
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			if(ingresso != null) {
				preparedStatement.setString(1, ingresso.getTipo());
				preparedStatement.setBigDecimal(2, ingresso.getPreco());
				preparedStatement.executeUpdate();
				
				try(ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
					if(resultSet.next())
						ingresso.setId(resultSet.getInt(1));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ingresso;
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
		String query = "SELECT * FROM tipo_ingresso WHERE id=?";
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, id);
			
			try(ResultSet resultSet = preparedStatement.executeQuery()){
				if(resultSet.next()) {
					Ingresso ingresso = new Ingresso();
					
					ingresso.setId(resultSet.getInt("id"));
					ingresso.setTipo(resultSet.getString("tipo"));
					ingresso.setPreco(resultSet.getBigDecimal("preco"));
					
					return Optional.of(ingresso);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
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
	
	public static void main(String[] args) {
		IngressoDao ingressoDao = new IngressoDao();
		
		Ingresso ingresso = new Ingresso();
		
		ingresso.setTipo("Inteiro");
		ingresso.setPreco(new BigDecimal(20.0));
		
		ingresso = ingressoDao.inserir(ingresso);
		
		System.out.println(ingresso.getId());
		System.out.println(ingresso.getTipo());
		System.out.println(ingresso.getPreco());
	}

}
