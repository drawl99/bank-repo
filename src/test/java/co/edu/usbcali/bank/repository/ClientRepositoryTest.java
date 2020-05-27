package co.edu.usbcali.bank.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.bank.domain.Client;
import co.edu.usbcali.bank.domain.DocumentType;

@SpringBootTest
class ClientRepositoryTest {
	
	private final static Long clieId=709080L;
	
	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	DocumentTypeRepository documentTypeRepository;

	@Test
	@DisplayName("save")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void aTest() {
		assertNotNull(clientRepository, "El clientRepository esta nulo");
		
		Client client=new Client();
		client.setClieId(clieId);
		client.setAdress("Avenida Siempre Viva 123");
		client.setEmail("hjsimpson@gmail.com");
		client.setEnable("S");
		client.setName("Homero J Simpson");
		client.setPhone("555 55 555 555");
		
		Optional<DocumentType> documentTypeOptional=documentTypeRepository.findById(1L);
		assertTrue(documentTypeOptional.isPresent(),"El tipo de documento no existe");
		
		DocumentType documentType=documentTypeOptional.get();
		
		client.setDocumentType(documentType);
		
		clientRepository.save(client);
	}
	
	@Test
	@DisplayName("findById")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	void bTest() {
		assertNotNull(clientRepository, "El clientRepository esta nulo");
		assertTrue(clientRepository.findById(clieId).isPresent(), "The client with id:+"+clieId+"is null");
	}
	
	@Test
	@DisplayName("update")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void cTest() {
		assertNotNull(clientRepository, "El clientRepository esta nulo");
		assertTrue(clientRepository.findById(clieId).isPresent(), "The client with id:+"+clieId+"is null");
		
		Client client=clientRepository.findById(clieId).get();
		client.setName("Diego A Gomez");
		clientRepository.save(client);
	}
	
	@Test
	@DisplayName("delete")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void dTest() {
		assertNotNull(clientRepository, "El clientRepository esta nulo");
		assertTrue(clientRepository.findById(clieId).isPresent(), "The client with id:+"+clieId+"is null");
		
		Client client=clientRepository.findById(clieId).get();
		
		clientRepository.delete(client);
	}
	
	@Test
	@DisplayName("findAll")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void eTest() {
		assertNotNull(clientRepository, "El clientRepository esta nulo");
		
		List<Client> clients=clientRepository.findAll();
		assertFalse(clients.isEmpty(),"Los clientes esta vacio");
	}
	
	@Test
	@DisplayName("findByName")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void fTest() {
		assertNotNull(clientRepository, "El clientRepository esta nulo");
		
		List<Client> clients= clientRepository.findByName("Karole Dunge");
		assertFalse(clients.isEmpty(),"Los clientes esta vacio");
	}
	
	@Test
	@DisplayName("findByEmail")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void gTest() {
		assertNotNull(clientRepository, "El clientRepository esta nulo");
		
		List<Client> clients= clientRepository.findByEmail("hdownes0@bloomberg.com");
		assertFalse(clients.isEmpty(),"Los clientes esta vacio");
	}
	
	@Test
	@DisplayName("findByDocumentType")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void hTest() {
		assertNotNull(clientRepository, "El clientRepository esta nulo");
		
		List<Client> clients= clientRepository.findByDocumentType(2L);
		assertFalse(clients.isEmpty(),"Los clientes esta vacio");
	}
	
	
	private final static Logger log = LoggerFactory.getLogger(ClientRepositoryTest.class);
	
	@Test
	@DisplayName("findAllSort")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void iTest() {
		assertNotNull(clientRepository, "El clientRepository esta nulo");
		
		Sort sort = Sort.by("name").ascending();
		List<Client> clients= clientRepository.findAll(sort);
		assertFalse(clients.isEmpty(),"Los clientes esta vacio");
		
		for (Client client : clients) {
			log.info("Name:"+client.getName());
		}
	}
	
	@Test
	@DisplayName("findByNameContains")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void jTest() {
		assertNotNull(clientRepository, "El clientRepository esta nulo");
		
		List<Client> clients= clientRepository.findByNameContains("J");
		assertFalse(clients.isEmpty(),"Los clientes esta vacio");
		
		
		for (Client client : clients) {
			log.info("Name:"+client.getName());
		}
	}

}
