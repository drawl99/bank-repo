package co.edu.usbcali.bank.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.bank.domain.TransactionType;


@SpringBootTest
class TransactionTypeRepositoryTest {

	private final static Long trtyId=4L;
	
	@Autowired
	TransactionTypeRepository transactionTypeRepository;
	
	
	@Test
	@DisplayName("save")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void aTest() {
		assertNotNull(transactionTypeRepository,"transaccionTypeRepository nulo");
		
		TransactionType transactionType = new TransactionType();
		transactionType.setTrtyId(trtyId);
		transactionType.setEnable("S");
		transactionType.setName("Otro");
		
		transactionTypeRepository.save(transactionType);
		
	}
	
	@Test
	@DisplayName("findById")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void bTest() {
		assertNotNull(transactionTypeRepository,"transaccionTypeRepository nulo");
		assertTrue(transactionTypeRepository.findById(trtyId).isPresent(),"The transacctionType with id:"+trtyId+"is null");
		
	}
	
	@Test
	@DisplayName("update")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void cTest() {
		assertNotNull(transactionTypeRepository,"transaccionTypeRepository nulo");
		assertTrue(transactionTypeRepository.findById(trtyId).isPresent(),"The transacctionType with id:"+trtyId+"is null");
		
		TransactionType transactionType = transactionTypeRepository.findById(trtyId).get();
		transactionType.setName("OTRO");
		
		transactionTypeRepository.save(transactionType);
	}
	
	@Test
	@DisplayName("delete")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void dTest() {
		assertNotNull(transactionTypeRepository,"transaccionTypeRepository nulo");
		assertTrue(transactionTypeRepository.findById(trtyId).isPresent(),"The transacctionType with id:"+trtyId+"is null");
		
		TransactionType transactionType = transactionTypeRepository.findById(trtyId).get();
		
		
		transactionTypeRepository.delete(transactionType);
	}
	
	@Test
	@DisplayName("findAll")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void eTest() {
		assertNotNull(transactionTypeRepository,"transaccionTypeRepository nulo");
		List<TransactionType> transactionTypes = transactionTypeRepository.findAll();
		assertFalse(transactionTypes.isEmpty(),"transactionTypes are empty");
	}
	

}
