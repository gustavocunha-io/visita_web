package br.gasc.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface Logica {
	String executar(HttpServletRequest request, HttpServletResponse response);
}
