package co.edu.usbcali.bank.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import co.edu.usbcali.bank.domain.Account;
import co.edu.usbcali.bank.domain.Transaction;
import co.edu.usbcali.bank.domain.TransactionType;
import co.edu.usbcali.bank.domain.User;

@SpringBootTest
class TransactionServiceTest {

	private static Long tranId=null;
	
	@Autowired
	TransactionService transactionService;
	
	@Autowired
	TransactionTypeService transactionTypeService;
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	UserService userService;
	
	@Test
	@DisplayName("save")
	@Rollback(false)
	void aTest() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Transaction transaction = new Transaction();
		transaction.setTranId(tranId);
		transaction.setAmount(BigDecimal.valueOf(100000));
		transaction.setDate(timestamp);
		
		Optional<Account> accountOptional=accountService.findById("4640-0341-9387-5781");
		assertTrue(accountOptional.isPresent(),"la cuenta no existe");
		Account account=accountOptional.get();
		transaction.setAccount(account);
		
		Optional<TransactionType> transactionTypeOptional=transactionTypeService.findById(1L);
		assertTrue(transactionTypeOptional.isPresent(), "el tipo de transaccion no existe");
		TransactionType transactionType = transactionTypeOptional.get();
		transaction.setTransactionType(transactionType);
		
		Optional<User> userOptional=userService.findById("cfaier0@cafepress.com");
		assertTrue(userOptional.isPresent(),"el usuario no existe");
		User user= userOptional.get();
		transaction.setUser(user);
		
		
		
		
		try {
			transactionService.save(transaction);
			assertNotNull(transaction.getTranId(), "No genero el id");
			tranId = transaction.getTranId();
		} catch (Exception e) {
			assertNull(e.getMessage());
		}
	}
	
	@Test
	@DisplayName("findById")
	@Rollback(false)
	void bTest() {
		assertTrue(transactionService.findById(tranId).isPresent());
		Transaction transaction = transactionService.findById(tranId).get();
		assertNotNull(transaction, "El transaction es nulo");
		
		
		
	}
	
	@Test
	@DisplayName("update")
	@Rollback(false)
	void cTest() {
		assertTrue(transactionService.findById(tranId).isPresent());
		Transaction transaction = transactionService.findById(tranId).get();
		assertNotNull(transaction, "El transaction es nulo");
		transaction.setAmount(BigDecimal.valueOf(12345678L));
		
		try {
			transactionService.update(transaction);
		} catch (Exception e) {
			assertNull(e,e.getMessage());
		}
		
		
	}
	
	@Test
	@DisplayName("delete")
	@Rollback(false)
	void dTest() {
		assertTrue(transactionService.findById(tranId).isPresent());
		Transaction transaction = transactionService.findById(tranId).get();
		assertNotNull(transaction, "El transaction es nulo");
				
		try {
			transactionService.delete(transaction);
		} catch (Exception e) {
			assertNull(e,e.getMessage());
		}
		
		
	}
	
	@Test
	@DisplayName("findAll")
	@Rollback(false)
	void eTest() {
		assertNotNull(transactionService, "El transactionService esta nulo");
		
		List<Transaction> transactions=transactionService.findAll();
		assertFalse(transactions.isEmpty(),"Los transactions esta vacio");
	}

}
