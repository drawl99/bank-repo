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

import co.edu.usbcali.bank.domain.TransactionType;
import co.edu.usbcali.bank.dto.TransactionTypeDTO;
import co.edu.usbcali.bank.mapper.TransactionTypeMapper;
import co.edu.usbcali.bank.service.TransactionTypeService;

@RestController
@RequestMapping("/api/transactionType/")
@CrossOrigin("*")
public class TransactionTypeController {

	private final static Logger log = LoggerFactory.getLogger(TransactionTypeController.class);

	
	@Autowired
	TransactionTypeMapper transactionTypeMapper;
	
	@Autowired
	TransactionTypeService transactionTypeService;
	
	@GetMapping("findAll")
	public ResponseEntity<?> findAll(){
		log.info("call findAll");
		List<TransactionType> transactionTypes = transactionTypeService.findAll();
		List<TransactionTypeDTO> transactionTypeDTOs = transactionTypeMapper.transactionTypeDTOs(transactionTypes);
		return ResponseEntity.ok().body(transactionTypeDTOs);
	}
	
	@GetMapping("findById/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		log.info("call findById with id:{}", id);
		Optional<TransactionType> transactionTypeOptional = transactionTypeService.findById(id);
		if (transactionTypeOptional.isPresent() == false) {
			return ResponseEntity.badRequest().body("El transactionType no existe");
		}

		TransactionType transactionType = transactionTypeOptional.get();
		
		TransactionTypeDTO transactionTypeDTO = transactionTypeMapper.toTransactionTypeDTO(transactionType);

		return ResponseEntity.ok().body(transactionTypeDTO);
	}
	
	@PostMapping("save")
	public ResponseEntity<?> save(@RequestBody TransactionTypeDTO transactionTypeDTO){
		try {
			TransactionType transactionType = transactionTypeMapper.transactionType(transactionTypeDTO);
			transactionType = transactionTypeService.save(transactionType);
			transactionTypeDTO = transactionTypeMapper.toTransactionTypeDTO(transactionType);
			return ResponseEntity.ok().body(transactionTypeDTO);
		} catch (Exception e) {
			log.error("save {}",e.getMessage());
			return ResponseEntity.badRequest().body(new ResponseError("400", e.getMessage()));
		}
	
	}
	
	@PutMapping("update")
	public ResponseEntity<?> update(@RequestBody TransactionTypeDTO transactionTypeDTO){
		try {
			TransactionType transactionType = transactionTypeMapper.transactionType(transactionTypeDTO);
			transactionType = transactionTypeService.update(transactionType);
			transactionTypeDTO = transactionTypeMapper.toTransactionTypeDTO(transactionType);
			return ResponseEntity.ok().body(transactionTypeDTO);
		} catch (Exception e) {
			log.error("save {}",e.getMessage());
			return ResponseEntity.badRequest().body(new ResponseError("400", e.getMessage()));
		}
	
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id){
		log.info("call findById with id:{}", id);
		try {
			transactionTypeService.deleteById(id);
			return ResponseEntity.ok().body("");
		} catch (Exception e) {
			log.error("delete {}",e.getMessage());
			return ResponseEntity.badRequest().body(new ResponseError("400", e.getMessage()));
		}
	
	}
	
	@GetMapping("count")
	public ResponseEntity<?> count(){
		return ResponseEntity.ok().body(transactionTypeService.count());
	}
}
