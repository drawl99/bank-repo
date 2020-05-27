package co.edu.usbcali.bank.service;

import java.util.List;

import co.edu.usbcali.bank.domain.Account;

public interface AccountService extends GenericService<Account, String> {
	public List<Account> findAccountsByClient(Long id);
	
	public Account updatePassword(Account entity) throws Exception;
}
