
package co.edu.usbcali.bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.usbcali.bank.domain.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
	
	@Query("SELECT t FROM Transaction t WHERE t.account.accoId = :accoId")
    public List<Transaction> findTransactionsByAccounts(@Param("accoId") String id);
}
