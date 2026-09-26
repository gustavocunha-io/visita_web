package br.gasc.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/controladora")
public class ControladoraServlet extends HttpServlet {
	private static final long serialVersionUID = 8106698518503992275L;
	
	

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nomePacote = String.format("%s.%s", Logica.class.getPackageName(), request.getParameter("logica"));
		
		try {
			Class<?> classe = Class.forName(nomePacote);
			Logica logica = (Logica)classe.getDeclaredConstructor().newInstance();
			String url = logica.executar(request, response);
			
			request.getRequestDispatcher(url).forward(request, response);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException |
		 InvocationTargetException | NoSuchMethodException | ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}