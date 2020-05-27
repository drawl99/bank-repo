package co.edu.usbcali.bank.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import co.edu.usbcali.bank.domain.Account;
import co.edu.usbcali.bank.dto.ClientLoginDTO;
import co.edu.usbcali.bank.repository.AccountRepository;


@Service 
@Scope("singleton")

public class ClientLoginImpl implements ClientLogin {

	@Autowired
	AccountRepository accountRepository;

	@Override
	public String login(ClientLoginDTO loginDTO) throws Exception {
		final Logger log = LoggerFactory.getLogger(ClientLoginImpl.class);
		
		Account account = accountRepository.findById(loginDTO.getAccoId()).get();
		log.info("password "+loginDTO.getPassword());
		log.info("account "+account.getPassword());
		if (accountRepository.findById(loginDTO.getAccoId()).isPresent() == false) {
			throw new Exception("La cuenta con id: " + loginDTO.getAccoId() + " no existe");
		}
		if(account.getClient().getClieId() != loginDTO.getClieId()) {
			throw new Exception("El numero del identificacion no coincide con la cuenta");
		}
		if(!account.getPassword().trim().equals(loginDTO.getPassword().trim())) {
			throw new Exception("Contraseña invalida");
		}
		if(account.getEnable().trim().equals("N")) {
			throw new Exception("Cuenta inactiva");
		}
		
		return "1";
	}

}
