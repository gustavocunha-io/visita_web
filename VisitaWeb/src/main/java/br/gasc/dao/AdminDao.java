package br.gasc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.gasc.controller.SenhaService;
import br.gasc.dto.FabricaConexoes;

public class AdminDao {
	private Connection connection;
	
	public AdminDao() {
		connection = FabricaConexoes.getConnection();
	}
	
	public boolean validarCredenciais(String login, String senha) {
		String query = "SELECT senha FROM administrador WHERE login = ?";
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, login);
			
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while(resultSet.next())
					return SenhaService.varificarSenha(senha, resultSet.getString("senha"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}