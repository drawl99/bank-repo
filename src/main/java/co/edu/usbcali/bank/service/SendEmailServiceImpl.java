package co.edu.usbcali.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import co.edu.usbcali.bank.domain.Account;

@Service
@Scope("singleton")
public class SendEmailServiceImpl {
	@Autowired
	private JavaMailSender javaMailSender;

	public void sendOpenAccount(String receptor, Account account) {

		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

		String mensaje = "La cuenta " + account.getAccoId() + " Con contraseña " + account.getPassword()
				+ " Esta inactiva para activarla consigne 200.000";

		simpleMailMessage.setTo(receptor);
		simpleMailMessage.setSubject("Apertura Cuenta #" + account.getAccoId());
		simpleMailMessage.setText(mensaje);

		javaMailSender.send(simpleMailMessage);
	}

	public void notificationUpdadePasword(String receptor, String asunto, String mensage) {

		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

		simpleMailMessage.setTo(receptor);
		simpleMailMessage.setSubject("Cambio de contraseña a la cuenta: " + asunto);
		simpleMailMessage.setText("La nueva contraseña es: " + mensage);

		javaMailSender.send(simpleMailMessage);
	}
}
