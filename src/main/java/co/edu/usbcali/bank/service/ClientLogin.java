package co.edu.usbcali.bank.service;

import co.edu.usbcali.bank.dto.ClientLoginDTO;

public interface ClientLogin {
	public String login(ClientLoginDTO loginDTO)throws Exception;
}
