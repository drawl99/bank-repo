package co.edu.usbcali.bank.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import co.edu.usbcali.bank.domain.Account;
import co.edu.usbcali.bank.domain.Client;
import co.edu.usbcali.bank.domain.RegisteredAccount;


@SpringBootTest
class RegisteredAccountServiceTest {

	private static Long reacId=null;
	
	
	@Autowired
	RegisteredAccountService registeredAccountService;
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	AccountService accountService;
	
	@Test
	@DisplayName("save")
	@Rollback(false)
	void aTest() {
		RegisteredAccount registeredAccount = new RegisteredAccount();
		registeredAccount.setReacId(reacId);
		registeredAccount.setEnable("S");
		
		Optional<Client> clientOptional = clientService.findById(1L);
		assertTrue(clientOptional.isPresent(), "El cliente no existe");
		Client client = clientOptional.get();
		
		Optional<Account> accountOptional =accountService.findById("4640-0341-9387-5781");
		assertTrue(accountOptional.isPresent(), "La cuenta no existe");
		Account account = accountOptional.get();
		
		registeredAccount.setClient(client);
		registeredAccount.setAccount(account);
		
		try {
			registeredAccountService.save(registeredAccount);
			assertNotNull(registeredAccount.getReacId(),"No genero el id");
			reacId = registeredAccount.getReacId();
		} catch (Exception e) {
			assertNull(e,e.getMessage());
		}
	}
	
	@Test
	@DisplayName("findById")
	@Rollback(false)
	void bTest() {
		assertTrue(registeredAccountService.findById(reacId).isPresent());
		RegisteredAccount registeredAccount = registeredAccountService.findById(reacId).get();
		assertNotNull(registeredAccount, "El registeredAccount es nulo");
		
		
		
	}
	
	@Test
	@DisplayName("update")
	@Rollback(false)
	void cTest() {
		assertTrue(registeredAccountService.findById(reacId).isPresent());
		RegisteredAccount registeredAccount = registeredAccountService.findById(reacId).get();
		assertNotNull(registeredAccount, "El registeredAccount es nulo");
		registeredAccount.setEnable("N");
		try {
			registeredAccountService.update(registeredAccount);
		} catch (Exception e) {
			assertNull(e,e.getMessage());
		}
		
		
	}
	
	@Test
	@DisplayName("delete")
	@Rollback(false)
	void dTest() {
		assertTrue(registeredAccountService.findById(reacId).isPresent());
		RegisteredAccount registeredAccount = registeredAccountService.findById(reacId).get();
		assertNotNull(registeredAccount, "El registeredAccount es nulo");
		
		try {
			registeredAccountService.delete(registeredAccount);
		} catch (Exception e) {
			assertNull(e,e.getMessage());
		}
		
		
	}
	
	
	@Test
	@DisplayName("findAll")
	@Rollback(false)
	void eTest() {
		assertNotNull(registeredAccountService, "El registeredAccountService esta nulo");
		
		List<RegisteredAccount> registeredAccounts=registeredAccountService.findAll();
		assertFalse(registeredAccounts.isEmpty(),"Los registeredAccounts esta vacio");
	}

}
