package co.edu.usbcali.bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.usbcali.bank.domain.RegisteredAccount;

public interface RegisteredAccountRepository extends JpaRepository<RegisteredAccount, Long> {
	
	@Query("SELECT a FROM RegisteredAccount a WHERE a.client.clieId = :clieId")
	public List<RegisteredAccount> findRegisteredAccountsByClient(@Param("clieId") Long id);
	
	@Query("SELECT a FROM RegisteredAccount a WHERE a.account.accoId = :accoId and a.client.clieId = :clieId")
	public RegisteredAccount findAccountInRegisteredAccount(@Param("accoId") String id, @Param("clieId") Long clieId);
	
}
