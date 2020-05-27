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

import co.edu.usbcali.bank.domain.RegisteredAccount;
import co.edu.usbcali.bank.dto.RegisteredAccountDTO;
import co.edu.usbcali.bank.mapper.RegisteredAccountMapper;
import co.edu.usbcali.bank.service.RegisteredAccountService;

@RestController
@RequestMapping("/api/registeredAccount/")
@CrossOrigin("*")
public class RegisteredAccountController {
	
	private final static Logger log = LoggerFactory.getLogger(RegisteredAccountController.class);

	
	@Autowired
	RegisteredAccountService registeredAccountService;
	
	@Autowired
	RegisteredAccountMapper registeredAccountMapper;
	
	@GetMapping("findAll")
	public ResponseEntity<?> findAll(){
		log.info("call findAll");
		List<RegisteredAccount> registeredAccounts = registeredAccountService.findAll();
		List<RegisteredAccountDTO> registeredAccountDTOs = registeredAccountMapper.toRegisteredAccountDTOs(registeredAccounts);
		return ResponseEntity.ok().body(registeredAccountDTOs);
	}
	
	@GetMapping("findById/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		log.info("call findById with id:{}", id);
		Optional<RegisteredAccount> registeredAccountOptional = registeredAccountService.findById(id);
		if (registeredAccountOptional.isPresent() == false) {
			return ResponseEntity.badRequest().body("El registeredAccount no existe");
		}

		RegisteredAccount registeredAccount = registeredAccountOptional.get();
		
		RegisteredAccountDTO registeredAccountDTO = registeredAccountMapper.toRegisteredAccountDTO(registeredAccount);

		return ResponseEntity.ok().body(registeredAccountDTO);
	}
	
	@PostMapping("save")
	public ResponseEntity<?> save(@RequestBody RegisteredAccountDTO registeredAccountDTO){
		try {
			RegisteredAccount registeredAccount = registeredAccountMapper.toRegisteredAccount(registeredAccountDTO);
			registeredAccount = registeredAccountService.save(registeredAccount);
			registeredAccountDTO = registeredAccountMapper.toRegisteredAccountDTO(registeredAccount);
			return ResponseEntity.ok().body(registeredAccountDTO);
		} catch (Exception e) {
			log.error("save {}",e.getMessage());
			return ResponseEntity.badRequest().body(new ResponseError("400", e.getMessage()));
		}
	
	}
	
	@PutMapping("update")
	public ResponseEntity<?> update(@RequestBody RegisteredAccountDTO registeredAccountDTO){
		try {
			RegisteredAccount registeredAccount = registeredAccountMapper.toRegisteredAccount(registeredAccountDTO);
			registeredAccount = registeredAccountService.update(registeredAccount);
			registeredAccountDTO = registeredAccountMapper.toRegisteredAccountDTO(registeredAccount);
			return ResponseEntity.ok().body(registeredAccountDTO);
		} catch (Exception e) {
			log.error("save {}",e.getMessage());
			return ResponseEntity.badRequest().body(new ResponseError("400", e.getMessage()));
		}
	
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id){
		log.info("call findById with id:{}", id);
		try {
			registeredAccountService.deleteById(id);
			return ResponseEntity.ok().body("");
		} catch (Exception e) {
			log.error("delete {}",e.getMessage());
			return ResponseEntity.badRequest().body(new ResponseError("400", e.getMessage()));
		}
	
	}
	
	@GetMapping("count")
	public ResponseEntity<?> count(){
		return ResponseEntity.ok().body(registeredAccountService.count());
	}
	
	@GetMapping("findByClient/{id}")
	public ResponseEntity<?> findRegisteredAccountByClient(@PathVariable("id") Long id){
		List<RegisteredAccount> registeredAccounts = registeredAccountService.findRegisteredAccountsByClient(id);
		List<RegisteredAccountDTO> registeredAccountDTOs = registeredAccountMapper.toRegisteredAccountDTOs(registeredAccounts);
		return ResponseEntity.ok().body(registeredAccountDTOs);
	}

}
