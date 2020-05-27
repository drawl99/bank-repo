package co.edu.usbcali.bank.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.bank.domain.User;
import co.edu.usbcali.bank.domain.UserType;


@SpringBootTest
class UserRepositoryTest {
	
	private final static String userEmail = "a@hotmail.com";
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserTypeRepository userTypeRepository;

	@Test
	@DisplayName("save")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void aTest() {
		assertNotNull(userRepository,"El User Repository esta nulo");
		
		User user = new User();
		user.setUserEmail(userEmail);
		user.setName("Andres");
		user.setEnable("S");
		
		Optional<UserType> userTypeOptional = userTypeRepository.findById(1L);
		assertTrue(userTypeOptional.isPresent(), "el tipo de usuario no existe");
		
		UserType userType = userTypeOptional.get();
		user.setUserType(userType);
		
		userRepository.save(user);
		
	}
	
	@Test
	@DisplayName("findById")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	void btest() {
		assertNotNull(userRepository, "El userRepository es nulo");
		assertTrue(userRepository.findById(userEmail).isPresent(), "The user wiht id:"+userEmail+"is null");
		
	}
	
	@Test
	@DisplayName("update")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void cTest() {
		assertNotNull(userRepository, "El clientRepository esta nulo");
		assertTrue(userRepository.findById(userEmail).isPresent(), "The user with id:+"+userEmail+"is null");
		
		User user=userRepository.findById(userEmail).get();
		user.setName("andres giraldo llanos");
		userRepository.save(user);
	}
	
	@Test
	@DisplayName("delete")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void dTest() {
		assertNotNull(userRepository, "El clientRepository esta nulo");
		assertTrue(userRepository.findById(userEmail).isPresent(), "The client with id:+"+userEmail+"is null");
		
		User user=userRepository.findById(userEmail).get();
		
		userRepository.delete(user);
	}
	
	@Test
	@DisplayName("findAll")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void eTest() {
		assertNotNull(userRepository, "El clientRepository esta nulo");
		
		List<User> users=userRepository.findAll();
		assertFalse(users.isEmpty(),"Los clientes esta vacio");
	}

	
	

}
