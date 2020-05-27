package co.edu.usbcali.bank.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.bank.domain.Account;
import co.edu.usbcali.bank.domain.Transaction;
import co.edu.usbcali.bank.domain.TransactionType;
import co.edu.usbcali.bank.domain.User;


@SpringBootTest
class TransactionRepositoryTest {

	private final static Long tranId = 6L;
	
	@Autowired
	TransactionRepository transactionRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	TransactionTypeRepository transactionTypeRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Test
	@DisplayName("save")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void aTest() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		assertNotNull(transactionRepository,"el transactionRepository es nulo");
		
		Transaction transaction = new Transaction();
		transaction.setTranId(tranId);
		transaction.setAmount(BigDecimal.valueOf(400000));
		transaction.setDate(timestamp);
		
		Optional<Account> accountOptional=accountRepository.findById("2928-4331-8647-0560");
		assertTrue(accountOptional.isPresent(),"la cuenta no existe");
		
		Account account=accountOptional.get();
		transaction.setAccount(account);
		
		Optional<TransactionType> transactionTypeOptional=transactionTypeRepository.findById(2L);
		assertTrue(transactionTypeOptional.isPresent(), "La transaccion no existe");
		
		TransactionType transactionType = transactionTypeOptional.get();
		transaction.setTransactionType(transactionType);
		
		Optional<User> userOptional=userRepository.findById("cfaier0@cafepress.com");
		assertTrue(userOptional.isPresent(),"el usuario no existe");
		
		User user= userOptional.get();
		transaction.setUser(user);
		
		transactionRepository.save(transaction);
		
		
	}
	
	@Test
	@DisplayName("findById")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void bTest() {
		
		assertNotNull(transactionRepository,"el transactionRepository es nulo");
		assertTrue(transactionRepository.findById(tranId).isPresent(),"the transaction with id:"+tranId+"is null");
		
		
		
	}
	
	@Test
	@DisplayName("update")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void cTest() {
		
		assertNotNull(transactionRepository,"el transactionRepository es nulo");
		assertTrue(transactionRepository.findById(tranId).isPresent(),"the transaction with id:"+tranId+"is null");
		
		Transaction transaction = transactionRepository.findById(tranId).get();
		transaction.setAmount(BigDecimal.valueOf(5012));
		
		transactionRepository.save(transaction);
		
	}
	
	@Test
	@DisplayName("delete")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void dTest() {
		
		assertNotNull(transactionRepository,"el transactionRepository es nulo");
		assertTrue(transactionRepository.findById(tranId).isPresent(),"the transaction with id:"+tranId+"is null");
		
		Transaction transaction = transactionRepository.findById(tranId).get();
		
		
		transactionRepository.delete(transaction);
		
	}
	
	@Test
	@DisplayName("findAll")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void eTest() {
		
		assertNotNull(transactionRepository,"el transactionRepository es nulo");
		List<Transaction> transactions = transactionRepository.findAll();
		assertFalse(transactions.isEmpty(),"transactions are empty");
		
	}
	
	
	

}
