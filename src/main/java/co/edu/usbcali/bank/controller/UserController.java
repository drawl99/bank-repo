package co.edu.usbcali.bank.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usbcali.bank.domain.User;
import co.edu.usbcali.bank.dto.UserDTO;
import co.edu.usbcali.bank.mapper.UserMapper;
import co.edu.usbcali.bank.service.UserService;

@RestController
@RequestMapping("/api/user/")
@CrossOrigin("*")
public class UserController {

	private final static Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;
	
	@Autowired
	UserMapper userMapper;
	
	@GetMapping("findAll")
	public ResponseEntity<?> findAll(){
		log.info("call findAll");
		List<User> users = userService.findAll();
		List<UserDTO> userDTOs = userMapper.toUserDTOs(users);
		return ResponseEntity.ok().body(userDTOs);
	}
	
	@GetMapping("findById/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") String id) {
		log.info("call findById with id:{}", id);
		Optional<User> userOptional = userService.findById(id);
		if (userOptional.isPresent() == false) {
			return ResponseEntity.badRequest().body("El user no existe");
		}

		User user = userOptional.get();
		
		UserDTO userDTO = userMapper.toUserDTO(user);

		return ResponseEntity.ok().body(userDTO);
	}
	
	@PostMapping("save")
	public ResponseEntity<?> save(@RequestBody UserDTO userDTO){
		try {
			User user = userMapper.toUser(userDTO);
			user = userService.save(user);
			userDTO = userMapper.toUserDTO(user);
			return ResponseEntity.ok().body(userDTO);
		} catch (Exception e) {
			log.error("save {}",e.getMessage());
			return ResponseEntity.badRequest().body(new ResponseError("400", e.getMessage()));
		}
	
	}
	
	@PutMapping("update")
	public ResponseEntity<?> update(@RequestBody UserDTO userDTO){
		try {
			User user = userMapper.toUser(userDTO);
			user = userService.update(user);
			userDTO = userMapper.toUserDTO(user);
			return ResponseEntity.ok().body(userDTO);
		} catch (Exception e) {
			log.error("save {}",e.getMessage());
			return ResponseEntity.badRequest().body(new ResponseError("400", e.getMessage()));
		}
	
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") String id){
		log.info("call findById with id:{}", id);
		try {
			userService.deleteById(id);
			return ResponseEntity.ok().body("");
		} catch (Exception e) {
			log.error("delete {}",e.getMessage());
			return ResponseEntity.badRequest().body(new ResponseError("400", e.getMessage()));
		}
	
	}
	
	@GetMapping("count")
	public ResponseEntity<?> count(){
		return ResponseEntity.ok().body(userService.count());
	}
}
