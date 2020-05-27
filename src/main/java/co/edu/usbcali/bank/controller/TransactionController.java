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

import co.edu.usbcali.bank.domain.Transaction;
import co.edu.usbcali.bank.dto.TransactionDTO;
import co.edu.usbcali.bank.mapper.TransactionMapper;
import co.edu.usbcali.bank.service.TransactionService;

@RestController
@RequestMapping("/api/transaction/")
@CrossOrigin("*")
public class TransactionController {
	
	private final static Logger log = LoggerFactory.getLogger(TransactionController.class);

	@Autowired
	TransactionService transactionService;
	
	@Autowired
	TransactionMapper transactionMapper;
	
	@GetMapping("findAll")
	public ResponseEntity<?> findAll(){
		log.info("call findAll");
		List<Transaction> transactions = transactionService.findAll();
		List<TransactionDTO> transactionDTOs = transactionMapper.toTransactionDTOs(transactions);
		return ResponseEntity.ok().body(transactionDTOs);
	}
	
	@GetMapping("findById/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		log.info("call findById with id:{}", id);
		Optional<Transaction> transactionOptional = transactionService.findById(id);
		if (transactionOptional.isPresent() == false) {
			return ResponseEntity.badRequest().body("El transaction no existe");
		}

		Transaction transaction = transactionOptional.get();
		
		TransactionDTO transactionDTO = transactionMapper.toTransactionDTO(transaction);

		return ResponseEntity.ok().body(transactionDTO);
	}
	
	@PostMapping("save")
	public ResponseEntity<?> save(@RequestBody TransactionDTO transactionDTO){
		try {
			Transaction transaction = transactionMapper.toTransaction(transactionDTO);
			transaction = transactionService.save(transaction);
			transactionDTO = transactionMapper.toTransactionDTO(transaction);
			return ResponseEntity.ok().body(transactionDTO);
		} catch (Exception e) {
			log.error("save {}",e.getMessage());
			return ResponseEntity.badRequest().body(new ResponseError("400", e.getMessage()));
		}
	
	}
	
	@PutMapping("update")
	public ResponseEntity<?> update(@RequestBody TransactionDTO transactionDTO){
		try {
			Transaction transaction = transactionMapper.toTransaction(transactionDTO);
			transaction = transactionService.update(transaction);
			transactionDTO = transactionMapper.toTransactionDTO(transaction);
			return ResponseEntity.ok().body(transactionDTO);
		} catch (Exception e) {
			log.error("save {}",e.getMessage());
			return ResponseEntity.badRequest().body(new ResponseError("400", e.getMessage()));
		}
	
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id){
		log.info("call findById with id:{}", id);
		try {
			transactionService.deleteById(id);
			return ResponseEntity.ok().body("");
		} catch (Exception e) {
			log.error("delete {}",e.getMessage());
			return ResponseEntity.badRequest().body(new ResponseError("400", e.getMessage()));
		}
	
	}
	
	@GetMapping("count")
	public ResponseEntity<?> count(){
		return ResponseEntity.ok().body(transactionService.count());
	}
	
	@GetMapping("findTransactionsByAccounts/{id}")
    public ResponseEntity<?> findTransactionsByAccounts(@PathVariable("id") String id) {
        log.info("call findTransactionsByAccounts with clieId:{}", id);
        List<Transaction> transactions = transactionService.findTransactionsByAccounts(id);
        List<TransactionDTO> transactionDTOs = transactionMapper.toTransactionDTOs(transactions);
        return ResponseEntity.ok().body(transactionDTOs);
    }


}
