package co.edu.usbcali.bank.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import co.edu.usbcali.bank.domain.TransactionType;


@SpringBootTest
class TransactionTypeServiceTest {
	
	private static Long trtyId = null;
	
	@Autowired
	TransactionTypeService transactionTypeService;
	
	
	
	@Test
	@DisplayName("save")
	@Rollback(false)
	void aTest() {
		TransactionType transactionType = new TransactionType();
		transactionType.setTrtyId(trtyId);
		transactionType.setEnable("S");
		transactionType.setName("Test");
		
		try {
			transactionTypeService.save(transactionType);
			assertNotNull(transactionType.getTrtyId(), "No genero el id");
			trtyId = transactionType.getTrtyId();
		} catch (Exception e) {
			assertNull(e,e.getMessage());
		}
	}
	
	@Test
	@DisplayName("findById")
	@Rollback(false)
	void bTest() {
		assertTrue(transactionTypeService.findById(trtyId).isPresent());
		TransactionType transactionType = transactionTypeService.findById(trtyId).get();
		assertNotNull(transactionType, "El transactionType es nulo");
	}

	@Test
	@DisplayName("update")
	@Rollback(false)
	void cTest() {
		assertTrue(transactionTypeService.findById(trtyId).isPresent());
		TransactionType transactionType = transactionTypeService.findById(trtyId).get();
		assertNotNull(transactionType, "El transactionType es nulo");
		transactionType.setName("tranTest");
		try {
			transactionTypeService.update(transactionType);
		} catch (Exception e) {
			assertNull(e,e.getMessage());
		}
	}
	
	@Test
	@DisplayName("delete")
	@Rollback(false)
	void dTest() {
		assertTrue(transactionTypeService.findById(trtyId).isPresent());
		TransactionType transactionType = transactionTypeService.findById(trtyId).get();
		assertNotNull(transactionType, "El transactionType es nulo");
		
		try {
			transactionTypeService.delete(transactionType);
		} catch (Exception e) {
			assertNull(e,e.getMessage());
		}
	}
	
	@Test
	@DisplayName("findAll")
	@Rollback(false)
	void eTest() {
		assertNotNull(transactionTypeService, "El transactionType es nulo");
		
		List<TransactionType> transactionTypes = transactionTypeService.findAll();
		assertFalse(transactionTypes.isEmpty(), "Los transactionTypes estan vacios");
	}
}
