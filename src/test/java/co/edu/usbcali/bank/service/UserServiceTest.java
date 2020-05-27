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

import co.edu.usbcali.bank.domain.User;
import co.edu.usbcali.bank.domain.UserType;

@SpringBootTest
class UserServiceTest {

	private final static String userEmail="abc@hotmail.com";
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserTypeService userTypeService;
	
	@Test
	@DisplayName("save")
	@Rollback(false)
	void aTest() {
		assertNotNull(userService, "El userService es nulo");
		User user = new User();
		user.setUserEmail(userEmail);
		user.setEnable("S");
		user.setName("Yordi");
		
		Optional<UserType> userTypeOptional = userTypeService.findById(1L);
		assertTrue(userTypeOptional.isPresent(), "El tipo de usuario no existe");
		UserType userType = userTypeOptional.get();
		user.setUserType(userType);
		
		try {
			userService.save(user);
		} catch (Exception e) {
			assertNull(e,e.getMessage());
		}
		
	}
	
	@Test
	@DisplayName("findById")
	
	void bTest() {
		assertNotNull(userService, "El userService esta nulo");
		assertTrue(userService.findById(userEmail).isPresent(), "The user with id:+"+userEmail+"is null");
	}
	
	@Test
	@DisplayName("update")
	void cTest() {
		assertNotNull(userService, "El userService esta nulo");
		assertTrue(userService.findById(userEmail).isPresent(), "The user with id:+"+userEmail+"is null");
		User user = userService.findById(userEmail).get();
		user.setName("drawl");
		
		try {
			userService.update(user);
		} catch (Exception e) {
			assertNull(e.getMessage());
		}
	}
	
	@Test
	@DisplayName("delete")
	void dTest() {
		assertNotNull(userService, "El userService esta nulo");
		assertTrue(userService.findById(userEmail).isPresent(), "The user with id:+"+userEmail+"is null");
		User user = userService.findById(userEmail).get();
		
		
		try {
			userService.delete(user);
		} catch (Exception e) {
			assertNull(e.getMessage());
		}
	}
	
	@Test
	@DisplayName("findAll")
	@Rollback(false)
	void eTest() {
		assertNotNull(userService, "El userService esta nulo");
		
		List<User> users=userService.findAll();
		assertFalse(users.isEmpty(),"Los users esta vacio");
	}

}
