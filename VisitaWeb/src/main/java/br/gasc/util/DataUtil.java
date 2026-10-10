package br.gasc.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataUtil {
	private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public static String converterDataToString(LocalDate data) {
		return data.format(DATE_FORMAT);
	}
	
	public static String converterDataHoraToString(LocalDateTime dataHora) {
		return dataHora.format(DATE_TIME_FORMAT);
	}
}
