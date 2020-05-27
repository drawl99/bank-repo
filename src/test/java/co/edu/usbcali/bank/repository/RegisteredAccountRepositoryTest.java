package co.edu.usbcali.bank.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.bank.domain.Account;
import co.edu.usbcali.bank.domain.Client;
import co.edu.usbcali.bank.domain.RegisteredAccount;


@SpringBootTest
class RegisteredAccountRepositoryTest {

	private final static Long reacId = 9L;
	
	@Autowired
	RegisteredAccountRepository registerAccountRepository;
	
	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Test
	@DisplayName("save")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void aTest() {
		assertNotNull(registerAccountRepository,"el registeredAccountRepository es nulo");
		
		RegisteredAccount registeredAccount = new RegisteredAccount();
		registeredAccount.setReacId(reacId);
		registeredAccount.setEnable("S");
		
		Optional<Client> clientOptional=clientRepository.findById(1L);
		assertTrue(clientOptional.isPresent(),"El tipo de documento no existe");
		
		Client client=clientOptional.get();
		registeredAccount.setClient(client);
		
		Optional<Account> accountOptional=accountRepository.findById("4640-0341-9387-5781");
		assertTrue(accountOptional.isPresent(),"La cuenta no existe");
		
		Account account=accountOptional.get();
		registeredAccount.setAccount(account);
		
		registerAccountRepository.save(registeredAccount);
	}
	
	@Test
	@DisplayName("findById")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void bTest() {
		assertNotNull(registerAccountRepository,"el registeredAccountRepository es nulo");
		assertTrue(registerAccountRepository.findById(reacId).isPresent(),"The registeredAccount with Id:"+reacId+"is null");
		
	}
	
	@Test
	@DisplayName("Update")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void cTest() {
		assertNotNull(registerAccountRepository,"el registeredAccountRepository es nulo");
		assertTrue(registerAccountRepository.findById(reacId).isPresent(),"The registeredAccount with Id:"+reacId+"is null");
		
		RegisteredAccount registeredAccount = registerAccountRepository.findById(reacId).get();
		registeredAccount.setEnable("N");
		
		registerAccountRepository.save(registeredAccount);
	}
	
	@Test
	@DisplayName("delete")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void dTest() {
		assertNotNull(registerAccountRepository,"el registeredAccountRepository es nulo");
		assertTrue(registerAccountRepository.findById(reacId).isPresent(),"The registeredAccount with Id:"+reacId+"is null");
		
		RegisteredAccount registeredAccount = registerAccountRepository.findById(reacId).get();
		
		
		registerAccountRepository.delete(registeredAccount);
	}
	
	@Test
	@DisplayName("findAll")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void eTest() {
		assertNotNull(registerAccountRepository,"el registeredAccountRepository es nulo");
		List<RegisteredAccount> registeredAccounts = registerAccountRepository.findAll();
		assertFalse(registeredAccounts.isEmpty(),"registeredAccounts are empty");
	}

}
