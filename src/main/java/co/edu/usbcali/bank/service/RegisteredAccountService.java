package co.edu.usbcali.bank.service;

import java.util.List;

import co.edu.usbcali.bank.domain.RegisteredAccount;

public interface RegisteredAccountService extends GenericService<RegisteredAccount, Long> {
	public List<RegisteredAccount> findRegisteredAccountsByClient(Long id);
	
	public boolean findAccountInRegisteredAccount(String id, Long clieId);
}
