package co.edu.usbcali.bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.usbcali.bank.domain.Account;

public interface AccountRepository extends JpaRepository<Account, String> {
	@Query("SELECT a FROM Account a WHERE a.client.clieId = :clieId")
	public List<Account> findAccountsByClient(@Param("clieId") Long id);
}
