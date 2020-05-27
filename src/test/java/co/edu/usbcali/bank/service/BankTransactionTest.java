	package co.edu.usbcali.bank.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import co.edu.usbcali.bank.dto.DepositDTO;
import co.edu.usbcali.bank.dto.TransferDTO;
import co.edu.usbcali.bank.dto.WithdrawDTO;

@SpringBootTest
class BankTransactionTest {

	@Autowired
	BankTransaction bankTransaction;
	
	private String accoId="4640-0341-9387-5781";
	private String accoIdDestination="1630-2511-2937-7299";
	private BigDecimal amount= new BigDecimal(200000);
	private String userEmail = "sendrizzi1n@posterous.com";
	
	@Test
	void withdraw() {
		try {
			WithdrawDTO withdrawDTO = new WithdrawDTO(accoId, amount, userEmail);
			Long idTransaction = bankTransaction.withdraw(withdrawDTO);
			assertNotNull(idTransaction);
		} catch (Exception e) {
			e.printStackTrace();
			assertNull(e,e.getMessage());
		}
	}

	@Test
	void deposit() {
		try {
			DepositDTO depositDTO = new DepositDTO(accoId, amount, userEmail);
			Long idTransaction = bankTransaction.deposit(depositDTO);
			assertNotNull(idTransaction);
		} catch (Exception e) {
			e.printStackTrace();
			assertNull(e,e.getMessage());
		}
	}
	
	@Test
	void transfer() {
		try {
			TransferDTO transferDTO = new TransferDTO(accoId,accoIdDestination ,amount, userEmail);
			Long idTransaction = bankTransaction.transfer(transferDTO);
			assertNotNull(idTransaction);
		} catch (Exception e) {
			e.printStackTrace();
			assertNull(e,e.getMessage());
		}
	}
}
