package co.edu.usbcali.bank.service;

import java.util.List;

import co.edu.usbcali.bank.domain.Transaction;

public interface TransactionService extends GenericService<Transaction, Long> {
	public List<Transaction> findTransactionsByAccounts(String id);
}
