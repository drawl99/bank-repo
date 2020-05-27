package co.edu.usbcali.bank.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.bank.domain.Account;
import co.edu.usbcali.bank.domain.Client;
import co.edu.usbcali.bank.repository.AccountRepository;
import co.edu.usbcali.bank.repository.ClientRepository;

@Service
@Scope("singleton")
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	ClientRepository clientRespository;
	
	@Autowired
	SendEmailServiceImpl sendEmailService;
	
	@Autowired
	Validator validator;
	
	@Override
	@Transactional(readOnly = true)
	public List<Account> findAll() {
		return accountRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Account> findById(String id) {
		return accountRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return accountRepository.count();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Account save(Account entity) throws Exception {
		validate(entity);
		
		/*
		if(accountRepository.findById(entity.getAccoId()).isPresent() == true) {
			throw new Exception("La cuenta con id:"+entity.getAccoId()+" ya existe");
		}
		*/
		if(entity.getClient() == null || 
				clientRespository.findById(entity.getClient().getClieId()).isPresent() == false) {
			throw new Exception("El cliente no existe");
		}
		
		if (entity.getBalance().compareTo(BigDecimal.ZERO) < 0) {
			throw new Exception("El balance no puede ser negativo");
		}
		entity.setAccoId(this.generateAccoId());
		entity.setPassword(this.generatePassword());
		
		accountRepository.save(entity);
		return entity;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Account update(Account entity) throws Exception {
		
		validate(entity);
		if(accountRepository.findById(entity.getAccoId()).isPresent() == false) {
			throw new Exception("La cuenta con id:"+entity.getAccoId()+" no existe");
		}
		if(entity.getClient() == null || 
				clientRespository.findById(entity.getClient().getClieId()).isPresent() == false) {
			throw new Exception("El cliente no existe");
		}
		if(entity.getPassword().trim().length()<6 || entity.getPassword().trim().length()>20) {
			throw new Exception("La contraseña debe tener entre 6 y 20 digitos");
		}
		accountRepository.save(entity);
		return entity;
	}
	
	

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(Account entity) throws Exception {
		if(accountRepository.findById(entity.getAccoId()).isPresent() == false) {
			throw new Exception("La cuenta con id:"+entity.getAccoId()+" no existe");
		}
		entity = accountRepository.findById(entity.getAccoId()).get();
		
		if(entity.getRegisteredAccounts().size() >0) {
			throw new Exception("La cuenta tiene registeredaccounts asociadas");
		}
		if(entity.getTransactions().size() > 0) {
			throw new Exception("La cuenta tiene transacciones asociadas");
		}
		entity.setEnable("N");
		accountRepository.save(entity);

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(String id) throws Exception {
		if(id == null || id == "") {
			throw new Exception("El id es obligatorio");
		}
		if(accountRepository.findById(id).isPresent() == false) {
			throw new Exception("La cuenta con id:"+id+" No existe");
		}
		delete(accountRepository.findById(id).get());
	}

	@Override
	public void validate(Account entity) throws Exception {
		if(entity==null) {
			throw new Exception("la cuenta es nula");
		}
		
		Set<ConstraintViolation<Account>> constraintViolations = validator.validate(entity);

        if (constraintViolations.size() > 0) {
            StringBuilder strMessage = new StringBuilder();

            for (ConstraintViolation<Account> constraintViolation : constraintViolations) {
                strMessage.append(constraintViolation.getPropertyPath()
                                                     .toString());
                strMessage.append(" - ");
                strMessage.append(constraintViolation.getMessage());
                strMessage.append(". \n");
            }

            throw new Exception(strMessage.toString());
        }


	}
	
	public String generatePassword() {
		
		char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
		
		Random random = new Random();
		int tamanioPassword = (int) 6+random.nextInt((20+1) - 6);
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < tamanioPassword; i++) {
			char c = chars[random.nextInt(chars.length)];
		    sb.append(c);
		}
		return sb.toString();
	}
	
	
	public String generateAccoId() {

		String accountId = "";
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				Integer number = (int) Math.floor(Math.random() * (9 - 0 + 1) + 0);
				accountId += number.toString();
			}
			if (i != 3) {
				accountId += "-";
			}
		}
		return accountId;
	}

	@Override
	public List<Account> findAccountsByClient(Long id) {
		return accountRepository.findAccountsByClient(id);
		
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Account updatePassword(Account entity) throws Exception {
		validate(entity);
		if(entity.getPassword().trim().length()<6 || entity.getPassword().trim().length()>20) {
			throw new Exception("La contraseña debe tener entre 6 y 20 digitos");
		}
		Client client = clientRespository.findById(entity.getClient().getClieId()).get();
		sendEmailService.notificationUpdadePasword(client.getEmail(), entity.getAccoId(), entity.getPassword());
		accountRepository.save(entity);
		return entity;
	}
	
	
}
