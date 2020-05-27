package co.edu.usbcali.bank.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
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


@SpringBootTest
class AccountRepositoryTest {

	private final static String accoId="1234-1234-1234-1234";
	
	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	AccountRepository accountRepository;

	@Test
	@DisplayName("save")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void aTest() {
		BigDecimal balance= new BigDecimal("5000000");
		assertNotNull(accountRepository, "El accountRepository esta nulo");
		
		Account account = new Account();
		account.setAccoId(accoId);
		account.setEnable("S");
		account.setPassword("123");
		account.setBalance(balance);
		account.setVersion(1L);
		Optional<Client> clientOptional=clientRepository.findById(1L);
		assertTrue(clientOptional.isPresent(),"El tipo de documento no existe");
		
		Client client=clientOptional.get();
		account.setClient(client);
		
		accountRepository.save(account);
		
		
	}
	
	@Test
	@DisplayName("findById")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	void bTest() {
		assertNotNull(accountRepository, "El accountRepository esta nulo");
		assertTrue(accountRepository.findById(accoId).isPresent(), "The account with id:+"+accoId+"is null");
	}
	
	@Test
	@DisplayName("update")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void cTest() {
		assertNotNull(accountRepository, "El accountRepository esta nulo");
		assertTrue(accountRepository.findById(accoId).isPresent(), "The account with id:+"+accoId+"is null");
		
		Account account = accountRepository.findById(accoId).get();
		account.setBalance(BigDecimal.valueOf(100000000));
		
		accountRepository.save(account);
	}
	
	@Test
	@DisplayName("delete")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void dTest() {
		assertNotNull(accountRepository, "El accountRepository esta nulo");
		assertTrue(accountRepository.findById(accoId).isPresent(), "The account with id:+"+accoId+"is null");
		
		Account account = accountRepository.findById(accoId).get();
		
		accountRepository.delete(account);
	}
	
	@Test
	@DisplayName("findAll")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void eTest() {
		assertNotNull(accountRepository, "El accountRepository esta nulo");
		List<Account> accounts = accountRepository.findAll();
		assertFalse(accounts.isEmpty(),"accounts are empty");
		
		
	}
	

}
