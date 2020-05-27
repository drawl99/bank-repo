package co.edu.usbcali.bank.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.bank.domain.DocumentType;


@SpringBootTest
class DocumentTypeRepositoryTest {
	
	private final static Long dotyId=4L;
	
	@Autowired
	DocumentTypeRepository documentTypeRepository;

	@Test
	@DisplayName("save")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void aTest() {
		assertNotNull(documentTypeRepository, "El documentTypeRepository esta nulo");
		
		DocumentType documentType = new DocumentType();
		documentType.setDotyId(dotyId);
		documentType.setEnable("S");
		documentType.setName("PASAPORTE");
		
		documentTypeRepository.save(documentType);
		
		
	}
	
	@Test
	@DisplayName("findById")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void bTest() {
		assertNotNull(documentTypeRepository, "El documentTypeRepository esta nulo");
		assertTrue(documentTypeRepository.findById(dotyId).isPresent(), "The documentType with id:"+dotyId+"is null");
		
	}
	
	@Test
	@DisplayName("update")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void cTest() {
		assertNotNull(documentTypeRepository, "El documentTypeRepository esta nulo");
		assertTrue(documentTypeRepository.findById(dotyId).isPresent(), "The documentType with id:"+dotyId+"is null");
		
		DocumentType documentType = documentTypeRepository.findById(dotyId).get();
		documentType.setName("LICENCIA");
		documentTypeRepository.save(documentType);
		
	}
	
	@Test
	@DisplayName("delete")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void dTest() {
		assertNotNull(documentTypeRepository, "El documentTypeRepository esta nulo");
		assertTrue(documentTypeRepository.findById(dotyId).isPresent(), "The documentType with id:"+dotyId+"is null");
		
		DocumentType documentType = documentTypeRepository.findById(dotyId).get();
		
		documentTypeRepository.delete(documentType);
		
	}
	
	
	@Test
	@DisplayName("findAll")
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Rollback(false)
	void eTest() {
		assertNotNull(documentTypeRepository, "El documentTypeRepository esta nulo");
		List<DocumentType> documentTypes= documentTypeRepository.findAll();
		assertFalse(documentTypes.isEmpty(),"documentTypes are empty");
	}
	
	
	

}
