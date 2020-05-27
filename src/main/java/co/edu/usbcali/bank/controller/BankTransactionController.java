package co.edu.usbcali.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usbcali.bank.dto.DepositDTO;
import co.edu.usbcali.bank.dto.TransferDTO;
import co.edu.usbcali.bank.dto.WithdrawDTO;
import co.edu.usbcali.bank.service.BankTransaction;

@RestController
@RequestMapping("/api/bankTransaction/")
@CrossOrigin("*")
public class BankTransactionController {
	
	@Autowired
	BankTransaction bankTransaction;
	
	@PostMapping("transfer")
	public ResponseEntity<?> transfer(@RequestBody TransferDTO transferDTO) {
		
		try {
			Long transaccionId= bankTransaction.transfer(transferDTO);
			return ResponseEntity.ok().body(transaccionId);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ResponseError("400", e.getMessage()));
		}
		
	}
	
	@PostMapping("withdraw")
	public ResponseEntity<?> withdraw(@RequestBody WithdrawDTO withdrawDTO) {
		try {
			Long transaccionId=bankTransaction.withdraw(withdrawDTO);
			return ResponseEntity.ok().body(transaccionId);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ResponseError("400", e.getMessage()));
		}
	}
	
	@PostMapping("deposit")
	public ResponseEntity<?> deposit(@RequestBody DepositDTO depositDTO) {
		try {
			Long transaccionId=bankTransaction.deposit(depositDTO);
			return ResponseEntity.ok().body(transaccionId);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ResponseError("400", e.getMessage()));
		}
	}

}
