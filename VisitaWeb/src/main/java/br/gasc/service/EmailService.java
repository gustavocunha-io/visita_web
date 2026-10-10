package br.gasc.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class EmailService {
	private final String FILENAME = "email.properties";
	private final String SMTP_HOST = "smtp.gmail.com";
	private final int SMTP_PORT = 465;
	private final String EMAIL_REMETENTE = "email.address";
	private final String SENHA_EMAIL_REMETENTE = "email.password";
	Properties properties = new Properties();
	
	
	
	public EmailService() {
		try (InputStream inputStream = EmailService.class.getClassLoader().getResourceAsStream(FILENAME)) {
			if(inputStream == null)
				throw new RuntimeException(String.format("ERRO: O arquivo %s não foi encontrado!", FILENAME));
			
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendEmail(String emailDestinatario, String assunto, StringBuffer mensagem) {
		try {
			HtmlEmail email = new HtmlEmail();
			
			email.setHostName(SMTP_HOST);
			email.setSmtpPort(SMTP_PORT);
			email.setAuthenticator(new DefaultAuthenticator(properties.getProperty(EMAIL_REMETENTE), properties.getProperty(SENHA_EMAIL_REMETENTE)));
			email.setTLS(false);
			email.setSSLOnConnect(true);
			email.setFrom(properties.getProperty(EMAIL_REMETENTE));
			email.setSubject(assunto);
			
			StringBuilder mensagemBuilder = new StringBuilder(mensagem.toString());
			
			email.setHtmlMsg(mensagemBuilder.toString());
			email.addTo(emailDestinatario);
			email.send();
		} catch (EmailException emailException) {
			emailException.printStackTrace();
		}
	}
}
