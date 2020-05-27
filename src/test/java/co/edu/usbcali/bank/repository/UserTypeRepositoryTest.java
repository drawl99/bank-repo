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

import co.edu.usbcali.bank.domain.UserType;


@SpringBootTest
class UserTypeRepositoryTest {

	private final static Long ustyId=4L;
	
	@Autowired
	UserTypeRepository userTypeRepository;
	
	
	@Test
	@DisplayName("save")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void aTest() {
		assertNotNull(userTypeRepository,"userTypeRepository es nulo");
		
		UserType userType = new UserType();
		userType.setUstyId(ustyId);
		userType.setEnable("S");
		userType.setName("SUPER ADMIN");
		
		userTypeRepository.save(userType);
		
		
	}
	

	@Test
	@DisplayName("findById")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void bTest() {
		assertNotNull(userTypeRepository,"userTypeRepository es nulo");
		assertTrue(userTypeRepository.findById(ustyId).isPresent(),"no existe el tipo de usuario");
		
		
		
	}
	
	@Test
	@DisplayName("update")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void cTest() {
		assertNotNull(userTypeRepository,"userTypeRepository es nulo");
		assertTrue(userTypeRepository.findById(ustyId).isPresent(),"no existe el tipo de usuario");
		
		UserType userType = userTypeRepository.findById(ustyId).get();
		userType.setEnable("N");
		
		userTypeRepository.save(userType);
		
	}
	
	@Test
	@DisplayName("delete")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void dTest() {
		assertNotNull(userTypeRepository,"userTypeRepository es nulo");
		assertTrue(userTypeRepository.findById(ustyId).isPresent(),"no existe el tipo de usuario");
		
		UserType userType = userTypeRepository.findById(ustyId).get();
		
		
		userTypeRepository.delete(userType);
		
	}
	
	@Test
	@DisplayName("findAll")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void eTest() {
		assertNotNull(userTypeRepository,"userTypeRepository es nulo");
		
		List<UserType> userTypes = userTypeRepository.findAll();
		assertFalse(userTypes.isEmpty(),"userTypes are empty");
		
	}

}
