package co.edu.usbcali.bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.usbcali.bank.domain.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

	public List<Client> findByName(String name);

	public List<Client> findByEmail(String email);

	// JPQL
	@Query("SELECT cli FROM Client cli WHERE cli.documentType.dotyId = :dotyId")
	public List<Client> findByDocumentType(@Param("dotyId") Long id);

	public List<Client> findByNameContains(String text);
}
