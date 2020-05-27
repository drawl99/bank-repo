package co.edu.usbcali.bank.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import co.edu.usbcali.bank.domain.Account;
import co.edu.usbcali.bank.domain.Client;

@SpringBootTest
class AccountServiceTest {

	private final static String accoId = "1235-1234-1234-1234";
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	ClientService clientService;
	
	@Test
	@DisplayName("save")
	@Rollback(false)
	void aTest() {
		assertNotNull(accountService, "accountService es nulo");
		
		Account account = new Account();
		account.setAccoId(accoId);
		account.setBalance(BigDecimal.valueOf(10000));
		account.setEnable("S");
		account.setPassword("123456");
		account.setVersion(1L);
		
		Optional<Client> clientOptional = clientService.findById(1L);
		assertTrue(clientOptional.isPresent(), "El cliente no esxiste");
		
		Client client = clientOptional.get();
		account.setClient(client);
		
		try {
			accountService.save(account);
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}
	}

	@Test
	@DisplayName("findById")
	@Rollback(false)
	void bTest() {
		assertNotNull(accountService, "accountService es nulo");
		assertTrue(accountService.findById(accoId).isPresent(), "The account with id:"+accoId+" is null");
	}
	
	@Test
	@DisplayName("update")
	@Rollback(false)
	void cTest() {
		assertNotNull(accountService, "accountService es nulo");
		assertTrue(accountService.findById(accoId).isPresent(), "The account with id:"+accoId+" is null");
		Account account = accountService.findById(accoId).get();
		account.setBalance(BigDecimal.valueOf(80000));
		
		try {
			accountService.update(account);
		} catch (Exception e) {
			assertNull(e,e.getMessage());
		}
	}
	
	@Test
	@DisplayName("delete")
	@Rollback(false)
	void dTest() {
		assertNotNull(accountService, "accountService es nulo");
		assertTrue(accountService.findById(accoId).isPresent(), "The account with id:"+accoId+" is null");
		Account account = accountService.findById(accoId).get();
		
		
		try {
			accountService.delete(account);
		} catch (Exception e) {
			assertNull(e,e.getMessage());
		}
	}
	
	@Test
	@DisplayName("delete")
	@Rollback(false)
	void eTest() {
		assertNotNull(accountService, "accountService es nulo");
		List<Account> accounts = accountService.findAll();
		assertFalse(accounts.isEmpty(),"accounts esta vacia");
	}
}
