package br.gasc.dto;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class FabricaConexoes {
	private final static String FILENAME = "database.properties";
	private final static String DRIVE = "db.driver"; 
	
	public static Connection getConnection() {
		Properties properties = new Properties();
		
		try (InputStream inputStream = FabricaConexoes.class.getClassLoader().getResourceAsStream(FILENAME)) {
			if(inputStream == null)
				throw new RuntimeException(String.format("ERRO: O arquivo %s não foi encontrado!", FILENAME));
			
			properties.load(inputStream);
			Class.forName(properties.getProperty(DRIVE));
			
			return DriverManager.getConnection(properties.getProperty("db.url"), properties.getProperty("db.user"), properties.getProperty("db.password"));
		} catch (IOException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		Connection connection = getConnection();
		
		if(connection != null) {
			String query = "INSERT INTO pessoa (nome, cpf, email) VALUES(?, ?, ?)";
			
			try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
				preparedStatement.setString(1, "gustavo");
				preparedStatement.setString(2, "12345678901");
				preparedStatement.setString(3, "gustavo@gmail.com");
				preparedStatement.execute();
				
				System.out.println("STATUS: Pessoa inserida com sucesso!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
